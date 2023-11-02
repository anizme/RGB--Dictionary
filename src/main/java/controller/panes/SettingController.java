package controller.panes;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SettingController extends ActionController {
    @FXML
    private ImageView backgroundView;

    public ImageView getBackgroundView() {
        return backgroundView;
    }
}
