package controller.panes;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SettingController extends ActionController {
    @FXML
    private ImageView backgroundView;
    @FXML
    private JFXToggleButton switchMode;

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public JFXToggleButton getSwitchMode() {
        return switchMode;
    }
}
