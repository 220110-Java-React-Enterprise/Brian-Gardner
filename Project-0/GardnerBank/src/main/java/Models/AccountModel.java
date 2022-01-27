package Models;

import CRUD_Repo.AccountRepo;
import CustomLists.CustomListInterface;
import InputOutputFunctions.InputValidation;
import InputOutputFunctions.OutputFormatter;
import UI.DataStore;

//Class used for storing information in/retrieving information from accounts table
public class AccountModel {
    //Static class variables to store allowed description length
    protected static final int descriptionMinLength = 0;
    protected static final int descriptionMaxLength = 256;

    //protected member variables to be accessed/changed through getter/setter functions
    protected Integer id;
    protected AccountType type;
    protected Double balance;
    protected Integer headID;
    protected String description;

    //No arg constructor
    public AccountModel() {
        this.id = 0;
        this.type = AccountType.HEAD;
        this.balance = 0.0;
        this.headID = this.id;
    }

    //Constructor with all parameters
    public AccountModel(Integer id, AccountType type, Double balance, Integer headID, String description) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.headID = headID;
        this.description = description;
    }

    //Getter/setter methods to access and change protected member variables
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return type;
    }

    public void setAccountType(AccountType type) {
        this.type = type;
    }

    public boolean setAccountType(int typeInt) {
        switch(typeInt) {
            case 1: this.setAccountType(AccountType.CHECKING);
                return true;
            case 2: this.setAccountType(AccountType.SAVINGS);
                return true;
            case 3: this.setAccountType(AccountType.HEAD);
                return true;
            default:
                System.out.println("Failed to set account type - type must be (1)Checking (2)Savings (3)Head");
                return false;
        }
    }

    public Double getBalance() {
        return balance;
    }

    public String getBalanceString() { return OutputFormatter.formatCurrency(this.getBalance()); }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getHeadID() {
        return headID;
    }

    public void setHeadID(Integer headID) {
        this.headID = headID;
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

    //Function to return the String version of enumerated AccountType
    public String getAccountTypeString() {
        switch(this.getAccountType()) {
            case HEAD: return "HEAD";
            case CHECKING: return "CHECKING";
            case SAVINGS: return "SAVINGS";
            default: return "";
        }
    }

    //Function to set enumerated AccountType using String input
    public void setAccountTypeString(String accountType) {
        switch(accountType) {
            case "HEAD": this.setAccountType(AccountType.HEAD);
                break;
            case "SAVINGS": this.setAccountType(AccountType.SAVINGS);
                break;
            case "CHECKING":
            default: this.setAccountType(AccountType.CHECKING);
        }
    }

    //Function used by HeadAccounts to recursively set subAccounts and subAccounts of any HeadAccountModels within
    //Does nothing if mistakenly called by superclass AccountModel
    public void setSubAccountsRecursive(AccountRepo accountRepo) {
        System.out.println("Function setSubAccounts mistakenly called on AccountModel superclass.");
    }

    //Function used by HeadAccounts to recursively add string version of sub accounts to a list of strings
    //Does nothing if accidentally called by superclass
    public boolean getSubAccountsStringsRecursive(CustomListInterface<String> subAccountsStrings, int tabIndex) {
        return false;
    }

    //Override of Object.toString() method to automatically convert AccountModel object to String
    //with all member variables present
    @Override
    public String toString() {
        String balanceString = String.format("$%.2f", this.getBalance());
        return this.getHeadID() + ": " + this.getAccountTypeString() + " Account #" + this.getId() + ": " +
                this.getBalanceString() + "\nDescription: " + this.getDescription();
    }
}
