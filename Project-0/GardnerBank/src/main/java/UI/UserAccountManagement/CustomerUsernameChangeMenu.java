package UI.UserAccountManagement;

import UI.View;
import UI.ViewManager;

public class CustomerUsernameChangeMenu extends View {
    //No args constructor sets view name and manager
    public CustomerUsernameChangeMenu() {
        viewName = "UI.UserAccountManagement.CustomerUsernameChangeMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for CustomerUsernameChange menu");
        viewManager.navigate("UI.UserAccountManagement.UserAccountManagementMenu");
    }
}
