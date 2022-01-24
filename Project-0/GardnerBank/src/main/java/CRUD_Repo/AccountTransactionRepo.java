package CRUD_Repo;

import Models.AccountTransactionModel;
import Models.AccountTransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for AccountTransactionModel obejcts to be stored in/retrieved from
//SQL database using a Connection object
public class AccountTransactionRepo implements JointDataSourceCRUD<AccountTransactionModel> {
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

            model = new AccountTransactionModel();
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
}
