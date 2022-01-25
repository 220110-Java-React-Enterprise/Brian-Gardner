package UI.BankAccountManagement;

import CRUD_Repo.AccountRepo;
import CRUD_Repo.CustomerAccountRepo;
import CustomLists.CustomArrayList;
import Models.AccountModel;
import Models.CustomerAccountModel;
import UI.DataStore;
import UI.View;
import UI.ViewManager;

public class BankAccountChangeCurrent extends View {
    //Static final variable to store max number of steps
    private static final int MAX_STEPS = 2;

    //Member variable to store the calling menu's name
    private String callingViewName;

    //No args constructor sets view name and manager
    public BankAccountChangeCurrent() {
        viewName = "UI.TransactionMenus.BankAccountChangeCurrent";
        viewManager = ViewManager.getViewManager();
        callingViewName = "UI.BankAccountManagement.BankAccountManagementMenu";
    }

    //Constructor with callingViewName argument to store which view to return to
    public BankAccountChangeCurrent(String callingViewName) {
        super();
        this.callingViewName = callingViewName;
    }

    //Getter/setter methods for callingMenuName member string
    public String getCallingViewName() {
        return callingViewName;
    }

    public void setCallingViewName(String callingViewName) {
        this.callingViewName = callingViewName;
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //AccountRepo to retrieve data of account user is switching to
        AccountRepo accountRepo = new AccountRepo();

        //CustomerAccountRepo object used to retrieve all accounts belonging to a customer
        CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();

        //Variable to store the number of steps left in account selection process
        int steps = MAX_STEPS;

        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";
        boolean foundAccountId = false;
        AccountModel accountModel = new AccountModel();

        while (steps > 0) {
            while (steps == MAX_STEPS) {
                CustomArrayList<CustomerAccountModel> customerAccountModels = new CustomArrayList<>();

                if (!customerAccountRepo.readByCustomerId(DataStore.getCustomerModel().getId(), customerAccountModels)) {
                    System.out.println("Read from customers_accounts table failed. Returning to last menu.");
                    steps = -1;
                }

                for (int i = 0; i < customerAccountModels.size(); i++) {
                    System.out.println(customerAccountModels.get(i));
                }

                System.out.println("(Enter 0 to exit)\nWhich bank account would you like to switch to using? Enter id#: ");
                strInput = viewManager.getScanner().nextLine();

                //Attempt to turn string input into integer to store in intInput
                //return to loop start if it fails
                try {
                    intInput = Integer.parseInt(strInput);
                } catch (Exception e) {
                    System.out.println("Error: input received could not be parsed to integer.");
                    continue;
                }

                if (intInput == 0) {
                    steps = -1;
                    break;
                }

                for (int i = 0; i < customerAccountModels.size(); i++) {
                    if (customerAccountModels.get(i).getAccountID() == intInput) {
                        foundAccountId = true;
                        break;
                    }
                }
                if (foundAccountId) {
                    System.out.println("Switching to account #" + intInput);
                    steps--;
                }
                else {
                    System.out.println("Could not find account #" + intInput + " in list of linked accounts.");
                }
            }
            if (steps == 1) {
                if (!accountRepo.read(intInput, accountModel)) {
                    System.out.println("Failed to read account #" + intInput + " from accounts table.");
                    steps = -1;
                }
                else {
                    DataStore.setAccountModel(accountModel);
                    System.out.println("Account successfully switched.");
                    steps--;
                }
            }
        }

        System.out.println("BankAccountChangeCurrent menu placeholder");
        viewManager.navigate(DataStore.getLastViewName());
    }
}
