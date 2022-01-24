package Models;

//Enumerated values for account types
enum AccountType {
    HEAD, CHECKING, SAVINGS
}

//Class used for storing information in/retrieving information from accounts table
public class AccountModel {
    //Private member variables to be accessed/changed through getter/setter functions
    private Integer id;
    private AccountType type;
    private Double balance;
    private Integer headID;

    //No arg constructor
    public AccountModel() {
        this.id = 0;
        this.type = AccountType.HEAD;
        this.balance = 0.0;
        this.headID = this.id;
    }

    //Constructor with all parameters
    public AccountModel(Integer id, AccountType type, Double balance, Integer headID) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.headID = headID;
    }

    //Getter/setter methods to access and change private member variables
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getHeadID() {
        return headID;
    }

    public void setHeadID(Integer headID) {
        this.headID = headID;
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
}
