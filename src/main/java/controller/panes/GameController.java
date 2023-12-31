package controller.panes;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.games.ChaoticWord;
import controller.panes.games.CrossWord;
import controller.panes.games.GameSelectionController;
import controller.panes.games.Hangman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends ActionController implements Initializable {
    protected AnchorPane selectGame;
    protected GameSelectionController gameSelectionController;

    protected AnchorPane crossWordPane;
    protected CrossWord crossWordController;

    protected AnchorPane hangmanPane;
    protected Hangman hangmanController;
    protected AnchorPane chaoticWord;
    protected ChaoticWord chaoticWordController;
    @FXML
    private ImageView gameMenuBackground;
    @FXML
    private JFXButton btBack;
    @FXML
    private AnchorPane contentPane;

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

    public void showHangman() {
        this.setGamePane(hangmanPane);
    }

    public void showChaoticWord() {
        this.setGamePane(chaoticWord);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gameSelect.fxml"));
            selectGame = fxmlLoader.load();
            gameSelectionController = fxmlLoader.getController();
            gameSelectionController.setContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("crossword.fxml"));
            crossWordPane = fxmlLoader.load();
            crossWordController = fxmlLoader.getController();
            crossWordController.setContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hangman.fxml"));
            hangmanPane = fxmlLoader.load();
            hangmanController = fxmlLoader.getController();
            hangmanController.setContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("chaoticword.fxml"));
            chaoticWord = fxmlLoader.load();
            chaoticWordController = fxmlLoader.getController();
            chaoticWordController.setContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        gameMenuBackground.setOnMouseClicked(mouseEvent -> {
            showGamePane();
            gameMenuBackground.setVisible(false);
            gameMenuBackground.setImage(null);
            gameMenuBackground = null;
            System.gc();
        });
    }
}
