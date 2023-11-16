package controller.panes;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.games.CrossWord;
import controller.panes.games.GameSelectionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends ActionController implements Initializable {

    @FXML
    private JFXButton btBack;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane gameContainer;

    protected AnchorPane selectGame;
    protected GameSelectionController gameSelectionController;

    protected AnchorPane crossWordPane;
    protected CrossWord crossWordController;

    @FXML
    void gameMenu(ActionEvent event) {
        showGamePane();
    }

    private void setGamePane(AnchorPane contentPane) {
        this.contentPane.getChildren().setAll(contentPane);
        btBack.setVisible(true);
    }

    public void showGamePane() {
        this.setGamePane(selectGame);
        btBack.setVisible(false);
    }

    public void showCrossWord() {
        this.setGamePane(crossWordPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gameSelect.fxml"));
            selectGame = fxmlLoader.load();
            gameSelectionController = fxmlLoader.getController();
            gameSelectionController.initGameControllerContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("crossword.fxml"));
            crossWordPane = fxmlLoader.load();
            crossWordController = fxmlLoader.getController();
            crossWordController.initGameControllerContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showGamePane();

    }
}
