package controller.panes.favoriteServices;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudyModeController extends FavoriteAction implements Initializable {

    @FXML
    private ChoiceBox<String> cbMode;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane modePane;

    @FXML
    private JFXButton selectionButton;

    @FXML
    private JFXButton writingButton;

    protected AnchorPane selectionPane;
    protected SelectionMode selectionModeController;
    protected AnchorPane writingPane;
    protected WritingMode writingModeController;

    private void showSelection() throws SQLException {
        contentPane.getChildren().setAll(selectionPane);
        selectionModeController.reStart(new ActionEvent());
    }

    private void showWriting() throws SQLException {
        contentPane.getChildren().setAll(writingPane);
        writingModeController.reStart(new ActionEvent());
    }

    @FXML
    void mode(ActionEvent event) {
        if (modePane.isVisible()) {
            modePane.setVisible(false);
        } else {
            modePane.setVisible(true);
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

        selectionButton.setOnMouseClicked(mouseEvent -> {
            try {
                showSelection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        writingButton.setOnMouseClicked(mouseEvent -> {
            try {
                showWriting();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cbMode.getItems().add("Writing");
        cbMode.getItems().add("Selection");
    }
}
