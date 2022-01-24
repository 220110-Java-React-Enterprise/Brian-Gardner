package UI.TransactionMenus;

import UI.View;
import UI.ViewManager;

public class TransferMenu extends View {
    //No args constructor sets view name and manager
    public TransferMenu() {
        viewName = "UI.TransactionMenus.TransferMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {


        System.out.println("Placeholder for Transfer menu");
        viewManager.navigate("UI.TransactionMenus.TransactionMenu");
    }
}
