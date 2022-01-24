package UI;

public abstract class View {
    protected String viewName;
    protected ViewManager viewManager;

    public String getViewName() {
        return this.viewName;
    }

    public abstract void renderView();
}
