package controller.panes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private JFXButton btExit;

    @FXML
    private JFXButton btMenu;

    @FXML
    private AnchorPane menuDetails;

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void menu(ActionEvent event) {
        menuDetails.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDetails.setVisible(false);
    }
}
