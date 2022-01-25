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

    //Function to read the id of the most recently added row in the accounts table
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

    //Function to create a new row in accounts table from a AccountModel object
    @Override
    public boolean create(AccountModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "INSERT INTO accounts (account_type, balance, head_id, description)" +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getAccountTypeString());
            preparedStatement.setDouble(2, model.getBalance());
            preparedStatement.setInt(3, model.getHeadID());
            preparedStatement.setString(4, model.getDescription());

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Set the model id to last id added through auto-increment
            model.setId(readLastId());

            //Indicate that model was successfully added to table and return true
            System.out.println(model + "...successfully created in accounts table.");
            return true;
        } catch (SQLException e) {
            //Display stack trace and return false if not successful
            e.printStackTrace();
            return false;
        }
    }

    //Function to create a AccountModel object with data read from a row in accounts table
    //using AccountModel.id as index to find row in table
    @Override
    public boolean read(Integer id, AccountModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM accounts WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Check if the result set is empty and return false if so
            if(!rs.next()) {
                System.out.println("Account with id#: " + id + " not found in accounts table.");
                return false;
            }

            //Input data into model object whose reference is passed into function
            model.setId(rs.getInt("id"));
            model.setAccountTypeString(rs.getString("account_type"));
            model.setBalance(rs.getDouble("balance"));
            model.setHeadID(rs.getInt("head_id"));
            model.setDescription(rs.getString("description"));

            //Return true if all parts of operation were successful
            System.out.println(model + " found in accounts table.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to update information in a row in the accounts table using a AccountModel object
    //using AccountModel.id as index to find row in table
    @Override
    public boolean update(AccountModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "UPDATE accounts SET account_type = ?, balance = ?, head_id = ?, description = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getAccountTypeString());
            preparedStatement.setDouble(2, model.getBalance());
            preparedStatement.setInt(3, model.getHeadID());
            preparedStatement.setString(4, model.getDescription());
            preparedStatement.setInt(5, model.getId());

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Return true if all parts of operation were successful
            System.out.println(model + "\nUpdated in accounts table.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to remove a row from the accounts table
    //using AccountModel.id as index to find row in table
    @Override
    public boolean delete(Integer id) {
        try {
            //Prepare the SQL statement to be run
            String sql = "DELETE FROM accounts WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement
            preparedStatement.executeUpdate();

            //Return true if delete operation was successful
            System.out.println("Account id#" + id + " successfully deleted.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if operation threw an exception
            e.printStackTrace();
            return false;
        }
    }
}
