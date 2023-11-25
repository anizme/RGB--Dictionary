package controller.panes.games;

import controller.panes.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSelectionController extends GameController implements Initializable {
    @FXML
    void btCrossWord(ActionEvent event) {
        ((GameController) container).showCrossWord();
    }

    @FXML
    void btGame2(ActionEvent event) {
        ((GameController) container).showHangman();
    }

    @FXML
    void btGame3(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
