package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import InputOutputFunctions.OutputFormatter;
import Models.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that extends CustomerRepoCRUD
//holds additional methods not implementing DataSourceCRUD interface
public class CustomerRepo extends CustomerRepoCRUD {
    //Function to fill a custom list of Strings with basic information about all users - minus passwords
    public boolean readAllStringsClean(CustomListInterface<String> accountStrings) {
        //Create empty list of customer models to read from table
        CustomArrayList<CustomerModel> customerModels = new CustomArrayList<>();

        //Attempt to read from table
        if (!this.readAll(customerModels)) {
            System.out.println("Customer read failed.");
            return false;
        }

        //Add 'clean' customer information to list of strings
        for (int i = 0; i < customerModels.size(); i++) {
            accountStrings.add(OutputFormatter.formatCustomerClean(customerModels.get(i)));
        }

        return true;
    }

    //Function to print custom list of 'cleaned' customer info to console
    public void printAllClean() {
        //Create empty list of strings
        CustomListInterface<String> accountStrings = new CustomArrayList<>();

        //Populate list with readAllStringsClean
        if (!this.readAllStringsClean(accountStrings)) {
            return;
        }

        //Print strings to console
        for (int i = 0; i < accountStrings.size(); i++) {
            System.out.println(accountStrings.get(i));
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
}
