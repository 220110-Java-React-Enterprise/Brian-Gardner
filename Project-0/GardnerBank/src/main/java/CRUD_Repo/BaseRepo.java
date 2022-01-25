package CRUD_Repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Base repository class that holds functions used by all other repository types
//Including turning auto_commit on and off
public class BaseRepo {
    //java.sql.Connection object allowing data to be stored into a SQL database
    private final Connection connection;

    //No-arg constructor which sets the java.sql.Connection object using the ConnectionManager class
    public BaseRepo() { connection = ConnectionManager.getConnection(); }

    //Function to read the id of the most recently added row in the transactions table

    //Function that checks the database for the most recently generated auto-increment value
    //Run immediately after creating a new table row that has an auto-increment primary key
    // such as customers, accounts, transactions
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

    //Function to set auto-commit to true - returns false if failed to do so
    public boolean setAutoCommitTrue() {
        //Return value
        boolean success = false;

        try {
            //Attempt to set auto-commit to true
            connection.setAutoCommit(true);

            //Warn user that auto-commit set to false; do not attempt to carry out single-statement SQL operations
            System.out.println("WARNING: auto-commit set to true. Set to false before " +
                    "carrying out multi-statement operations.");

            //Set success to true if end of try block was reached without exception thrown
            success = true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            System.out.println("WARNING: failed to set auto-commit to true\n" +
                    "Do not attempt to commit single statements.");
        }

        //Return whether the operation to set auto-commit was successful
        return success;
    }

    //Function to set auto-commit to false - returns false if failed to do so
    public boolean setAutoCommitFalse() {
        //Return value
        boolean success = false;

        try {
            //Attempt to set auto-commit to false
            connection.setAutoCommit(false);

            //Warn user that auto-commit set to false; do not attempt to carry out single-statement SQL operations
            System.out.println("WARNING: auto-commit set to false. Set to true before " +
                    "carrying out single-statement operations.");

            //Set success to true if end of try block was reached without exception thrown
            success = true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            System.out.println("WARNING: failed to set auto-commit to false\n" +
                    "Do not attempt to commit multiple statements.");
        }

        //Return whether the operation to set auto-commit was successful
        return success;
    }
}
