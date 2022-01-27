package UI;

//Abstract class holding basic member variables and functions for menu classes that extend it
public abstract class View {
    //Variable holding name of view
    protected String viewName;

    //Variable holding the reference to the view manager
    protected ViewManager viewManager;

    //Function to return name of the view calling it
    public String getViewName() {
        return this.viewName;
    }

    //Function to render the menu to user
    public abstract void renderView();
}
