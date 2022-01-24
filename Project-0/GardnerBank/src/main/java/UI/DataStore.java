package UI;

import Models.CustomerModel;

//Class to hold static program data such as user's name
public class DataStore {
    //Holds the current user information
    private static CustomerModel model;
    private static String name;
    private static String potentialErrorMessage;

    public static CustomerModel getModel() {
        return model;
    }

    public static void setModel(CustomerModel model) {
        DataStore.model = model;
    }

    public static String getName(){
        return name;
    }

    public static void setName(String n) {
        name = n;
    }
    public static void setName(CustomerModel customerModel) { name = customerModel.getFullName(); }

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
