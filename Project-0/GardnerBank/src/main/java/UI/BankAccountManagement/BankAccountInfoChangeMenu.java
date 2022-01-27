package UI.BankAccountManagement;

import CustomLists.CustomArrayList;
import Models.AccountType;
import UI.DataStore;
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
        if (DataStore.getAccountModel().getAccountType() == AccountType.HEAD) {
            CustomArrayList<String> accountStrings = new CustomArrayList<>();

            DataStore.getAccountModel().getSubAccountsStringsRecursive(accountStrings, 0);

            for (int i = 0; i < accountStrings.size(); i++) {
                System.out.println(accountStrings.get(i));
            }
        }

        System.out.println("Placeholder for BankAccountInfoChange menu");
        viewManager.navigate("UI.BankAccountManagement.BankAccountManagementMenu");
    }
}
