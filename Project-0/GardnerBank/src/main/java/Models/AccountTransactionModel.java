package Models;

import InputValidation.CurrencyFormatter;

//Class used for storing information in/retrieving information from the joint accounts_transactions table
public class AccountTransactionModel {
    //Space for potential static members/functions

    //Private member variables to be accessed/changed through getter/setter methods
    private Integer accountId;
    private Integer transactionId;
    private Double amount;

    //No arg constructor
    public AccountTransactionModel() {

    }

    //Constructor with all parameters
    public AccountTransactionModel(Integer accountId, Integer transactionId, Double amount) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    //Getter/setter methods to access and changed private member variables
    public Integer getAccountId() {
        return accountId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAmountString() { return CurrencyFormatter.format(getAmount()); }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    //Override of Object.toString() method to automatically convert AccountTransactionModel object to String
    //with all member variables present
    @Override
    public String toString() {
        return "Account #" + this.getAccountId() + ": Transaction #" + this.getTransactionId() + ": " + this.getAmountString();
    }
}
