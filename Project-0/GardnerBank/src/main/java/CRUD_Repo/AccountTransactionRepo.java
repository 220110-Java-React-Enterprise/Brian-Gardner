package CRUD_Repo;

import Models.*;
import Models.AccountTransactionModel;
import UI.DataStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for AccountTransactionModel objects to be stored in/retrieved from
//SQL database using a Connection object
public class AccountTransactionRepo extends BaseRepo implements JointDataSourceCRUD<AccountTransactionModel> {
    //java.sql.Connection object allowing data to be stored into a SQL database
    private final Connection connection;

    //No-arg constructor which sets the java.sql.Connection object using the ConnectionManager class
    public AccountTransactionRepo() { connection = ConnectionManager.getConnection(); }


    //Function to create a new row in accounts_transactions table from a AccountTransactionModel object
    @Override
    public boolean create(AccountTransactionModel model) {
        try {
            String sql = "INSERT INTO accounts_transactions (account_id, transaction_id, approval_needed) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, model.getAccountId());
            preparedStatement.setInt(2, model.getTransactionId());
            preparedStatement.setDouble(3, model.getAmount());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to create a AccountTransactionModel object with data read from a row in accounts_transactions table
    //using AccountTransactionModel.id as index to find row in table
    @Override
    public boolean read(Integer accountId, Integer transactionId, AccountTransactionModel model) {
        try {
            String sql = "SELECT * FROM accounts_transactions WHERE account_id = ? AND transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, transactionId);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                model.setAccountId(rs.getInt("account_id"));
                model.setTransactionId(rs.getInt("transaction_id"));
                model.setAmount(rs.getDouble("approval_needed"));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to update information in a row in the accounts_transactions table using a AccountTransactionModel object
    //using accountId and transactionId as index to find row in table
    @Override
    public boolean update(AccountTransactionModel model) {
        try {
            String sql = "UPDATE accounts_transactions SET approval_needed = ? WHERE account_id = ? AND transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, model.getAmount());
            preparedStatement.setInt(2, model.getAccountId());
            preparedStatement.setInt(3, model.getTransactionId());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to remove a row from the accounts_transactions table
    //using account_id and transaction_id as index to find row in table
    @Override
    public boolean delete(Integer accountId, Integer transactionId) {
        try {
            String sql = "DELETE FROM accounts_transactions WHERE account_id = ? AND transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, transactionId);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
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

            //Reduce steps by one if try block completed & lastId was found; set to -1 if exception was thrown
            //or lastId was not found
            //steps -= operationSuccess ? 1 : maxSteps;
        }

        //Attempt to set auto-commit back to true regardless of whether operation was successful
        this.setAutoCommitTrue();

        return operationSuccess;
    }
}
