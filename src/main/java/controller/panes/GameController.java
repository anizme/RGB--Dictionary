package controller.panes;

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
    private AnchorPane gameContainer;

    protected AnchorPane selectGame;
    protected GameSelectionController gameSelectionController;

    protected AnchorPane crossWordPane;
    protected CrossWord crossWordController;

    private Button back = new Button("BACK");


    private void setGamePane(AnchorPane contentPane) {
        this.gameContainer.getChildren().setAll(contentPane);

    }

    public void showGamePane() {
        this.setGamePane(selectGame);
        System.out.println("hi");
        gameContainer.getChildren().add(back);
    }

    public void showCrossWord() {
        this.setGamePane(crossWordPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showGamePane();
            }
        });
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gameSelect.fxml"));
            selectGame = fxmlLoader.load();
            gameSelectionController = fxmlLoader.getController();
            gameSelectionController.initData(this.state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("crossword.fxml"));
            crossWordPane = fxmlLoader.load();
            crossWordController = fxmlLoader.getController();
            crossWordController.initData(this.state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showGamePane();

    }
}
