package UI.UserAccountManagement;

import UI.View;
import UI.ViewManager;

public class CustomerPasswordChangeMenu extends View {
    //No args constructor sets view name and manager
    public CustomerPasswordChangeMenu() {
        viewName = "UI.UserAccountManagement.CustomerPasswordChangeMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for CustomerPasswordChange menu");
        viewManager.navigate("UI.UserAccountManagement.UserAccountManagementMenu");
    }
}
