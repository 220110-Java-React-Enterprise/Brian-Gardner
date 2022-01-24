package UI.TransactionMenus;

import UI.View;
import UI.ViewManager;

public class WithdrawalMenu extends View {
    //No args constructor sets view name and manager
    public WithdrawalMenu() {
        viewName = "UI.TransactionMenus.WithdrawalMenu";
        viewManager = ViewManager.getViewManager();
    }

    //Function to render menu view
    @Override
    public void renderView() {
        System.out.println("Placeholder for Withdrawal menu");
        viewManager.navigate("UI.TransactionMenus.TransactionMenu");
    }
}
