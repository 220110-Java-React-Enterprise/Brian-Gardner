package CRUD_Repo;

import Models.CustomerAccountModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for CustomerAccountModel obejcts to be stored in/retrieved from
//SQL database using a Connection object
public class CustomerAccountRepo implements JointDataSourceCRUD<CustomerAccountModel> {
    //java.sql.Connection object allowing data to be stored into a SQL database
    private final Connection connection;

    //No-arg constructor which sets the java.sql.Connection object using the ConnectionManager class
    public CustomerAccountRepo() { connection = ConnectionManager.getConnection(); }

    //Function to create a new row in customers_accounts table from a CustomerAccountModel object
    @Override
    public boolean create(CustomerAccountModel model) {
        try {
            String sql = "INSERT INTO customers_accounts (customer_id, account_id, approval_needed) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, model.getCustomerID());
            preparedStatement.setInt(2, model.getAccountID());
            preparedStatement.setBoolean(3, model.getApprovalNeeded());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to create a CustomerAccountModel object with data read from a row in customers_accounts table
    //using CustomerAccountModel.id as index to find row in table
    @Override
    public boolean read(Integer customerId, Integer accountId, CustomerAccountModel model) {
        try {
            String sql = "SELECT * FROM customers_accounts WHERE customer_id = ? AND account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, accountId);

            ResultSet rs = preparedStatement.executeQuery();

            model = new CustomerAccountModel();
            while(rs.next()) {
                model.setCustomerID(rs.getInt("customer_id"));
                model.setAccountID(rs.getInt("account_id"));
                model.setApprovalNeeded(rs.getBoolean("approval_needed"));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to update information in a row in the customers_accounts table using a CustomerAccountModel object
    //using customerId and accountId as index to find row in table
    @Override
    public boolean update(CustomerAccountModel model) {
        try {
            String sql = "UPDATE customers_accounts SET approval_needed = ? WHERE customer_id = ? AND account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, model.getApprovalNeeded());
            preparedStatement.setInt(2, model.getCustomerID());
            preparedStatement.setInt(3, model.getAccountID());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to remove a row from the customers_accounts table
    //using customer_id and account_id as index to find row in table
    @Override
    public boolean delete(Integer customerId, Integer accountId) {
        try {
            String sql = "DELETE FROM customers_accounts WHERE customer_id = ? AND account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
