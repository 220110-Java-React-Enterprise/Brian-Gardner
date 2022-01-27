package UI.BankAccountManagement;

import CRUD_Repo.AccountTransactionRepo;
import UI.DataStore;
import UI.View;
import UI.ViewManager;

public class BankAccountTransactionHistoryMenu extends View {
    //No args constructor sets view name and manager
    public BankAccountTransactionHistoryMenu() {
        viewName = "UI.TransactionMenus.BankAccountTransactionHistoryMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        //Repo used to display current account's transaction history
        AccountTransactionRepo accountTransactionRepo = new AccountTransactionRepo();

        if (DataStore.getAccountModel() != null) {
            System.out.println("\nTransaction history for account #" + DataStore.getAccountModel().getId() + ": ");
            accountTransactionRepo.printTransactionDetails(DataStore.getAccountModel().getId(), "account_id");
        }

        System.out.println();
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
