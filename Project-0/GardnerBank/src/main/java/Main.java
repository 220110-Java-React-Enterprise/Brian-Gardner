import CRUD_Repo.CustomerAccountRepo;
import InputValidation.InputValidation;
import Models.CustomerAccountModel;
import UI.StartMenu;
import UI.ViewManager;

public class Main {
    public static void main(String ...args) {
        //Testing out joint repos
        CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();
        CustomerAccountModel model = new CustomerAccountModel();
        model.setAccountID(1000);
        model.setCustomerID(1);
        model.setApprovalNeeded(true);


        System.out.println(customerAccountRepo.create(model));

        //Initialize ViewManager
        ViewManager viewManager = ViewManager.getViewManager();

        viewManager.registerView(new StartMenu());
        viewManager.navigate("UI.StartMenu");

        while(viewManager.isRunning()) {
            viewManager.render();
        }

/*
        Connection connection = CRUD_Repo.ConnectionManager.getConnection();

        Models.CustomerModel bran = new Models.CustomerModel(1, "Bran", "Axl", "Gerd", "bgard", "securepword");

        CRUD_Repo.CustomerRepo customerRepo = new CRUD_Repo.CustomerRepo();

        System.out.println("Creating...");
        customerRepo.create(bran);
        System.out.println("Created. Enter to continue");

        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        System.out.println("Updating...");
        bran.setGivenName("bren");
        System.out.println("Updated. Enter to continue");

        sc.nextLine();

        System.out.println("Customer with id 1: ");
        Models.CustomerModel queryCustomer = customerRepo.read(1);
        System.out.println("name: " + queryCustomer.getGivenName() + " " + queryCustomer.getSurname());
        System.out.println("Enter to continue.");

        sc.nextLine();

        System.out.println("Deleting...");
        customerRepo.delete(1);
        System.out.println("Deleted. Enter to continue");

        sc.nextLine();
*/
    }
}
