import InputOutputFunctions.InputValidation;
import UI.StartMenu;
import UI.ViewManager;

public class Main {
    public static void main(String ...args) {
        //Initialize ViewManager
        ViewManager viewManager = ViewManager.getViewManager();

        //Add start menu to view manager and set it to next view
        viewManager.registerView(new StartMenu());
        viewManager.navigate("UI.StartMenu");

        //Begin rendering view manager
        while(viewManager.isRunning()) {
            viewManager.render();
        }
    }
}
