package UI;

public class SubMenu extends View {
    public SubMenu() {
        viewName = "UI.SubMenu";
        viewManager = ViewManager.getViewManager();
    }

    public void renderView() {
        System.out.println("Sub Menu");
        System.out.println("Welcome, " + DataStore.getName());

        viewManager.quit();
    }
}
