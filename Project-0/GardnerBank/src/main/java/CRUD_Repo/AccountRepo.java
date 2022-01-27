package CRUD_Repo;

import CustomLists.CustomListInterface;
import Models.AccountModel;
import Models.AccountType;
import Models.HeadAccountModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Class that extends AccountRepoCRUD
//holds additional methods not specified in DataSourceCRUD interface
public class AccountRepo extends AccountRepoCRUD {
    //Function to read all accounts in the accounts table where head_id equals a given account_id
    //into a customList
    public boolean readSubAccounts(AccountModel accountModel, CustomListInterface<AccountModel> subAccounts) {
        //Temporary AccountModel to store account information into before putting into CustomList
        AccountModel tempModel;
        try {
            //Prepare the SQL statement to be run
            String sql = "SELECT * FROM accounts WHERE head_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountModel.getId());

            //Run the SQL statement and store results
            ResultSet rs = preparedStatement.executeQuery();

            //Iterate through the results in rs and add each row as an object to the CustomList
            while(rs.next()) {
                //Reset the temp model to a new empty object
                tempModel = new AccountModel();

                //Read in a row from rs and store each element in appropriate field in model
                tempModel.setId(rs.getInt("id"));
                tempModel.setAccountTypeString(rs.getString("account_type"));
                tempModel.setBalance(rs.getDouble("balance"));
                tempModel.setDescription(rs.getString("description"));

                //If account_type is "head", turn tempModel into HeadAccountModel
                if (tempModel.getAccountType() == AccountType.HEAD) {
                    tempModel = new HeadAccountModel(tempModel);
                }

                //Add the model to custom list
                subAccounts.add(tempModel);
            }

            //Return true if read operations were successful and no exceptions thrown
            return true;
        } catch (SQLException e) {
            //Print stack trace and return false if an operation threw an exception
            e.printStackTrace();
            return false;
        }
    }

    //Function to turn sub account structure into strings
}
