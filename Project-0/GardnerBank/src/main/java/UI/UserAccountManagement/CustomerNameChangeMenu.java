package UI.UserAccountManagement;

import UI.View;
import UI.ViewManager;

public class CustomerNameChangeMenu extends View {
    //No args constructor sets view name and manager
    public CustomerNameChangeMenu() {
        viewName = "UI.UserAccountManagement.CustomerNameChangeMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for CustomerNameChange menu");
        viewManager.navigate("UI.UserAccountManagement.UserAccountManagementMenu");
    }
}
