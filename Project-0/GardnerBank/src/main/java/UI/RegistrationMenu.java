package UI;

import CRUD_Repo.CustomerRepo;
import CRUD_Repo.CustomerRepoCRUD;
import Models.CustomerModel;

//Menu prompting users to create a new customer account stored in customers table
public class RegistrationMenu extends View {
    //Static member variable holding max number of steps registration menu takes
    private static final int MAX_STEPS = 6;

    //No-arg constructor; sets name and ViewManager reference
    public RegistrationMenu() {
        viewName = "UI.RegistrationMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render the menu to user
    public void renderView() {
        //CustomerRepoCRUD object used to check if username is unique
        CustomerRepo customerRepo = new CustomerRepo();

        //Variable to store number of steps left in registration process
        int steps = MAX_STEPS;

        //Variables to store input as different types
        String strInput = "";
        CustomerModel customerModel = new CustomerModel();

        while (steps > 0) {
            //Prompt user to enter information to store in customers table
            //Inner loop to collect given/first name from user
            while (steps == MAX_STEPS) {
                System.out.println("\n(Enter 0 any time to exit)\nGiven name/first name: ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= customerModel.setGivenName(strInput) ? 1 : 0;
            }

            //Inner loop to collect middle name from user
            while (steps == MAX_STEPS - 1) {
                System.out.println("Middle name (leave blank if not applicable): ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= customerModel.setMiddleName(strInput) ? 1 : 0;
            }

            //Inner loop to collect surname/last name from user
            while (steps == MAX_STEPS - 2) {
                System.out.println("Surname/last name: ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= customerModel.setSurname(strInput) ? 1 : 0;
            }

            //Inner loop to prompt user for a username
            while (steps == MAX_STEPS - 3) {
                System.out.println("Enter desired username: ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Check if user entered a unique username
                if (!customerRepo.isUniqueUsername(strInput)) {
                    System.out.println("Error - username not found to be unique in customers table. Please enter a unique username.");
                    continue;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= customerModel.setUsername(strInput) ? 1 : 0;
            }

            //Inner loop to prompt user for a password
            while (steps == MAX_STEPS - 4) {
                System.out.println("Enter password: ");
                strInput = viewManager.getScanner().nextLine();

                //Check if user entered 0 to exit
                if (strInput.equals("0")) {
                    steps = -1;
                    break;
                }

                //Reduce steps by 1 if validation & setter method successful, otherwise loop again
                steps -= customerModel.setPassword(strInput) ? 1 : 0;
            }

            //Attempt to store user information into customers table
            if (steps == 1) {
                if (customerRepo.create(customerModel)) {
                    System.out.println(customerModel + "\n...added to customers table.");
                    steps--;
                }
                else {
                    System.out.println("Failed to add customer info to customers table.");
                }
            }
        }

        //Add customer info to data store and navigate to main menu if registration successful
        if (steps == 0) {
            DataStore.setCustomerModel(customerModel);

            viewManager.registerView(new MainMenu());
            viewManager.navigate("UI.MainMenu");
        }
        //Return to start menu if registration was not successful
        else {
            viewManager.navigate("UI.StartMenu");
        }
    }
}
