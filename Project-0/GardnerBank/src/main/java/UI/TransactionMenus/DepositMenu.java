package UI.TransactionMenus;

import UI.View;
import UI.ViewManager;

//Menu to allow customers to deposit money into their accounts
public class DepositMenu extends View {
    //No args constructor sets view name and manager
    public DepositMenu() {
        viewName = "UI.TransactionMenus.DepositsMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for deposit menu");
        viewManager.navigate("UI.TransactionMenus.TransactionMenu");
    }
}
