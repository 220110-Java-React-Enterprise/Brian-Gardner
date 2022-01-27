package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import InputOutputFunctions.OutputFormatter;
import Models.AccountModel;
import Models.AccountTransactionModel;
import Models.TransactionModel;
import Models.TransactionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that extends AccountTransactionRepoCRUD
//holds additional methods not implementing DataSourceCRUD interface
public class AccountTransactionRepo extends AccountTransactionRepoCRUD {
    //Function to complete a full transaction: either deposit or withdrawal
    public boolean createFullAccountTransaction(AccountModel accountModel, TransactionModel transactionModel,
                                                AccountTransactionModel accountTransactionModel) {
        //Return value that stores whether all operations were successful
        //Set initial value to true if transaction type is NOT transfer
        boolean operationSuccess;

        //Set the transaction type to a boolean - true for deposit, false for withdrawal
        boolean isDeposit = (transactionModel.getTransactionType() == TransactionType.DEPOSIT);

        //Integers to track number of steps left in operation
        int maxSteps = 5;
        int steps = maxSteps;

        //Integer to hold lastId; set operationSuccess to false if failed to gather
        int lastId = 0;

        //Strings to create/update SQL rows
        String sqlReadAccountBalance = "SELECT balance FROM accounts WHERE id = ?";
        String sqlTransactionCreate = "INSERT INTO transactions (transaction_type, description)" +
                "VALUES (?, ?)";
        String sqlAccountTransactionCreate = "INSERT INTO accounts_transactions " +
                "(account_id, transaction_id, amount) VALUES (?, ?, ?)";
        String sqlUpdateAccountBalance = "UPDATE accounts SET balance = ? WHERE id = ?";

        //Declare the statements in function scope; set values in individual steps
        PreparedStatement readAccountBalance;
        PreparedStatement createTransaction;
        PreparedStatement createAccountTransaction;
        PreparedStatement updateAccountBalance;

        //Step one - check that the transaction type is NOT transfer
        //Set return value to true if transaction type is not transfer
        operationSuccess = (transactionModel.getTransactionType() != TransactionType.TRANSFER);

        //Reduce steps by one if transaction type is not transfer; set below 0 otherwise
        steps -= operationSuccess ? 1 : maxSteps;

        //Create a temporary variable to store updated balance after deposit
        double tempBalance = accountModel.getBalance() + accountTransactionModel.getAmount();

        //Step two - check that the account passed in exists in the database
        //and that the transaction will not set the balance below 0
        if (steps == maxSteps - 1) {
            try {
                //Attempt to prepare readAccountBalance statement
                readAccountBalance = connection.prepareStatement(sqlReadAccountBalance);

                //Set arguments for readAccountBalance
                readAccountBalance.setInt(1, accountModel.getId());

                //Attempt to run statement and store results in rs
                ResultSet rs = readAccountBalance.executeQuery();

                if (rs.next()) {
                    accountModel.setBalance(rs.getDouble("balance"));

                    //Set temporary balance to current account balance plus transaction amount
                    tempBalance = accountModel.getBalance() + accountTransactionModel.getAmount();

                    //Check if transaction would set account below $0
                    if (tempBalance < 0.0) {
                        System.out.println("Error: transaction would set account balance below $0.00");
                        operationSuccess = false;
                    }
                }
                else {
                    System.out.println("Account not found.");
                    operationSuccess = false;
                }
            } catch (SQLException e) {
                //Print stack trace and return false if an operation threw an exception
                e.printStackTrace();
                operationSuccess = false;
            }

            //Reduce steps by one if try block completed, account was found and balance was determined to not go below 0
            steps -= operationSuccess ? 1 : maxSteps;
        }


        //Step three - attempt to create transaction row
        if (steps == maxSteps - 2) {
            try {
                //Attempt to prepare createTransaction statement
                createTransaction = connection.prepareStatement(sqlTransactionCreate);

                //Set arguments for createTransaction
                createTransaction.setString(1, transactionModel.getTransactionTypeString());
                createTransaction.setString(2, transactionModel.getDescription());

                //Attempt to run the createTransaction statement
                createTransaction.executeUpdate();

                //Set lastId to most recently created auto-increment
                lastId = this.readLastId();

                //Set success to whether readLastId successfully read most recent id (true) or returned -1 (false)
                operationSuccess = lastId > 0;

                //Set transaction id to last id if readLastId successful
                if (operationSuccess) {
                    transactionModel.setId(lastId);
                }
            } catch (SQLException e) {
                //Print stack trace and return false if an operation threw an exception
                e.printStackTrace();
                operationSuccess = false;
            }

            //Reduce steps by one if try block completed & lastId was found; set to -1 if exception was thrown
            //or lastId was not found
            steps -= operationSuccess ? 1 : maxSteps;
        }

        //Step four - attempt to set auto-commit to false
        //so changes to account_transaction and account are committed at the same time
        if (steps == maxSteps - 3) {
            operationSuccess = this.setAutoCommitFalse();
            steps -= operationSuccess ? 1 : maxSteps;
        }

        //Final step - attempt to create account_transaction and update accounts rows
        if (steps == 1) {
            //Set accountTransactionModel values
            accountTransactionModel.setAccountId(accountModel.getId());
            accountTransactionModel.setTransactionId(transactionModel.getId());

            try {
                //Attempt to prepare statements
                createAccountTransaction = connection.prepareStatement(sqlAccountTransactionCreate);
                updateAccountBalance = connection.prepareStatement(sqlUpdateAccountBalance);

                //Set arguments for createAccountTransaction
                createAccountTransaction.setInt(1, accountTransactionModel.getAccountId());
                createAccountTransaction.setInt(2, accountTransactionModel.getTransactionId());
                createAccountTransaction.setDouble(3, accountTransactionModel.getAmount());

                //Set arguments for updateAccountBalance
                updateAccountBalance.setDouble(1, tempBalance);
                updateAccountBalance.setInt(2, accountTransactionModel.getAccountId());

                //Attempt to run statements
                createAccountTransaction.executeUpdate();
                updateAccountBalance.executeUpdate();

                //Attempt to commit statements
                connection.commit();

                //Update account balance to tempBalance after transaction completes
                accountModel.setBalance(tempBalance);
            } catch (SQLException e) {
                //Print stack trace and return false if an operation threw an exception
                e.printStackTrace();

                //Attempt to rollback transaction
                try {
                    connection.rollback();
                } catch (SQLException e_1) {
                    //Print stack trace and return false if an operation threw an exception
                    e_1.printStackTrace();

                    System.out.println("WARNING: ROLLBACK FAILED");
                }

                //Set operationSuccess to false
                operationSuccess = false;
            }
        }

        //Attempt to set auto-commit back to true regardless of whether operation was successful
        this.setAutoCommitTrue();

        return operationSuccess;
    }

    //Function to read account-transactions by a given id (transaction or account id), joined with information from the
    //transaction table (transaction type/description), and store into respective custom lists
    public boolean readTransactionDetails(int id, String fieldName, CustomListInterface<AccountTransactionModel> accountTransactionModels,
                                          CustomListInterface<TransactionModel> transactionModels) {
        //Exit method if fieldName is neither account_id nor transaction_id
        if (fieldName == null || (!fieldName.equals("account_id") && !fieldName.equals("transaction_id"))) {
            System.out.println("Field name must be set to either account_id or transaction_id");
            return false;
        }

        //Create temporary accountTransactionModel & transactionModel objects to store data read from tables
        AccountTransactionModel accountTransactionModel;
        TransactionModel transactionModel;

        try {
            //Prepare the SQL statement to be run, inserting fieldName as either account_id or transaction_id
            String sql = "SELECT accounts_transactions.*, transactions.transaction_type, transactions.description " +
                    "FROM accounts_transactions, transactions WHERE accounts_transactions.transaction_id = " +
                    "transactions.id AND " + fieldName + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset temp models to empty objects
                accountTransactionModel = new AccountTransactionModel();
                transactionModel= new TransactionModel();

                //Read in a row from rs and store each element in appropriate field in temp models
                accountTransactionModel.setAccountId(rs.getInt("account_id"));
                accountTransactionModel.setTransactionId(rs.getInt("transaction_id"));
                accountTransactionModel.setAmount(rs.getDouble("amount"));

                transactionModel.setId(rs.getInt("transaction_id"));
                transactionModel.setTransactionType(rs.getString("transaction_type"));
                transactionModel.setDescription(rs.getString("description"));

                //Add models to custom lists
                accountTransactionModels.add(accountTransactionModel);
                transactionModels.add(transactionModel);
            }

            //Return true if read operations were successful and no exceptions thrown
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to perform readTransactionDetails and store the results in a custom list of formatted strings
    public boolean readTransactionDetails(int id, String fieldName, CustomListInterface<String> results) {
        //Boolean to be returned at end of function to indicate whether successful
        boolean operationSuccess;

        //Create empty custom lists of models to store data read from tables
        CustomArrayList<AccountTransactionModel> accountTransactionModels = new CustomArrayList<>();
        CustomArrayList<TransactionModel> transactionModels = new CustomArrayList<>();

        operationSuccess = this.readTransactionDetails(id, fieldName, accountTransactionModels, transactionModels);

        if (!operationSuccess) {
            System.out.println("readTransactionDetails failed");
            return false;
        }

        if (accountTransactionModels.size() != transactionModels.size()) {
            System.out.println("Error: custom array lists for each model do not match in size.");
            return false;
        }

        for (int i = 0; i < accountTransactionModels.size() && i < transactionModels.size(); i++) {
            results.add(OutputFormatter.formatTransactionDetails(accountTransactionModels.get(i), transactionModels.get(i)));
        }

        return true;
    }

    //Function to print readTransactionDetails results to console
    public boolean printTransactionDetails(int id, String fieldName) {
        //Create empty custom list of strings to pass into readTransactionDetails
        CustomArrayList<String> details = new CustomArrayList<>();

        //Run readTransactionDetails and return false if function failed
        if (!readTransactionDetails(id, fieldName, details)) {
            return false;
        }

        //Loop through custom list and print details to console
        for (int i = 0; i < details.size(); i++) {
            System.out.println(details.get(i));
        }

        //Return true if successful
        return true;
    }
}
