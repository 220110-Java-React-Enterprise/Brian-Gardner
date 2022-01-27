package CRUD_Repo;

import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;
import Models.AccountModel;
import Models.AccountType;
import Models.HeadAccountModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that contains methods allowing for AccountModel objects to be stored in a database
//using a Connection object
public class AccountRepoCRUD extends BaseRepo implements DataSourceCRUD<AccountModel> {
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

            //Set model as a head account model if type = HEAD
            if (model.getAccountType() == AccountType.HEAD) {
                model = new HeadAccountModel(model);
            }

            //Return true if all parts of operation were successful
            System.out.println(model + " found in accounts table.");
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to fill a custom list with data from accounts table
    public boolean readAll(CustomListInterface<AccountModel> accountModels) {
        //Temporary account model to store data read from table and add to custom list
        AccountModel accountModel;

        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset the temp model to a new empty object
                accountModel = new AccountModel();

                //Read in a row from rs and store each element in appropriate field in model
                accountModel.setId(rs.getInt("id"));
                accountModel.setAccountTypeString(rs.getString("account_type"));
                accountModel.setBalance(rs.getDouble("balance"));
                accountModel.setHeadID(rs.getInt("head_id"));
                accountModel.setDescription(rs.getString("description"));

                //Add the model to CustomList
                accountModels.add(accountModel);
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
    public boolean readAllStrings(CustomListInterface<String> accountStrings) {
        //Create empty custom list of models to store data read from tables
        CustomArrayList<AccountModel> accountModels = new CustomArrayList<>();

        if (!this.readAll(accountModels)) {
            System.out.println("readAll method failed");
            return false;
        }

        for (int i = 0; i < accountModels.size(); i++) {
            accountStrings.add(accountModels.get(i).toString());
        }

        return true;
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
