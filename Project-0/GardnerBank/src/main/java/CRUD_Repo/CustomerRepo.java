package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import Models.CustomerAccountModel;
import Models.CustomerModel;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for CustomerModel objects to be stored in/retrieved from
//SQL database using a Connection object
public class CustomerRepo implements DataSourceCRUD<CustomerModel> {
    //java.sql.Connection object allowing data to be stored into a SQL database
    private final Connection connection;

    //No-arg constructor which sets the java.sql.Connection object using the ConnectionManager class
    public CustomerRepo() {
        connection = ConnectionManager.getConnection();
    }

    //Function to read the id of the most recently added row in the customers table
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
            System.out.println(model + "...successfully created in customers table.");
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
            System.out.println(model + " found in customers table.");
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
            System.out.println(model + " found in customers table.");
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
            System.out.println("Data successfully read from customers table into CustomList");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a CustomList of strings with usernames from the customers table
    public boolean readUsernames(CustomListInterface<String> usernames) {
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT username FROM customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Add username read from customers table as String in CustomList while rs iterator has next
            while(rs.next()) {
                usernames.add(rs.getString("username"));
            }

            //Return true if read operations were successful and no exceptions thrown
            System.out.println("Data successfully read from customers table into CustomList");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a CustomList of string pairs with usernames from the customers table
    public boolean readUsernamePasswordPairs(CustomListInterface<String[]> pairs) {
        String[] tmpPair;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT username, password FROM customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Add username/password pair read from customers table as String in CustomList while rs
            //iterator has next
            while(rs.next()) {
                //Set temp pair to new empty array
                tmpPair = new String[2];

                //Add username and password to indices 0 and 1 of temp pair, respectively
                tmpPair[0] = rs.getString("username");
                tmpPair[1] = rs.getString("password");

                //Add temp pair to CustomList
                pairs.add(tmpPair);
            }

            //Return true if read operations were successful and no exceptions thrown
            System.out.println("Data successfully read from customers table into CustomList");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to check if a given username is unique against customers table
    public boolean isUniqueUsername(String username) {
        //Create empty arraylist of usernames to be filled by readUsernames function
        CustomArrayList<String> usernames = new CustomArrayList<>();

        //Attempt to read usernames from customers table and store whether successful in boolean
        boolean readSuccessful = this.readUsernames(usernames);

        //Look through list of usernames if read was successful
        if (readSuccessful) {
            for (int i = 0; i < usernames.size(); i++) {
                //Return false if username was found in list
                if (username.equals(usernames.get(i))) {
                    return false;
                }
            }
            //Return true if username was not found in list
            return true;
        }

        //Notify user read was unsuccessful and return false
        System.out.println("Read unsuccessful.");
        return false;
    }

    //Function to check if a set of login credentials exist in customers table
    //i.e. if a username and password match
    public boolean checkLoginCredentials(String username, String password) {
        //Create empty arrayList of String pairs to be filled by readUsernamePasswordPairs function
        CustomArrayList<String[]> pairs = new CustomArrayList<>();

        //Attempt to read username/password pairs from customers table and store whether successful in boolean
        boolean readSuccessful = this.readUsernamePasswordPairs(pairs);

        //Look through list of credential pairs if read was successful
        if (readSuccessful) {
            for (int i = 0; i < pairs.size(); i++) {
                //Find username in list
                if (username.equals(pairs.get(i)[0])) {
                    //Return whether the given password matches password with given username
                    return password.equals(pairs.get(i)[1]);
                }
            }
        }

        return false;
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

            //Run the SQL statement and store results
            preparedStatement.executeUpdate();

            //Return true if all parts of operation were successful
            System.out.println(model + "\nUpdated in customers table.");
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
            System.out.println("Customer id#" + id + " successfully deleted.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if operation threw an exception
            e.printStackTrace();
            return false;
        }
    }
}
