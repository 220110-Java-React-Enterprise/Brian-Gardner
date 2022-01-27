import CRUD_Repo.AccountRepo;
import CRUD_Repo.AccountTransactionRepo;
import CRUD_Repo.CustomerRepo;
import CRUD_Repo.TransactionRepoCRUD;
import CustomLists.CustomArrayList;
import Models.AccountTransactionModel;
import Models.TransactionModel;
import UI.StartMenu;
import UI.ViewManager;

public class Main {
    public static void main(String ...args) {

        //Initialize ViewManager
        ViewManager viewManager = ViewManager.getViewManager();

        viewManager.registerView(new StartMenu());
        viewManager.navigate("UI.StartMenu");

        while(viewManager.isRunning()) {
            viewManager.render();
        }

/*
double doubles[] = new double[20];
        doubles[0] = -.005;
        for (int i = 1; i < 20; i++) {
            doubles[i] = doubles[i - 1] * 7;
        }
        for (int i = 0; i < 20; i++) {
            System.out.println(OutputFormatter.format(doubles[i]));
        }
        Connection connection = CRUD_Repo.ConnectionManager.getConnection();

        Models.CustomerModel bran = new Models.CustomerModel(1, "Bran", "Axl", "Gerd", "bgard", "securepword");

        CRUD_Repo.CustomerRepoCRUD customerRepo = new CRUD_Repo.CustomerRepoCRUD();

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
