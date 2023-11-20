package controller.panes.favoriteServices;

import controller.ApplicationStart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudyModeController extends FavoriteAction implements Initializable {

    @FXML
    private ChoiceBox<String> cbMode;

    @FXML
    private AnchorPane blur;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane hintPane;

    @FXML
    private AnchorPane modePane;

    protected AnchorPane selectionPane;
    protected SelectionMode selectionModeController;
    protected AnchorPane writingPane;
    protected WritingMode writingModeController;

    private void showSelection() {
        contentPane.getChildren().setAll(selectionPane);
    }

    private void showWriting() throws SQLException {
        contentPane.getChildren().setAll(writingPane);
        writingModeController.reStart(new ActionEvent());
    }

    @FXML
    void getHint(ActionEvent event) {

    }

    @FXML
    void hint(ActionEvent event) {
        if (hintPane.isVisible()) {
            hintPane.setVisible(false);
            blur.setVisible(false);
        } else {
            hintPane.setVisible(true);
            blur.setVisible(true);
        }
    }

    @FXML
    void hintOK(ActionEvent event) {
        hintPane.setVisible(false);
        blur.setVisible(false);
    }

    @FXML
    void mode(ActionEvent event) {
        if (modePane.isVisible()) {
            modePane.setVisible(false);
            blur.setVisible(false);
        } else {
            modePane.setVisible(true);
            blur.setVisible(true);
        }
    }

    @FXML
    void modeOK(ActionEvent event) throws SQLException {
        if (cbMode.getSelectionModel().getSelectedItem().equals("Selection")) {
            showSelection();
        } else {
            showWriting();
        }
        modePane.setVisible(false);
        blur.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("writingMode.fxml"));
            writingPane = fxmlLoader.load();
            writingModeController = fxmlLoader.getController();
            writingModeController.initFavoriteControllerContainer(this.actionController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("selectionMode.fxml"));
            selectionPane = fxmlLoader.load();
            selectionModeController = fxmlLoader.getController();
            selectionModeController.initFavoriteControllerContainer(this.actionController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cbMode.getItems().add("Writing");
        cbMode.getItems().add("Selection");
    }
}
