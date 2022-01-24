package UI;

import CustomLists.CustomLinkedList;

import java.util.Scanner;

public class ViewManager {
    private static ViewManager viewManager;
    private boolean running;
    private final Scanner scanner;

    CustomLinkedList<View> viewList;
    View nextView;

    private ViewManager() {
        running = true;
        scanner = new Scanner(System.in);
        viewList = new CustomLinkedList<>();
    }

    public static ViewManager getViewManager() {
        if (viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    public void navigate(String destination) {
        for(View view : viewList) {
            if (view.viewName.equals(destination)) {
                nextView = view;
            }
        }
    }

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
