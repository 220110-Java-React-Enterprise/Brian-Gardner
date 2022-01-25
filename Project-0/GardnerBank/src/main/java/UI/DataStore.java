package UI;

import Models.*;

//Class to hold static program data such as user's name
public class DataStore {
    //Holds current user information
    private static CustomerModel customerModel;

    //Holds current bank account information
    private static AccountModel accountModel;

    //Holds the current transaction information
    private static TransactionModel transactionModel;

    //Holds the current joint object information
    private static CustomerAccountModel customerAccountModel;
    private static AccountTransactionModel accountTransactionModel;

    //Holds the last view name
    private static String lastViewName;

    //Holds potential error message for input validation
    private static String potentialErrorMessage;

    public static CustomerModel getCustomerModel() {
        return customerModel;
    }

    public static void setCustomerModel(CustomerModel customerModel) {
        DataStore.customerModel = customerModel;
    }


    public static AccountModel getAccountModel() {
        return accountModel;
    }

    public static void setAccountModel(AccountModel accountModel) {
        DataStore.accountModel = accountModel;
    }

    public static TransactionModel getTransactionModel() {
        return transactionModel;
    }

    public static void setTransactionModel(TransactionModel transactionModel) {
        DataStore.transactionModel = transactionModel;
    }

    public static CustomerAccountModel getCustomerAccountModel() {
        return customerAccountModel;
    }

    public static void setCustomerAccountModel(CustomerAccountModel customerAccountModel) {
        DataStore.customerAccountModel = customerAccountModel;
    }

    public static AccountTransactionModel getAccountTransactionModel() {
        return accountTransactionModel;
    }

    public static void setAccountTransactionModel(AccountTransactionModel accountTransactionModel) {
        DataStore.accountTransactionModel = accountTransactionModel;
    }

    public static String getName(){
        return customerModel.getFullName();
    }

    public static String getLastViewName() {
        return lastViewName;
    }

    public static void setLastViewName(String lastViewName) {
        DataStore.lastViewName = lastViewName;
    }

    public static String getPotentialErrorMessage() {
        return potentialErrorMessage;
    }

    public static void setPotentialErrorMessage(String potentialErrorMessage) {
        DataStore.potentialErrorMessage = potentialErrorMessage;
    }

    public static void addToPotentialErrorMessage(String addedPotentialErrorMessage) {
        DataStore.potentialErrorMessage += addedPotentialErrorMessage;
    }

    public static void clearPotentialErrorMessage() {
        DataStore.potentialErrorMessage = "";
    }
}
