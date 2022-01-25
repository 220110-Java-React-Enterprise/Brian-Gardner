package UI;

//First menu shown - asks user whether they are a new or existing customer
public class StartMenu extends View {
    public StartMenu() {
        viewName = "UI.StartMenu";
        viewManager = ViewManager.getViewManager();
    }

    public void renderView() {
        //Set the DataStore's objects to null if logging out from the main menu
        DataStore.setCustomerModel(null);
        DataStore.setAccountModel(null);
        DataStore.setTransactionModel(null);

        //Variables to store input as different types
        int intInput = -1;
        String strInput = "";

        //Command-line heading
        System.out.println(" --- Welcome to Gardner Bank --- ");

        //Main loop that exits program when intInput = 0
        while (intInput < 0 || intInput > 2) {
            //Prompt user to select current or new customer
            //This navigates the user to either login or registration menu
            System.out.println("Are you a new or current customer? (Enter 1 or 2; 0 to exit)");
            System.out.println("(1): Current customer\n(2): New customer");

            strInput = viewManager.getScanner().nextLine();

            //Test if user input empty string; return to loop beginning if so
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

            //Switch statement to direct user to either login or registration menu depending on input
            //or return to loop if invalid input
            switch(intInput) {
                case 0: viewManager.quit();
                    break;
                case 1: viewManager.registerView(new LoginMenu());
                    viewManager.navigate("UI.LoginMenu");
                    break;
                case 2: viewManager.registerView(new RegistrationMenu());
                    viewManager.navigate("UI.RegistrationMenu");
                    break;
                default:
                    System.out.println("Error: Invalid integer. Enter 0-2.");
            }
        }
    }

}
