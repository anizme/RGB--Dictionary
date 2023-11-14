package controller.panes;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class ActionController {
    protected ContainerController state;

    public void initData(ContainerController state) {
        this.state = state;
    }
}
