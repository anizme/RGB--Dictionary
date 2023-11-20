package controller.panes;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.favoriteServices.FlashCardController;
import controller.panes.favoriteServices.StudyModeController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import services.DatabaseConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FavoriteController extends ActionController implements Initializable {
    @FXML
    private JFXButton BACK;

    @FXML
    private ImageView backgroundView;

    @FXML
    private JFXButton btFlashCard;

    @FXML
    private JFXButton btSearch;

    @FXML
    private JFXButton btStudy;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane favoritePane;

    @FXML
    private ListView<String> lvFavorite;

    @FXML
    private TextField tfFavorite;

    protected FlashCardController flashCardController;
    protected AnchorPane flashCardPane;

    protected StudyModeController studyModeController;
    protected AnchorPane studyPane;

    @FXML
    void back(ActionEvent event) {
        this.contentPane.getChildren().setAll(favoritePane);
        BACK.setVisible(false);
    }

    @FXML
    void playFlashCard(ActionEvent event) {
        this.contentPane.getChildren().setAll(flashCardPane);
        BACK.setVisible(true);
    }

    @FXML
    void study(ActionEvent event) {
        this.contentPane.getChildren().setAll(studyPane);
        BACK.setVisible(true);
    }

    public FavoriteController() throws SQLException {
        if (lvFavorite != null) {
            lvFavorite.getItems().addAll(DatabaseConnect.getFavorite());
        }
    }

    public AnchorPane getFavoritePane() {
        return favoritePane;
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }


    public void updateListView(ActionEvent event) throws Exception {
        if (lvFavorite != null) {
            lvFavorite.getItems().clear();
            lvFavorite.getItems().addAll(DatabaseConnect.getFavoriteWordShortMeaning());
        }
    }

    public void searchFavorite(ActionEvent event) throws Exception {
        String tmp = tfFavorite.getText();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("flashCard.fxml"));
            flashCardPane = fxmlLoader.load();
            flashCardController = fxmlLoader.getController();
            flashCardController.initFavoriteControllerContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("favoriteStudy.fxml"));
            studyPane = fxmlLoader.load();
            studyModeController = fxmlLoader.getController();
            studyModeController.initFavoriteControllerContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
