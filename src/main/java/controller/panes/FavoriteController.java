package controller.panes;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.favoriteServices.FlashCardController;
import controller.panes.favoriteServices.StudyModeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.ApplicationStart.favoriteDB;

public class FavoriteController extends ActionController implements Initializable {
    protected FlashCardController flashCardController;
    protected AnchorPane flashCardPane;
    protected StudyModeController studyModeController;
    protected AnchorPane studyPane;
    @FXML
    private JFXButton BACK;
    @FXML
    private ImageView backgroundView;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private AnchorPane favoritePane;
    @FXML
    private ListView<String> lvFavorite;
    @FXML
    private TextField tfFavorite;

    public FavoriteController() throws SQLException {
        if (lvFavorite != null) {
            lvFavorite.getItems().addAll(favoriteDB.getFavorite());
        }
    }

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

    public AnchorPane getFavoritePane() {
        return favoritePane;
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }


    public void updateListView(ActionEvent event) throws Exception {
        if (lvFavorite != null) {
            lvFavorite.getItems().clear();
            lvFavorite.getItems().addAll(favoriteDB.getFavorite());
        }
    }

    @FXML
    void removeFavorite(ActionEvent event) throws Exception {
        ((ContainerController) this.container).getSearchController().removeFavorite(tfFavorite.getText().trim().toLowerCase());
        updateListView(new ActionEvent());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("flashCard.fxml"));
            flashCardPane = fxmlLoader.load();
            flashCardController = fxmlLoader.getController();
            flashCardController.setContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("favoriteStudy.fxml"));
            studyPane = fxmlLoader.load();
            studyModeController = fxmlLoader.getController();
            studyModeController.setContainer(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tfFavorite.textProperty().addListener(e -> {
            lvFavorite.getItems().clear();
            if (tfFavorite.getText() != null) {
                String searchWord = tfFavorite.getText();
                try {
                    lvFavorite.getItems().addAll(favoriteDB.getFavoriteTargets(searchWord));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        lvFavorite.setOnMouseClicked(mouseEvent -> {
            if (!lvFavorite.getSelectionModel().isEmpty()) {
                String[] parts = lvFavorite.getSelectionModel().getSelectedItem().split("\n");
                tfFavorite.setText(parts[0]);
            }
        });
    }
}
