package controller.panes.games;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.ActionController;
import controller.panes.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSelectionController extends GameController implements Initializable {
    @FXML
    private ImageView defaultGame;

    @FXML
    private ImageView crossWordGame;
    @FXML
    private JFXButton crossWord;

    private AnchorPane crossWordPane;
    private CrossWord crossWordController;

    @FXML
    private ImageView GAME2;
    @FXML
    private JFXButton game2;


    @FXML
    private ImageView GAME3;
    @FXML
    private JFXButton game3;

    @FXML
    private Rectangle crwBG;

    @FXML
    private Rectangle defBG;



    @FXML
    private AnchorPane gameContainer;

    @FXML
    void btCrossWord(ActionEvent event) {
        super.setGamePane(crossWordPane);
    }

    @FXML
    void btGame2(ActionEvent event) {

    }

    @FXML
    void btGame3(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("crossword.fxml"));
            crossWordPane = fxmlLoader.load();
            crossWordController = fxmlLoader.getController();
            //crossWordController.initData(this.state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
