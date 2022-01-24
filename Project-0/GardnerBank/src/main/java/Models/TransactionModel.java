package Models;

//Enumerated values for transaction types
enum TransactionType {
    DEPOSIT, WITHDRAWAL, TRANSFER
}

//Class used for storing information from/to be stored in
//transactions table
public class TransactionModel {
    //Private member variables to be accessed/changed through getter/setter functions
    private Integer id;
    private TransactionType transactionType;

    //No arg constructor
    public TransactionModel() {
    }

    //Constructor with all parameters
    public TransactionModel(Integer id, TransactionType type) {
        this.id = id;
        this.transactionType = type;
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
}
