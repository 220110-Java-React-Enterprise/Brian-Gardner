package Models;

import InputOutputFunctions.InputValidation;
import UI.DataStore;

//Class used for storing information from/to be stored in
//transactions table
public class TransactionModel {
    //Static class variables to store allowed description length
    private static final int descriptionMinLength = 0;
    private static final int descriptionMaxLength = 256;

    //Private member variables to be accessed/changed through getter/setter functions
    private Integer id;
    private TransactionType transactionType;
    private String description;

    //No arg constructor
    public TransactionModel() {
    }

    //Constructor with all parameters
    public TransactionModel(Integer id, TransactionType type, String description) {
        this.id = id;
        this.transactionType = type;
        this.description = description;
    }

    //Getter/setter methods to access and change private member variables
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        //Clear DataStore's potential error message
        DataStore.clearPotentialErrorMessage();

        //Validate given string against description content & length requirements
        if (InputValidation.isValidDescription("description", description, descriptionMinLength, descriptionMaxLength)) {
            this.description = description;
            return true;
        }
        else {
            System.out.println(DataStore.getPotentialErrorMessage());
            return false;
        }
    }

    //Function to return the String version of enumerated TransactionType
    public String getTransactionTypeString() {
        switch(this.getTransactionType()) {
            case DEPOSIT: return "DEPOSIT";
            case WITHDRAWAL: return "WITHDRAWAL";
            case TRANSFER: return "TRANSFER";
            default: return "";
        }
    }

    //Function to set enumerated TransactionType using String input
    public void setTransactionType(String transactionType) {
        switch(transactionType) {
            case "WITHDRAWAL": this.setTransactionType(TransactionType.WITHDRAWAL);
                break;
            case "TRANSFER": this.setTransactionType(TransactionType.TRANSFER);
                break;
            case "DEPOSIT":
            default: this.setTransactionType(TransactionType.DEPOSIT);
        }
    }

    //Override of Object.toString() method to automatically convert TransactionModel object to String
    //with all member variables present
    @Override
    public String toString() {
        return this.getTransactionTypeString() + " #" + this.getId() + "\nDescription: " + this.getDescription();
    }
}
