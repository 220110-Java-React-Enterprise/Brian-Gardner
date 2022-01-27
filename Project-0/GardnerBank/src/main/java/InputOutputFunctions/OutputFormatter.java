package InputOutputFunctions;

import Models.AccountTransactionModel;
import Models.CustomerModel;
import Models.TransactionModel;

//Class holding static methods to convert various data types into formatted strings
public class OutputFormatter {
    //Function to format a double as currency
    public static String formatCurrency(double dbl) {
        boolean isNegative = false;

        //Check if dbl is negative
        if (dbl < 0) {
            dbl = -dbl;
            isNegative = true;
        }

        //Convert to 2-decimal-place format String
        String decimalFormat = String.format("%.2f", dbl);

        //String to be returned
        String tmpString = "";

        //Index for the start of the substring that gets added to tmpString before the ','
        //Starts at 0 then gets set to x when a comma is added to tmpString
        int y = 0;

        //Iterate through the string, starting at index 1
        for (int x = 1; x < decimalFormat.length() - 5; x++) {
            if ((decimalFormat.length() - x) % 3 == 0) {
                tmpString += decimalFormat.substring(y, x) + ',';
                y = x;
            }
        }

        //Add remaining digits after commas are done being added in
        tmpString += decimalFormat.substring(y, decimalFormat.length());

        //Add dollar sign to start of string
        tmpString = "$" + tmpString;

        //Add - if negative
        tmpString = (isNegative ? "-" : "") + tmpString;

        //Return string
        return tmpString;
    }

    //Function to format transaction details, taking information from both a joint account-transaction model and transaction model
    //and returning them into readable format
    public static String formatTransactionDetails(AccountTransactionModel accountTransactionModel, TransactionModel transactionModel) {
        return "Transaction details: account #" + accountTransactionModel.getAccountId() + " - " + transactionModel.getTransactionTypeString() + " #" + transactionModel.getId() +
                ": " + accountTransactionModel.getAmountString() + " - Desc: " + transactionModel.getDescription();
    }
    
    //Function to format user information without passwords
    public static String formatCustomerClean(CustomerModel customerModel) {
        return "Customer #" + customerModel.getId() + ": " + customerModel.getFullName() + "\nusername: " +
                customerModel.getUsername();
    }
}
