package UI.TransactionMenus;

import CRUD_Repo.AccountRepoCRUD;
import CRUD_Repo.AccountTransactionRepo;
import CRUD_Repo.TransactionRepoCRUD;
import Models.AccountModel;
import Models.AccountTransactionModel;
import Models.TransactionModel;
import UI.BankAccountManagement.BankAccountChangeCurrent;
import UI.DataStore;
import UI.View;
import UI.ViewManager;

//Menu to allow customers to deposit money into their accounts
public class DepositMenu extends View {
    //Static final variable to store max number of steps
    private static final int MAX_STEPS = 4;

    //No args constructor sets view name and manager
    public DepositMenu() {
        viewName = "UI.TransactionMenus.DepositMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //AccountTransactionRepo object used to create new deposit
        AccountTransactionRepo accountTransactionRepo = new AccountTransactionRepo();

        //Variable to store number of steps left in deposit process
        int steps = MAX_STEPS;

        //Variables to store input as different types
        int intInput = -1;
        double dblInput = 0.0;
        String strInput = "";

        //Create account models to be passed into CreateDeposit function
        AccountModel accountModel = DataStore.getAccountModel();
        AccountTransactionModel accountTransactionModel = new AccountTransactionModel();
        TransactionModel transactionModel = new TransactionModel();

        //Set account transaction type to deposit
        transactionModel.setTransactionType("DEPOSIT");

        //Skip to bank account change menu if no current bank account set in datastore
        if (DataStore.getAccountModel() == null) {
            steps = -2;
        }

        while (steps > 0) {
            while (steps == MAX_STEPS) {
                System.out.println(DataStore.getAccountModel() + "Deposit into current bank account or another? (enter 1-2; 0 to exit)\n" +
                        "(1)Current account\n(2)Use another account");
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

                //Switch statement to direct user to continue with current account or switch accounts
                //depending on input; return to loop start if invalid input
                switch (intInput) {
                    case 0: steps = -1;
                        break;
                    case 1: steps--;
                        break;
                    case 2: steps = -2;
                        break;
                    default:
                        System.out.println("Error: Invalid integer. Enter 0-2.");
                }
            }

            while (steps == MAX_STEPS - 1) {
                System.out.println("How much would you like to deposit? (enter x to exit)");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered x to exit
                if (strInput.equals("x")) {
                    steps = -1;
                    break;
                }

                //Attempt to turn string input into double to store in dblInput
                //return to loop start if it fails
                try {
                    dblInput = Double.parseDouble(strInput);
                } catch (Exception e) {
                    System.out.println("Error: input received could not be parsed to double.");
                    continue;
                }

                //Test if input was less than $0.01; return to loop start if so
                if (dblInput < .01) {
                    System.out.println("Error: cannot deposit less than $0.01");
                    continue;
                }

                //Set the AccountTransactionModel's amount to the double
                accountTransactionModel.setAmount(dblInput);
                steps--;
            }

            while (steps == MAX_STEPS - 2) {
                //Prompt user to enter transaction description
                System.out.println("Enter a description - this can be made of characters and spaces only, or left empty" +
                        "(enter 0 to exit):");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= transactionModel.setDescription(strInput) ? 1 : 0;
            }

            //Attempt to store transaction and accountTransaction information into respective tables
            if (steps == 1) {
                if (accountTransactionRepo.createFullAccountTransaction(accountModel, transactionModel, accountTransactionModel)) {
                    System.out.println(transactionModel + "\n...added to transactions table.");
                    System.out.println(accountTransactionModel + "\n...added to accounts_transactions table.");
                    System.out.println(accountModel + "\n...updated in accounts table.");

                    steps = 0;
                }
                else {
                    System.out.println("Failed to complete deposit.");

                    steps = -1;
                }
            }
        }

        //Exit DepositMenu, using steps variable to signal different codes for next menu
        //and setting datastore objects
        switch (steps) {
            //Code for selecting a different account than active
            case -2: DataStore.setLastViewName(this.viewName);
                viewManager.registerView(new BankAccountChangeCurrent());
                viewManager.navigate("UI.TransactionMenus.BankAccountChangeCurrent");
                break;
            //Code for exiting menu
            case -1: viewManager.navigate("UI.TransactionMenus.TransactionMenu");
                break;
            //Code for successfully creating deposit in SQL database
            case 0: DataStore.setTransactionModel(transactionModel);
                DataStore.setAccountModel(accountModel);
                DataStore.setAccountTransactionModel(accountTransactionModel);
                viewManager.navigate("UI.TransactionMenus.TransactionMenu");
                break;
        }
    }
}
