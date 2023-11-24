package controller.panes;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class ActionController implements GeneralController {
    protected GeneralController container;

    public void setContainer(GeneralController container) {
        this.container = container;
    }
}
