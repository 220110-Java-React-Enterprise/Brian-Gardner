package UI.BankAccountManagement;

import CRUD_Repo.AccountRepo;
import CRUD_Repo.CustomerAccountRepo;
import CustomLists.CustomArrayList;
import InputOutputFunctions.OutputFormatter;
import Models.AccountModel;
import Models.CustomerAccountModel;
import UI.DataStore;
import UI.View;
import UI.ViewManager;

//Menu to close a bank account
public class BankAccountCloseMenu extends View {
    //Static final variable to store max number of steps
    private static final int MAX_STEPS = 3;
    //No args constructor sets view name and manager
    public BankAccountCloseMenu() {
        viewName = "UI.BankAccountManagement.BankAccountCloseMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //AccountRepo object used to create new Account
        AccountRepo accountRepo = new AccountRepo();

        //CustomerAccountRepo object used to retrieve all accounts belonging to a customer
        CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();

        //Variable to store the number of steps left in account deletion process
        int steps = MAX_STEPS;

        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";
        boolean foundAccountId = false;
        AccountModel accountModel = new AccountModel();

        while (steps > 0) {
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

                //Prompt user to enter id of account to close
                System.out.println("(Enter 0 to exit)\nWhich bank account would you like to use? Enter id#: ");
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

                //Search for account id in list of linked accounts
                for (int i = 0; i < customerAccountModels.size(); i++) {
                    if (customerAccountModels.get(i).getAccountID() == intInput) {
                        //Read account info from table if found in list
                        if (accountRepo.read(intInput, accountModel)) {
                            steps--;
                        }
                        else {
                            System.out.println("Error: account id found in list but not table.");
                        }
                        //Exit loop if account found
                        break;
                    }
                }
            }

            //Step to test whether account has a balance of at least .01
            while (steps == MAX_STEPS - 1) {
                double tmpBalance = accountModel.getBalance();

                //Test if balance is at least .01
                if (tmpBalance >= .01) {
                    //Ask user if they want to close account regardless
                    System.out.println("WARNING: account has balance of " + OutputFormatter.formatCurrency(tmpBalance) +
                            ". Are you sure you want to close account before withdrawing?\n(1)Yes\n(2)No");
                    strInput = viewManager.getScanner().nextLine();

                    //Attempt to turn string input into integer to store in intInput
                    //return to loop start if it fails
                    try {
                        intInput = Integer.parseInt(strInput);
                    } catch (Exception e) {
                        System.out.println("Error: input received could not be parsed to integer.");
                        continue;
                    }

                    //Set steps depending on whether user wants to continue with account deletion
                    switch (intInput) {
                        case 0: steps = -1;
                            break;
                        case 2: steps = MAX_STEPS;
                            break;
                        default: System.out.println("Error: Invalid integer. Enter 0-4.");
                    }
                }

                //Ensure user absolutely wants to close account
                if (steps == MAX_STEPS - 1) {
                    System.out.println("Enter 1 to delete account (WARNING - THIS CANNOT BE UNDONE.");
                    strInput = viewManager.getScanner().nextLine();

                    //Attempt to turn string input into integer to store in intInput
                    //return to loop start if it fails
                    try {
                        intInput = Integer.parseInt(strInput);
                    } catch (Exception e) {
                        System.out.println("Error: input received could not be parsed to integer.");
                        continue;
                    }

                    //Set steps depending on whether user wants to continue with account deletion
                    switch (intInput) {
                        case 1: steps--;
                            break;
                        default:
                            System.out.println("Account deletion cancelled.");
                            steps = MAX_STEPS;
                    }
                }
            }

            //Final step - attempt to delete account from table
            if (steps == 1) {
                if (!accountRepo.delete(accountModel.getId())) {
                    System.out.println("Delete failed.");
                }
                else {
                    System.out.println("Deletion succeeded.");
                    steps--;
                }
            }
        }

        //Direct user back to account management menu
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
