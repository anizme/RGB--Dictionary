package controller.panes;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import controller.ApplicationStart;
import controller.panes.games.GameSelectionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends ActionController implements Initializable {

    @FXML
    private AnchorPane gameContainer;

    private AnchorPane selectGame;
    private GameSelectionController gameSelectionController;

    protected void setGamePane(AnchorPane contentPane) {
        this.gameContainer.getChildren().setAll(contentPane);
    }

    public void showGamePane() {
        this.setGamePane(selectGame);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gameSelect.fxml"));
            selectGame = fxmlLoader.load();
            gameSelectionController = fxmlLoader.getController();
            gameSelectionController.initData(this.state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showGamePane();
    }
}
