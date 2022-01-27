package UI;

import CRUD_Repo.CustomerRepo;
import CRUD_Repo.CustomerRepoCRUD;
import Models.CustomerModel;

//Menu prompting users to login to existing customer account stored in customers table
public class LoginMenu extends View {
    public LoginMenu() {
        //No-arg constructor; sets name and ViewManager reference
        viewName = "UI.LoginMenu";
        viewManager = ViewManager.getViewManager();
    }

    public void renderView() {
        //CustomerRepoCRUD used to check if username and password combination is valid
        CustomerRepo customerRepo = new CustomerRepo();

        //Variable to store number of steps left in login process
        int steps = 3;

        //Variables to store input as different types
        String strInput = "";
        String username = "";
        String password = "";
        CustomerModel tmpModel = new CustomerModel();

        while (steps > 1) {
            //Prompt user to enter login info
            //Inner loop to prompt collect username
            while (steps == 3) {
                System.out.println("\n(Enter 0 any time to exit)\nUsername: ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = 0;
                    break;
                }

                username = strInput;

                //Check if user entered existing username
                if (customerRepo.isUniqueUsername(username)) {
                    System.out.println("Username not found in customers table.");
                    continue;
                }

                tmpModel.setUsername(strInput);
                steps--;
            }

            while (steps == 2) {
                System.out.println("Password: ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = 0;
                    break;
                }

                password = strInput;

                //Check if username/password credential pair match in customers table
                if (!customerRepo.checkLoginCredentials(username, password)) {
                    System.out.println("Username/password credentials did not match in customers table.");
                    steps = 3;
                    break;
                }

                steps--;
            }

            //Additional verification of customer account existence in customer table
            if (steps == 1) {
                if (!customerRepo.read(username, tmpModel)) {
                    System.out.println("Reading customer with username " + username + " failed despite matching username/password");
                    steps = 3;
                    break;
                }
            }
        }

        //Add customer info to data store and navigate to main menu if login successful
        if (steps == 1) {
            DataStore.setCustomerModel(tmpModel);

            viewManager.registerView(new MainMenu());
            viewManager.navigate("UI.MainMenu");
        }
        //Return to start menu if user did not log in
        else {
            viewManager.navigate("UI.StartMenu");
        }
    }
}
