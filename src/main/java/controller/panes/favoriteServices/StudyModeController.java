package controller.panes.favoriteServices;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.ActionController;
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

public class StudyModeController extends ActionController implements Initializable {

    protected AnchorPane selectionPane;
    protected SelectionMode selectionModeController;
    protected AnchorPane writingPane;
    protected WritingMode writingModeController;
    @FXML
    private ChoiceBox<String> cbMode;
    @FXML
    private AnchorPane blur;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private AnchorPane modePane;

    @FXML
    private JFXButton selectionButton;

    @FXML
    private JFXButton writingButton;

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
        blur.setVisible(true);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("writingMode.fxml"));
            writingPane = fxmlLoader.load();
            writingModeController = fxmlLoader.getController();
            writingModeController.setContainer(this.container);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("selectionMode.fxml"));
            selectionPane = fxmlLoader.load();
            selectionModeController = fxmlLoader.getController();
            selectionModeController.setContainer(this.container);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        selectionButton.setOnMouseClicked(mouseEvent -> {
            try {
                showSelection();
                blur.setVisible(false);
                modePane.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        writingButton.setOnMouseClicked(mouseEvent -> {
            try {
                showWriting();
                blur.setVisible(false);
                modePane.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cbMode.getItems().add("Writing");
        cbMode.getItems().add("Selection");
    }
}
