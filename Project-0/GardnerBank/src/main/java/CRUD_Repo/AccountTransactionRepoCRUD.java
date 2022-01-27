package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import Models.*;
import Models.AccountTransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for AccountTransactionModel objects to be stored in/retrieved from
//SQL database using a Connection object
public class AccountTransactionRepoCRUD extends BaseRepo implements JointDataSourceCRUD<AccountTransactionModel> {
     //Function to create a new row in accounts_transactions table from a AccountTransactionModel object
    @Override
    public boolean create(AccountTransactionModel model) {
        try {
            //Prepare the SQL statement to be run, inputting data from model
            String sql = "INSERT INTO accounts_transactions (account_id, transaction_id, approval_needed) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, model.getAccountId());
            preparedStatement.setInt(2, model.getTransactionId());
            preparedStatement.setDouble(3, model.getAmount());

            //Run the SQL statement to add to table
            preparedStatement.executeUpdate();

            //Return true if successful
            return true;
        } catch (SQLException e) {
            //Display stack trace and return false if not successful
            e.printStackTrace();
            return false;
        }
    }

    //Function to create a AccountTransactionModel object with data read from a row in accounts_transactions table
    //using AccountTransactionModel.id as index to find row in table
    @Override
    public boolean read(Integer accountId, Integer transactionId, AccountTransactionModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM accounts_transactions WHERE account_id = ? AND transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, transactionId);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Check if the result set is empty and return false if so
            if (!rs.next()) {
                System.out.println("Joint AccountTransaction with account id#" + accountId + " and transaction id#" +
                        transactionId + " not found in accounts_transactions table.");
                return false;
            }

            //Input data into model object whose reference is passed into function
            model.setAccountId(rs.getInt("account_id"));
            model.setTransactionId(rs.getInt("transaction_id"));
            model.setAmount(rs.getDouble("approval_needed"));

            //Return true if all parts of operation were successful
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to read data from the account_transactions table into a custom list of AccountTransactionModels
    //Where the id is equal to the given index
    public boolean read(Integer id, String fieldName, CustomListInterface<AccountTransactionModel> accountTransactionModels) {
        //Exit method if fieldName is neither account_id nor transaction_id
        if (fieldName == null || (!fieldName.equals("account_id") && !fieldName.equals("transaction_id"))) {
            System.out.println("Field name must be set to either account_id or transaction_id");
            return false;
        }

        //Create a temporary AccountTransactionModel object to store data read from the table that will be added to the
        //custom list
        AccountTransactionModel accountTransactionModel;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM accounts_transactions WHERE " + fieldName + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset the temp model to a new empty object
                accountTransactionModel = new AccountTransactionModel();

                //Read in a row from rs and store each element in appropriate field in temp model
                accountTransactionModel.setAccountId(rs.getInt("account_id"));
                accountTransactionModel.setTransactionId(rs.getInt("transaction_id"));
                accountTransactionModel.setAmount(rs.getDouble("amount"));

                //Add the model to CustomList
                accountTransactionModels.add(accountTransactionModel);
            }

            //Return true if read operations were successful and no exceptions thrown
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Method to read information from a custom list of objects and store formatted strings into a custom list
    //Where all objects match a certain id; field specified by fieldType
    public boolean readStrings(Integer id, String fieldName, CustomListInterface<String> accountTransactionStrings) {
        //Create empty custom list of strings with data from read with single id parameter
        CustomArrayList<AccountTransactionModel> accountTransactionModels = new CustomArrayList<>();

        if (!this.read(id, fieldName, accountTransactionModels)) {
            System.out.println("read method failed.");
            return false;
        }

        for (int i = 0; i < accountTransactionModels.size(); i++) {
            accountTransactionStrings.add(accountTransactionModels.get(i).toString());
        }

        return true;
    }

    //Method to read all rows from the table into a custom list
    public boolean readAll(CustomListInterface<AccountTransactionModel> accountTransactionModels) {
        //Temporary account model to store data read from table and add to custom list
        AccountTransactionModel accountTransactionModel;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM accounts_transactions";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset the temp model to a new empty object
                accountTransactionModel = new AccountTransactionModel();

                //Read in a row from rs and store each element in appropriate field in temp model
                accountTransactionModel.setAccountId(rs.getInt("account_id"));
                accountTransactionModel.setTransactionId(rs.getInt("transaction_id"));
                accountTransactionModel.setAmount(rs.getDouble("amount"));

                //Add the model to CustomList
                accountTransactionModels.add(accountTransactionModel);
            }

            //Return true if read operations were successful and no exceptions thrown
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a custom list of strings with data from readAll
    public boolean readAllStrings(CustomListInterface<String> accountTransactionStrings) {
        //Create empty custom list of models to store data read from tables
        CustomArrayList<AccountTransactionModel> accountTransactionModels = new CustomArrayList<>();

        if (!this.readAll(accountTransactionModels)) {
            System.out.println("readAll method failed");
            return false;
        }

        for (int i = 0; i < accountTransactionModels.size(); i++) {
            accountTransactionStrings.add(accountTransactionModels.get(i).toString());
        }

        return true;
    }

    //Function to update information in a row in the accounts_transactions table using a AccountTransactionModel object
    //using accountId and transactionId as index to find row in table
    @Override
    public boolean update(AccountTransactionModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "UPDATE accounts_transactions SET approval_needed = ? WHERE account_id = ? AND transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, model.getAmount());
            preparedStatement.setInt(2, model.getAccountId());
            preparedStatement.setInt(3, model.getTransactionId());

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Return true if all parts of operation were successful
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to remove a row from the accounts_transactions table
    //using account_id and transaction_id as index to find row in table
    @Override
    public boolean delete(Integer accountId, Integer transactionId) {
        try {
            //Prepare the SQL statement to be run
            String sql = "DELETE FROM accounts_transactions WHERE account_id = ? AND transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, transactionId);

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Return true if delete operation was successful
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to make a deposit
    public boolean createDeposit(AccountModel accountModel, TransactionModel transactionModel, AccountTransactionModel accountTransactionModel) {
        //Return value that stores whether all operations were successful
        boolean operationSuccess = false;

        //Integers to track number of steps left in operation
        int maxSteps = 3;
        int steps = maxSteps;

        //Integer to hold lastId; set operationSuccess to false if failed to gather
        int lastId = 0;

        //Strings to create/update SQL rows
        String sqlTransactionCreate = "INSERT INTO transactions (transaction_type, description)" +
                "VALUES (?, ?)";
        String sqlAccountTransactionCreate = "INSERT INTO accounts_transactions " +
                "(account_id, transaction_id, amount) VALUES (?, ?, ?)";
        String sqlUpdateAccountBalance = "UPDATE accounts SET balance = ? WHERE id = ?";

        //Declare the statements in function scope; set values in individual steps
        PreparedStatement createTransaction;
        PreparedStatement createAccountTransaction;
        PreparedStatement updateAccountBalance;

        //Create a temporary variable to store updated balance after deposit
        double tempBalance = accountModel.getBalance() + accountTransactionModel.getAmount();

        //Attempt to create transaction row
        if (steps == maxSteps) {
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
            } catch (SQLException e) {
                //Print stack trace and return false if an operation threw an exception
                e.printStackTrace();
            }

            if (operationSuccess) {
                transactionModel.setId(lastId);
            }

            //Reduce steps by one if try block completed & lastId was found; set to -1 if exception was thrown
            //or lastId was not found
            steps -= operationSuccess ? 1 : maxSteps;
        }

        //Attempt to set auto-commit to false, so changes to account_transaction and account are committed at the same time
        if (steps == maxSteps - 1) {
            operationSuccess = this.setAutoCommitFalse();
            steps -= operationSuccess ? 1 : maxSteps;
        }

        //Attempt to create account_transaction and update account rows
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
}
