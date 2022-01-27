package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import Models.AccountModel;
import Models.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for CustomerModel objects to be stored in/retrieved from
//SQL database using a Connection object
public class CustomerRepoCRUD extends BaseRepo implements DataSourceCRUD<CustomerModel> {
    //Function to create a new row in customers table from a CustomerModel object
    @Override
    public boolean create(CustomerModel model) {
        try {
            //Prepare the SQL statement to be run, inputting data from model
            String sql = "INSERT INTO customers (given_name, middle_name, surname, username, password)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getGivenName());
            preparedStatement.setString(2, model.getMiddleName());
            preparedStatement.setString(3, model.getSurname());
            preparedStatement.setString(4, model.getUsername());
            preparedStatement.setString(5, model.getPassword());

            //Run the SQL statement to add to table
            preparedStatement.executeUpdate();

            //Set the model id to last id added through auto-increment
            model.setId(readLastId());

            //Indicate that model was successfully added to table and return true
            return true;
        } catch (SQLException e) {
            //Display stack trace and return false if not successful
            e.printStackTrace();
            return false;
        }
    }


    //Function to fill in a CustomerModel object with data read from a row in customers table
    //using CustomerModel.id as index to find row in table
    @Override
    public boolean read(Integer id, CustomerModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM customers WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Check if the result set is empty and return false if so
            if (!rs.next()) {
                System.out.println("Customer with id#" + id + " not found in customers table.");
                return false;
            }

            //Input data into model object whose reference is passed into function
            model.setId(rs.getInt("id"));
            model.setGivenName(rs.getString("given_name"));
            model.setMiddleName(rs.getString("middle_name"));
            model.setSurname(rs.getString("surname"));
            model.setUsername(rs.getString("username"));
            model.setPassword(rs.getString("password"));

            //Return true if all parts of operation were successful
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill in a CustomerModel object with data read from a row in customers table
    //using CustomerModel.id as index to find row in table
    public boolean read(String username, CustomerModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM customers WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Check if the result set is empty and return false if so
            if (!rs.next()) {
                System.out.println("Customer with username " + username + " not found in customers table.");
                return false;
            }

            //Input data into model object whose reference is passed into function
            model.setId(rs.getInt("id"));
            model.setGivenName(rs.getString("given_name"));
            model.setMiddleName(rs.getString("middle_name"));
            model.setSurname(rs.getString("surname"));
            model.setUsername(rs.getString("username"));
            model.setPassword(rs.getString("password"));

            //Return true if all parts of operation were successful
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a CustomList (either CustomArrayList or CustomLinkedList) with data from the customers table
    public boolean readAll(CustomListInterface<CustomerModel> customers) {
        //Create a temporary CustomerModel object to store data read from the table that will be added to the
        //CustomList
        CustomerModel model;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset the temp model to a new empty object
                model = new CustomerModel();

                //Read in a row from rs and store each element in appropriate field in model
                model.setId(rs.getInt("id"));
                model.setGivenName(rs.getString("given_name"));
                model.setMiddleName(rs.getString("middle_name"));
                model.setSurname(rs.getString("surname"));
                model.setUsername(rs.getString("username"));
                model.setPassword(rs.getString("password"));

                //Add the model to CustomList
                customers.add(model);
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
    public boolean readAllStrings(CustomListInterface<String> customerStrings) {
        //Create empty custom list of models to store data read from tables
        CustomArrayList<CustomerModel> customerModels = new CustomArrayList<>();

        if (!this.readAll(customerModels)) {
            System.out.println("readAll method failed");
            return false;
        }

        for (int i = 0; i < customerModels.size(); i++) {
            customerStrings.add(customerModels.get(i).toString());
        }

        return true;
    }

    //Function to update information in a row in the customers table using a CustomerModel object
    //using CustomerModel.id as index to find row in table
    @Override
    public boolean update(CustomerModel model) {
        try {
            //Prepare the SQL statement to be run
            String sql = "UPDATE customers SET given_name = ?, middle_name = ?, surname = ?, username = ?, password = ?" +
                    " WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getGivenName());
            preparedStatement.setString(2, model.getMiddleName());
            preparedStatement.setString(3, model.getSurname());
            preparedStatement.setString(4, model.getUsername());
            preparedStatement.setString(5, model.getPassword());
            preparedStatement.setInt(6, model.getId());

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

    //Function to remove a row from the customers table
    //using CustomerModel.id as index to find row in table
    @Override
    public boolean delete(Integer id) {
        try {
            //Prepare the SQL statement to be run
            String sql = "DELETE FROM customers WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

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
}
