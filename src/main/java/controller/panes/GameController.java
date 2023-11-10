package controller.panes;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class GameController extends ActionController {
    @FXML
    private ImageView backgroundImage;

    public ImageView getBackgroundImage() {
        return backgroundImage;
    }
}
