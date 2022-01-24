package UI.BankAccountManagement;

import UI.View;
import UI.ViewManager;

public class BankAccountOwnershipChangeMenu extends View {
    //No args constructor sets view name and manager
    public BankAccountOwnershipChangeMenu() {
        viewName = "UI.BankAccountManagement.BankAccountOwnershipChangeMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for BankAccountOwnershipChange menu");
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
