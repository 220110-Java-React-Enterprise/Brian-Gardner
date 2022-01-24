package UI.BankAccountManagement;

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
        System.out.println("Placeholder for BankAccountTransactionHistory menu");
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
