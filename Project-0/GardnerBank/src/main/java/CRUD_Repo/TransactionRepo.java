package CRUD_Repo;

import Models.TransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for TransactionModel objects to be stored in/retrieved from
//SQL database using a Connection object
public class TransactionRepo implements DataSourceCRUD<TransactionModel> {
    //java.sql.Connection object allowing data to be stored into a SQL database
    private final Connection connection;

    //No-arg constructor which sets the java.sql.Connection object using the ConnectionManager class
    public TransactionRepo() { connection = ConnectionManager.getConnection(); }

    //Function to create a new row in transactions table from a TransactionModel object
    @Override
    public TransactionModel create(TransactionModel model) {
        try {
            String sql = "INSERT INTO transactions (id, transaction_type)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, model.getId());
            preparedStatement.setString(2, model.getTransactionTypeString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Function to create a TransactionModel object with data read from a row in transactions table
    //using TransactionModel.id as index to find row in table
    @Override
    public TransactionModel read(Integer id) {
        try {
            String sql = "SELECT * FROM transactions WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            TransactionModel model = new TransactionModel();
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setTransactionType(rs.getString("transaction_type"));
            }

            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Function to update information in a row in the transactions table using a TransactionModel object
    //using TransactionModel.id as index to find row in table
    @Override
    public TransactionModel update(TransactionModel model) {
        try {
            String sql = "UPDATE transactions SET transaction_type = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getTransactionTypeString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Function to remove a row from the transactions table
    //using TransactionModel.id as index to find row in table
    @Override
    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM transactions WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
