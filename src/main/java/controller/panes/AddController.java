package controller.panes;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.ApplicationStart.dictionaryManagement;

public class AddController extends ActionController implements Initializable {

    @FXML
    private ImageView backgroundView;

    @FXML
    private AnchorPane addPane;

    @FXML
    private TextArea taNewWord;

    @FXML
    private TextArea taWordMeaning;

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    @FXML
    void addAction(ActionEvent event) {
        Alert addAlert = new Alert(Alert.AlertType.NONE);
        if (taNewWord.getText().isEmpty() || taWordMeaning.getText().isEmpty()) {
            addAlert.setAlertType(Alert.AlertType.ERROR);
            addAlert.setHeaderText("ERROR when adding");
            addAlert.setContentText("Nothing to add");
            addAlert.showAndWait();
        }
        boolean isExist = !dictionaryManagement.dictionaryLookup(taNewWord.getText()).equals("NO INFORMATION");
        addAlert.setAlertType(Alert.AlertType.INFORMATION);
        addAlert.setHeaderText("Notification");
        if (dictionaryManagement.dictionaryConditionalAdd(taNewWord.getText(), taWordMeaning.getText(), isExist)) {
           addAlert.setContentText("Added " + taNewWord.getText() + " with meaning " + taWordMeaning.getText());
           addAlert.showAndWait();
           taWordMeaning.clear();
           taNewWord.clear();
        } else {
            addAlert.setContentText("Fail to add \"" + taNewWord.getText() + "\" with meaning \""
                    + taWordMeaning.getText() + "\"");
            addAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
