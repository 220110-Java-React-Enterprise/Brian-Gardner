package CRUD_Repo;

import Models.AccountModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for AccountModel objects to be stored in a database
//using a Connection object
public class AccountRepo implements DataSourceCRUD<AccountModel> {
    //java.sql.Connection object allowing data to be stored into a SQL database
    private final Connection connection;

    //No-arg constructor which sets the java.sql.Connection object using the ConnectionManager class
    public AccountRepo() { connection = ConnectionManager.getConnection(); }

    //Function to create a new row in accounts table from a AccountModel object
    @Override
    public AccountModel create(AccountModel model) {
        try {
            String sql = "INSERT INTO accounts (id, account_type, balance, head_id)" +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, model.getId());
            preparedStatement.setString(2, model.getAccountTypeString());
            preparedStatement.setDouble(3, model.getBalance());
            preparedStatement.setInt(4, model.getHeadID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Function to create a AccountModel object with data read from a row in accounts table
    //using AccountModel.id as index to find row in table
    @Override
    public AccountModel read(Integer id) {
        try {
            String sql = "SELECT * FROM accounts WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            AccountModel model = new AccountModel();
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setAccountTypeString(rs.getString("account_type"));
                model.setBalance(rs.getDouble("balance"));
                model.setHeadID(rs.getInt("head_id"));
            }

            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Function to update information in a row in the accounts table using a AccountModel object
    //using AccountModel.id as index to find row in table
    @Override
    public AccountModel update(AccountModel model) {
        try {
            String sql = "UPDATE accounts SET account_type = ?, balance = ?, head_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getAccountTypeString());
            preparedStatement.setDouble(2, model.getBalance());
            preparedStatement.setInt(3, model.getHeadID());
            preparedStatement.setInt(4, model.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Function to remove a row from the accounts table
    //using AccountModel.id as index to find row in table
    @Override
    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM customers WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
