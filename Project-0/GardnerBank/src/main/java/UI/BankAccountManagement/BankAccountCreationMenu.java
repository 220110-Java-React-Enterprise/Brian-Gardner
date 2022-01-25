package UI.BankAccountManagement;

import CRUD_Repo.AccountRepo;
import CRUD_Repo.CustomerAccountRepo;
import Models.AccountModel;
import Models.CustomerAccountModel;
import UI.DataStore;
import UI.View;
import UI.ViewManager;

public class BankAccountCreationMenu extends View {
    //Static final variable to store max number of steps
    private static final int MAX_STEPS = 3;

    //No args constructor sets view name and manager
    public BankAccountCreationMenu() {
        viewName = "UI.BankAccountManagement.BankAccountCreationMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //AccountRepo object used to create new Account
        AccountRepo accountRepo = new AccountRepo();

        //CustomerAccountRepo object used to create row in joint table connecting current user to new account
        CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();

        //Variable to store number of steps left in account creation process
        int steps = MAX_STEPS;

        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";
        AccountModel accountModel = new AccountModel();
        CustomerAccountModel customerAccountModel = new CustomerAccountModel();

        while (steps > 0) {
            //Prompt user to enter information to store in accounts table
            //Inner loop to collect account type from user
            while (steps == MAX_STEPS) {
                //Ask user what type of account they would like to create
                System.out.println("Which type of bank account would you like to create? (Enter 1-3; 0 to exit)\n" +
                        "(1): Checking\n(2): Savings\n(3): Head (holds other accounts within it)");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Test if user input empty string; return to loop start if so
                if (strInput.equals("")) {
                    System.out.println("Error: input received an empty string.");
                    continue;
                }

                //Attempt to turn string input into integer to store in intInput
                //return to loop start if it fails
                try {
                    intInput = Integer.parseInt(strInput);
                } catch (Exception e) {
                    System.out.println("Error: input received could not be parsed to integer.");
                    continue;
                }

                //Use AccountModel setter method to set account type based on integer
                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= accountModel.setAccountType(intInput) ? 1 : 0;
            }

            //Inner loop to collect account description from user
            while (steps == MAX_STEPS - 1) {
                //Prompt user to enter account description
                System.out.println("Enter a description - this can be made of characters and spaces only, or left empty" +
                        "(enter 0 to exit):");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= accountModel.setDescription(strInput) ? 1 : 0;
            }

            //Attempt to store account information into accounts table
            if (steps == 1) {
                if (accountRepo.create(accountModel)) {
                    System.out.println(accountModel + "\n...added to accounts table.");

                    customerAccountModel.setCustomerID(DataStore.getCustomerModel().getId());
                    customerAccountModel.setAccountID(accountModel.getId());
                    customerAccountModel.setApprovalNeeded(true);

                    if (customerAccountRepo.create(customerAccountModel)) {
                        System.out.println(customerAccountModel + "\n...added to customers_accounts table.");
                        steps--;
                    }
                    else {
                        System.out.println("Failed to add customer-account link to joint customers_accounts table.");
                    }
                }
                else {
                    System.out.println("Failed to add account info to accounts table.");
                }
            }
        }
        if (steps == 0) {
            DataStore.setAccountModel(accountModel);

            viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
        }
    }
}
