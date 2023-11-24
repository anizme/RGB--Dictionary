package controller.panes;

public abstract class ActionController implements GeneralController {
    protected GeneralController container;

    public void setContainer(GeneralController container) {
        this.container = container;
    }
}
