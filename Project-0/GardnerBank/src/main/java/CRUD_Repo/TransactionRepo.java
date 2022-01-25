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

    //Function to read the id of the most recently added row in the transactions table
    public int readLastId() {
        //Set lastId to -1; only change if retrieving last id was successful
        int lastId = -1;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT LAST_INSERT_ID();";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Check if the result set is empty
            if (!rs.next()) {
                System.out.println("Nothing in result set");
            }
            else {
                //Set lastId to the result
                lastId = rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            //Print stack trace if exception caught
            e.printStackTrace();
        }

        //Returns most recent auto-increment id if successfully found; -1 if not
        return lastId;
    }

    //Function to create a new row in transactions table from a TransactionModel object
    @Override
    public boolean create(TransactionModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "INSERT INTO transactions (transaction_type, description)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getTransactionTypeString());
            preparedStatement.setString(2, model.getDescription());

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Set the model id to last id added through auto-increment
            model.setId(readLastId());

            //Indicate that model was successfully added to table and return true
            System.out.println(model + "...successfully created in transactions table.");
            return true;
        } catch (SQLException e) {
            //Display stack trace and return false if not successful
            e.printStackTrace();
            return false;
        }
    }

    //Function to create a TransactionModel object with data read from a row in transactions table
    //using TransactionModel.id as index to find row in table
    @Override
    public boolean read(Integer id, TransactionModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM transactions WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Check if the result set is empty and return false if so
            if(!rs.next()) {
                System.out.println("Transaction with id#: " + id + " not found in transactions table.");
                return false;
            }

            //Input data into model object whose reference is passed into function
            model.setId(rs.getInt("id"));
            model.setTransactionType(rs.getString("transaction_type"));
            model.setDescription(rs.getString("description"));

            //Return true if all parts of operation were successful
            System.out.println(model + " found in transactions table.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to update information in a row in the transactions table using a TransactionModel object
    //using TransactionModel.id as index to find row in table
    @Override
    public boolean update(TransactionModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "UPDATE transactions SET transaction_type = ?, description = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getTransactionTypeString());
            preparedStatement.setString(2, model.getDescription());
            preparedStatement.setInt(3, model.getId());

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Return true if all parts of operation were successful
            System.out.println(model + "\nUpdated in transactions table.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to remove a row from the transactions table
    //using TransactionModel.id as index to find row in table
    @Override
    public boolean delete(Integer id) {
        try {
            //Prepare the SQL statement to be run
            String sql = "DELETE FROM transactions WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Return true if delete operation was successful
            System.out.println("Transaction id#" + id + " successfully deleted.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }
}
