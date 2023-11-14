package controller.panes;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import games.CrossWord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends ActionController implements Initializable {
    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane gameContainer;

    @FXML
    private JFXButton crossWord = null;

    @FXML
    private JFXButton game2;

    @FXML
    private JFXButton game3;

    @FXML
    private ImageView defaultGame;

    @FXML
    private ImageView crossWordGame;

    @FXML
    private Rectangle defBG;

    @FXML
    private Rectangle crwBG;

    @FXML
    private AnchorPane game1Pane = null;

    @FXML
    private CrossWord game2Controller;

    public GameController() {
    }

    public void crossWord(ActionEvent event) throws Exception {
        this.contentPane.getChildren().setAll(game1Pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (crossWord != null) {
            System.out.println("T");
            crossWord.setOnMouseEntered(e -> {
                crossWordGame.setVisible(true);
                defaultGame.setVisible(false);
                defBG.setVisible(false);
                crwBG.setVisible(true);
            });
            crossWord.setOnMouseExited(e -> {
                crossWordGame.setVisible(false);
                defaultGame.setVisible(true);
                defBG.setVisible(true);
                crwBG.setVisible(false);
            });
        }
    }

}
