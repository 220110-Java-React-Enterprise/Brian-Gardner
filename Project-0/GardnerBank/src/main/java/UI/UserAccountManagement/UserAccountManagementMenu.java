package UI.UserAccountManagement;

import UI.View;
import UI.ViewManager;

//Menu for managing account information, such as name/password changes
public class UserAccountManagementMenu extends View {
    //No args constructor sets view name and manager
    public UserAccountManagementMenu() {
        viewName = "UI.UserAccountManagement.UserAccountManagementMenu";
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
            //Prompt user to select user account management option
            System.out.println("Please enter the number corresponding to the user account management task you want to perform" +
                    "(or 0 to exit)\n(1)Customer name change\n(2)Username change\n(3)Password change");
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
            switch (intInput) {
                case 0: viewManager.navigate("UI.MainMenu");
                    break;
                case 1: viewManager.registerView(new CustomerNameChangeMenu());
                    viewManager.navigate("UI.UserAccountManagement.CustomerNameChangeMenu");
                    break;
                case 2: viewManager.registerView(new CustomerUsernameChangeMenu());
                    viewManager.navigate("UI.UserAccountManagement.CustomerUsernameChangeMenu");
                    break;
                case 3: viewManager.registerView(new CustomerPasswordChangeMenu());
                    viewManager.navigate("UI.UserAccountManagement.CustomerPasswordChangeMenu");
                    break;
                default:
                    System.out.println("Error: Invalid integer. Enter 0-3.");
            }
        }
    }
}
