package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import Models.AccountModel;
import Models.TransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for TransactionModel objects to be stored in/retrieved from
//SQL database using a Connection object
public class TransactionRepoCRUD extends BaseRepo implements DataSourceCRUD<TransactionModel> {
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

    //Function to fill a custom list with data from transactions table
    public boolean readAll(CustomListInterface<TransactionModel> transactionModels) {
        //Temporary account model to store data read from table and add to custom list
        TransactionModel transactionModel;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM transactions";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset the temp model to a new empty object
                transactionModel = new TransactionModel();

                //Read in a row from rs and store each element in appropriate field in model
                transactionModel.setId(rs.getInt("id"));
                transactionModel.setTransactionType(rs.getString("transaction_type"));
                transactionModel.setDescription(rs.getString("description"));
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
    public boolean readAllStrings(CustomListInterface<String> transactionStrings) {
        //Create empty custom list of models to store data read from tables
        CustomArrayList<TransactionModel> transactionModels = new CustomArrayList<>();

        if (!this.readAll(transactionModels)) {
            System.out.println("readAll method failed");
            return false;
        }

        for (int i = 0; i < transactionModels.size(); i++) {
            transactionStrings.add(transactionModels.get(i).toString());
        }

        return true;
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
