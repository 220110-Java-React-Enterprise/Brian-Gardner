package UI.BankAccountManagement;

import CRUD_Repo.CustomerAccountRepo;
import CRUD_Repo.CustomerRepo;
import CustomLists.CustomArrayList;
import Models.AccountModel;
import Models.CustomerAccountModel;
import Models.CustomerModel;
import UI.DataStore;
import UI.View;
import UI.ViewManager;

public class BankAccountOwnershipChangeMenu extends View {
    //Static final variable to store max number of steps
    private static final int MAX_STEPS = 3;

    //No args constructor sets view name and manager
    public BankAccountOwnershipChangeMenu() {
        viewName = "UI.BankAccountManagement.BankAccountOwnershipChangeMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //CustomerRepo used to look up other existing customer names
        CustomerRepo customerRepo = new CustomerRepo();

        //CustomerAccountRepo used to look up accounts currently existing under user and
        //add a new row
        CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();

        //Variable to store number of steps left in account ownership change process
        int steps = MAX_STEPS;

        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";
        boolean foundAccountId = false;
        boolean foundCustomerId = false;
        AccountModel accountModel = new AccountModel();
        CustomerAccountModel customerAccountModel = new CustomerAccountModel();

        while (steps > 0) {
            //Step one - ask user which account they would like to share
            while (steps == MAX_STEPS) {
                //Create empty list of customer-account models
                CustomArrayList<CustomerAccountModel> customerAccountModels = new CustomArrayList<>();

                //Attempt reading list of accounts linked to customer
                if (!customerAccountRepo.read(DataStore.getCustomerModel().getId(), "customer_id", customerAccountModels)) {
                    System.out.println("Read from customers_accounts table failed. Returning to last menu.");
                    steps = -1;
                }

                //Direct customer back to bank management menu if no accounts found
                if (customerAccountModels == null || customerAccountModels.size() == 0) {
                    System.out.println("No accounts found under current user. Redirecting to bank management menu.");
                    viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
                    return;
                }

                //Print out accounts linked to current user
                for (int i = 0; i < customerAccountModels.size(); i++) {
                    System.out.println(customerAccountModels.get(i));
                }

                //Prompt user to enter id of account to share with another user
                System.out.println("(Enter 0 to exit)\nWhich bank account would you like to share? Enter id#: ");
                strInput = viewManager.getScanner().nextLine();

                //Attempt to turn string input into integer to store in intInput
                //return to loop start if it fails
                try {
                    intInput = Integer.parseInt(strInput);
                } catch (Exception e) {
                    System.out.println("Error: input received could not be parsed to integer.");
                    continue;
                }

                //Exit menu if user entered 0
                if (intInput == 0) {
                    steps = -1;
                    break;
                }

                //Look through list of customer-account links for id
                for (int i = 0; i < customerAccountModels.size(); i++) {
                    if (customerAccountModels.get(i).getAccountID() == intInput) {
                        foundAccountId = true;
                        accountModel.setId(intInput);
                        customerAccountModel.setAccountID(intInput);
                        steps--;
                        break;
                    }
                }

                if (!foundAccountId) {
                    System.out.println("Could not find account #" + intInput + " in list of linked accounts.");
                }
            }

            //Step two - ask user which user to share with
            while (steps == MAX_STEPS - 1) {
                //Create empty list of customers to check id against
                CustomArrayList<CustomerModel> customerModels = new CustomArrayList<>();

                //Fill list of customers
                if (!customerRepo.readAll(customerModels)) {
                    System.out.println("Failed to read customer table");
                    steps = -1;
                    break;
                }

                //Print other users 'safe' information to console
                customerRepo.printAllClean();

                //Ask user which user they would like to share the account with
                System.out.println("Which user would you like to share this account with? Enter id#: ");
                strInput = viewManager.getScanner().nextLine();

                //Attempt to turn string input into integer to store in intInput
                //return to loop start if it fails
                try {
                    intInput = Integer.parseInt(strInput);
                } catch (Exception e) {
                    System.out.println("Error: input received could not be parsed to integer.");
                    continue;
                }

                //Exit if user enters 0
                if (intInput == 0) {
                    steps = -1;
                    break;
                }

                //Look through list of customers for id
                for (int i = 0; i < customerModels.size(); i++) {
                    if (customerModels.get(i).getId() == intInput) {
                        foundCustomerId = true;
                        customerAccountModel.setCustomerID(intInput);
                        steps--;
                        break;
                    }
                }

                if (!foundCustomerId) {
                    System.out.println("Customer id not found in table.");
                }
            }

            //Final step - create customer-account link
            if (steps == 1) {
                customerAccountModel.setApprovalNeeded(false);

                //Attempt to create the customer-account link
                if (!customerAccountRepo.create(customerAccountModel)) {
                    System.out.println("Failed to create customer-account link");
                    steps = -1;
                }
                else {
                    System.out.println("Customer account link successfully created");
                    steps--;
                }
            }
        }

        //Return user to bank management menu
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
