package UI.TransactionMenus;

import UI.View;
import UI.ViewManager;

//Menu to allow users to choose between depositing, withdrawing, transferring and approving transactions
public class TransactionMenu extends View {
    //No args constructor sets view name and manager
    public TransactionMenu() {
        viewName = "UI.TransactionMenus.TransactionMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";

        //Main loop that exits when 0 <= intInput <= 3
        while (intInput < 0 || intInput > 3) {
            //Prompt user to select transaction option
            System.out.println("Please enter the number corresponding to the transaction you want to perform (or 0 to exit)" +
                    "\n(1)Deposit\n(2)Withdrawal\n(3)Transfer");
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

            //Switch statement to direct user to task depending on input
            //return to loop start if invalid input
            switch(intInput) {
                case 0: viewManager.navigate("UI.MainMenu");
                    break;
                case 1: viewManager.registerView(new DepositMenu());
                    viewManager.navigate("UI.TransactionMenus.DepositsMenu");
                    break;
                case 2: viewManager.registerView(new WithdrawalMenu());
                    viewManager.navigate("UI.TransactionMenus.WithdrawalMenu");
                    break;
                case 3: viewManager.registerView(new TransferMenu());
                    viewManager.navigate("UI.TransactionMenus.TransferMenu");
                    break;
                default:
                    System.out.println("Error: Invalid integer. Enter 0-4.");
            }
        }
    }
}
