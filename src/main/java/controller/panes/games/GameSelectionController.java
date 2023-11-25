package controller.panes.games;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSelectionController extends GameAction implements Initializable {
    @FXML
    private ImageView defaultGame;

    @FXML
    private ImageView crossWordGame;
    @FXML
    private JFXButton crossWord;

    @FXML
    private ImageView GAME2;
    @FXML
    private JFXButton game2;


    @FXML
    private ImageView GAME3;
    @FXML
    private JFXButton game3;

    @FXML
    private AnchorPane gameContainer;

    @FXML
    void btCrossWord(ActionEvent event) {
        gameControllerContainer.showCrossWord();
    }

    @FXML
    void btGame2(ActionEvent event) {
        gameControllerContainer.showHangman();
    }

    @FXML
    void btGame3(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
