package UI.BankAccountManagement;

import UI.View;
import UI.ViewManager;

//Menu allowing users to view and alter bank accounts connected to their user account
public class BankAccountManagementMenu extends View {
    //No args constructor sets view name and manager
    public BankAccountManagementMenu() {
        viewName = "UI.BankAccountManagement.BankAccountManagementMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";

        //Main loop that exits when 0 <= intInput <= 4
        while (intInput < 0 || intInput > 4) {
            //Prompt user to select bank account management option
            System.out.println("Please enter the number corresponding to the bank account management task you want to perform" +
                    "(or 0 to exit)\n(1)Create bank account\n(2)Change bank account info\n(3)Change bank account ownership\n" +
                    "(4)View bank account transaction history");
            strInput = viewManager.getScanner().nextLine();

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

            //Switch statement to direct user to task depending on input
            //return to loop start if invalid input
            switch (intInput) {
                case 0: viewManager.navigate("UI.MainMenu");
                    break;
                case 1: viewManager.registerView(new BankAccountCreationMenu());
                    viewManager.navigate("UI.BankAccountManagement.BankAccountCreationMenu");
                    break;
                case 2: viewManager.registerView(new BankAccountInfoChangeMenu());
                    viewManager.navigate("UI.BankAccountManagement.BankAccountInfoChangeMenu");
                    break;
                case 3: viewManager.registerView(new BankAccountOwnershipChangeMenu());
                    viewManager.navigate("UI.BankAccountManagement.BankAccountOwnershipChangeMenu");
                    break;
                case 4: viewManager.registerView(new BankAccountTransactionHistoryMenu());
                    viewManager.navigate("UI.TransactionMenus.BankAccountTransactionHistoryMenu");
                    break;
                default:
                    System.out.println("Error: Invalid integer. Enter 0-4.");
            }
        }
    }
}
