package UI;

import CustomLists.CustomLinkedList;

import java.util.Scanner;

//View manager class that allows the application to navigate through different interfaces
public class ViewManager {
    //Singleton - only one view manager can exist, and as a static member of itself
    private static ViewManager viewManager;

    //Boolean member variable that determines whether views continue to render
    private boolean running;

    //Scanner that allows users to input data
    private final Scanner scanner;

    //List holding all registered views
    CustomLinkedList<View> viewList;
    //View that displays after the current one exits the render function
    View nextView;

    //No args constructor
    private ViewManager() {
        running = true;
        scanner = new Scanner(System.in);
        viewList = new CustomLinkedList<>();
    }

    //Function to return the view manager
    //Sets the static member variable if it has not been set
    public static ViewManager getViewManager() {
        if (viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    //Function to select the next view after the current one exits its render function
    public void navigate(String destination) {
        for(View view : viewList) {
            if (view.viewName.equals(destination)) {
                nextView = view;
            }
        }
    }

    //Function allowing new views to be added to the list
    public void registerView(View view) {
        viewList.add(view);
    }

    public void render() {
        nextView.renderView();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void quit() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
