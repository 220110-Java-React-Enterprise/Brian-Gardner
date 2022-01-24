package UI.BankAccountManagement;

import UI.View;
import UI.ViewManager;

public class BankAccountInfoChangeMenu extends View {
    //No args constructor sets view name and manager
    public BankAccountInfoChangeMenu() {
        viewName = "UI.BankAccountManagement.BankAccountInfoChangeMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for BankAccountInfoChange menu");
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
