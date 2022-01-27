package UI;

import CRUD_Repo.CustomerRepo;
import CRUD_Repo.CustomerRepoCRUD;
import Models.CustomerModel;

//Menu prompting users to create a new customer account stored in customers table
public class RegistrationMenu extends View {
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
        int steps = 6;

        //Variables to store input as different types
        String strInput = "";
        CustomerModel customerModel = new CustomerModel();

        while (steps > 0) {
            //Prompt user to enter information to store in customers table
            //Inner loop to collect given/first name from user
            while (steps == 6) {
                System.out.println("(Enter 0 any time to exit)\nGiven name/first name: ");
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
            while (steps == 5) {
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
            while (steps == 4) {
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
            while (steps == 3) {
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
            while (steps == 2) {
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
        if (steps == 0) {
            DataStore.setCustomerModel(customerModel);

            viewManager.registerView(new MainMenu());
            viewManager.navigate("UI.MainMenu");
        }
        else {
            viewManager.navigate("UI.StartMenu");
        }
    }
/*
    //Function to check if a string is valid name
    //Checks if name length falls within given integers and if all characters are alphabetical
    public boolean isValidName(String name, int minSize, int maxSize) {
        if (name.length() < minSize || name.length() > maxSize ) {
            return false;
        }
        char tempChar = ' ';

        for (int i = 0; i < name.length(); i++) {
            tempChar = name.charAt(i);
            if ((tempChar < 65 || tempChar > 90) && (tempChar < 97 || tempChar > 122)) {
                return false;
            }
        }
        return true;
    }

    //Function to check if a string is valid potential username
    //Checks if string length falls within given integers and if characters are all alphabetical or numeric
    public boolean isValidUsername(String username, int minSize, int maxSize) {
        if (username.length() < minSize || username.length() > maxSize) {
            return false;
        }
        char tempChar = ' ';

        for (int i = 0; i < username.length(); i++) {
            tempChar = username.charAt(i);

            if ((tempChar < 48 || tempChar > 57) && (tempChar < 65 || tempChar > 90) && (tempChar < 97 || tempChar > 122)) {
                return false;
            }
        }
        return true;
    }

    //Function to check if a string is a unique username
    public boolean isUniqueUsername(String username, CustomerRepoCRUD customerRepo) {
        CustomArrayList<String> usernames = new CustomArrayList<>();

        boolean readSuccessful = customerRepo.readUsernames(usernames);

        if (readSuccessful) {
            for (int i = 0; i < usernames.size(); i++) {
                if (username.equals(usernames.get(i))) {
                    return false;
                }
            }
            return true;
        }

        System.out.println("Read unsuccessful.");
        return false;
    }

    //Function tot check if string is valid password
    //Checks if string length falls within given integers and if characters are all alphabetical or numeric
    public boolean isValidPassword(String password, int minSize, int maxSize) {
        if (password.length() < minSize || password.length() > maxSize) {
            return false;
        }
        char tempChar = ' ';

        for (int i = 0; i < password.length(); i++) {
            tempChar = password.charAt(i);

            if ((tempChar < 48 || tempChar > 57) && (tempChar < 65 || tempChar > 90) && (tempChar < 97 || tempChar > 122)) {
                return false;
            }
        }
        return true;
    }

 */
}
