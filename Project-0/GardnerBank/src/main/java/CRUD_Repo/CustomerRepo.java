package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
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

    //Function to create a new row in customers table from a CustomerModel object
    @Override
    public CustomerModel create(CustomerModel model) {
        try {
            String sql = "INSERT INTO customers (given_name, middle_name, surname, username, password)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getGivenName());
            preparedStatement.setString(2, model.getMiddleName());
            preparedStatement.setString(3, model.getSurname());
            preparedStatement.setString(4, model.getUsername());
            preparedStatement.setString(5, model.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Function to create a CustomerModel object with data read from a row in customers table
    //using CustomerModel.id as index to find row in table
    @Override
    public CustomerModel read(Integer id) {
        try {
            String sql = "SELECT * FROM customers WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            CustomerModel model = new CustomerModel();
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setGivenName(rs.getString("given_name"));
                model.setMiddleName(rs.getString("middle_name"));
                model.setSurname(rs.getString("surname"));
                model.setUsername(rs.getString("username"));
                model.setPassword(rs.getString("password"));
            }

            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Function to read in a CustomerModel object from a row in customers table, using a username
    public boolean read(String username, CustomerModel model) {
        try {
            String sql = "SELECT * FROM customers WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setGivenName(rs.getString("given_name"));
                model.setMiddleName(rs.getString("middle_name"));
                model.setSurname(rs.getString("surname"));
                model.setUsername(rs.getString("username"));
                model.setPassword(rs.getString("password"));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Function to fill a CustomList (either CustomArrayList or CustomLinkedList) with data from the customers table
    public boolean readAll(CustomListInterface<CustomerModel> customers) {
        CustomerModel model;

        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                model = new CustomerModel();

                model.setId(rs.getInt("id"));
                model.setGivenName(rs.getString("given_name"));
                model.setMiddleName(rs.getString("middle_name"));
                model.setSurname(rs.getString("surname"));
                model.setUsername(rs.getString("username"));
                model.setPassword(rs.getString("password"));

                customers.add(model);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a CustomList of strings with usernames from the customers table
    public boolean readUsernames(CustomListInterface<String> usernames) {
        try {
            String sql = "SELECT username FROM customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                usernames.add(rs.getString("username"));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a CustomList of string pairs with usernames from the customers table
    public boolean readUsernamePasswordPairs(CustomListInterface<String[]> pairs) {
        String[] tmpPair;

        try {
            String sql = "SELECT username, password FROM customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                tmpPair = new String[2];

                tmpPair[0] = rs.getString("username");
                tmpPair[1] = rs.getString("password");

                pairs.add(tmpPair);
            }

            return true;
        } catch (SQLException e) {
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
    public CustomerModel update(CustomerModel model) {
        try {
            String sql = "UPDATE customers SET given_name = ?, middle_name = ?, surname = ?, username = ?, password = ?" +
                    " WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getGivenName());
            preparedStatement.setString(2, model.getMiddleName());
            preparedStatement.setString(3, model.getSurname());
            preparedStatement.setString(4, model.getUsername());
            preparedStatement.setString(5, model.getPassword());
            preparedStatement.setInt(6, model.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    //Function to remove a row from the customers table
    //using CustomerModel.id as index to find row in table
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
