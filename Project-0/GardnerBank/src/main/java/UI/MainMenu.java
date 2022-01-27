package UI;

import UI.BankAccountManagement.BankAccountManagementMenu;
import UI.TransactionMenus.TransactionMenu;
import UI.UserAccountManagement.UserAccountManagementMenu;

//Main menu for application, accessed after entering/creating customer account credentials
public class MainMenu extends View {
    //No-arg constructor
    public MainMenu() {
        viewName = "UI.MainMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";

        //User greetings
        System.out.println("\nWelcome, " + DataStore.getCustomerModel().getFullName());

        //Main loop that exits when 0 <= intInput <= 3
        while (intInput < 0 || intInput > 2) {
            //Prompt user to select submenu
            System.out.println("\nPlease enter the number corresponding to the submenu you want to navigate (or 0 to exit):" +
                    "\n(1)Bank Account Management\n(2)Complete/approve transaction");
            strInput = viewManager.getScanner().nextLine();

            //Test if user input empty string; return to loop start if so
            if (strInput.equals("")) {
                System.out.println("Error: input received an empty string.");
                continue;
            }

            //Attempt to turn string input into integer to store in intInput
            //return to loop beginning if it fails
            try {
                intInput = Integer.parseInt(strInput);
            } catch (Exception e) {
                System.out.println("Error: input received could not be parsed to integer.");
                continue;
            }

            //Switch statement to direct user to submenu or exit depending on input
            //return to loop start if invalid input
            switch (intInput) {
                case 0: viewManager.navigate("UI.StartMenu");
                    break;
                case 1: viewManager.registerView(new BankAccountManagementMenu());
                    viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
                    break;
                case 2: viewManager.registerView(new TransactionMenu());
                    viewManager.navigate("UI.TransactionMenus.TransactionMenu");
                    break;
                default:
                    System.out.println("Error: Invalid integer. Enter 0-2.");
            }
        }
    }
}
