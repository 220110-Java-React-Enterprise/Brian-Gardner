package UI.BankAccountManagement;

import UI.View;
import UI.ViewManager;

public class BankAccountCreationMenu extends View {
    //No args constructor sets view name and manager
    public BankAccountCreationMenu() {
        viewName = "UI.BankAccountManagement.BankAccountCreationMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for BankAccountCreation menu");
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
