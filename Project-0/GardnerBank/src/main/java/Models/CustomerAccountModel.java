package Models;

//Class used for storing information in/retrieving information from the joint customers_account table
public class CustomerAccountModel {
    //Space for potential static members/functions

    //CustomerAccountModel private member variables to be accessed/changed through getter/setter methods
    private Integer customerID;
    private Integer accountID;
    private Boolean approvalNeeded;

    //No arg constructor
    public CustomerAccountModel() {
    }

    //Constructor with all parameters
    public CustomerAccountModel(Integer customerID, Integer accountID, Boolean approvalNeeded) {
        this.customerID = customerID;
        this.accountID = accountID;
        this.approvalNeeded = approvalNeeded;
    }

    //Getter/setter methods to access and change private member variables
    public Integer getCustomerID() {
        return customerID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public Boolean getApprovalNeeded() {
        return approvalNeeded;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public void setApprovalNeeded(Boolean approvalNeeded) {
        this.approvalNeeded = approvalNeeded;
    }

    //Override of Object.toString() method to automatically convert CustomerAccountModel object to String
    //with all member variables present
    @Override
    public String toString() {
        String approval = this.approvalNeeded ? " - approval needed" : "";
        return "Customer #" + this.getCustomerID() + ", Account #" + this.getAccountID() + approval;
    }
}
