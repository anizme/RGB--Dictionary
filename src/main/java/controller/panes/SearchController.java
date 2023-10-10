package controller.panes;

import controller.Alert.ConfirmationAlert;
import controller.Alert.DetailAlert;
import controller.Alert.NoOptionAlert;
import controller.ApplicationStart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.ApplicationStart.dictionaryManagement;

public class SearchController extends ActionController implements Initializable {

    @FXML
    private ListView<String> lvSearchWordsList;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private TextArea taMeaning;

    @FXML
    private TextField tfSearchWord;

    @FXML
    private AnchorPane paneUpdate;

    @FXML
    private TextArea taUpdateMeaning;

    @FXML
    void lookup(ActionEvent event) {
        taMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
    }

    @FXML
    void update(ActionEvent event) {
        taUpdateMeaning.clear();
        if (paneUpdate.isVisible()) {
            paneUpdate.setVisible(false);
        } else {
            paneUpdate.setVisible(true);
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Error...",
                "Nothing to update");
        if (taUpdateMeaning.getText().isEmpty()) {
            alert.alertAction();
        } else {
            if (dictionaryManagement.dictionaryUpdate(tfSearchWord.getText(), taUpdateMeaning.getText())) {
                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
                        "Updated new meaning for this word.");
                alert.alertAction();
                paneUpdate.setVisible(false);
                taMeaning.setText(taUpdateMeaning.getText());
            } else {
                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
                        "Failed to update new meaning for this word.");
                alert.alertAction();
            }
        }
    }

    @FXML
    void remove(ActionEvent event) {
        System.out.println("remove");
        DetailAlert confirmationAlert = new ConfirmationAlert("CONFIRM..."
                , "Make sure you want to remove this word from the dictionary.");
        if (confirmationAlert.alertAction()) {
            boolean canRemove = dictionaryManagement.dictionaryRemove(tfSearchWord.getText());
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.INFORMATION, "Notification"
                    , "Removed " + tfSearchWord.getText());
            if (canRemove) {
                alert.alertAction();
                taMeaning.setText("");
                tfSearchWord.clear();
            } else {
                alert.setAlertFullInfo(Alert.AlertType.ERROR, "Error..."
                        , "Can not find the word " + tfSearchWord.getText());
                alert.alertAction();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfSearchWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            if (tfSearchWord.getText() != null) {
                lvSearchWordsList.getItems()
                        .addAll(ApplicationStart.dictionaryManagement.dictionarySearch(tfSearchWord.getText()));
            }
        });
        lvSearchWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!lvSearchWordsList.getSelectionModel().isEmpty()) {
                    tfSearchWord.setText(lvSearchWordsList.getSelectionModel().getSelectedItem());
                    taMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
                }
            }
        });
        paneUpdate.setVisible(false);
    }
}
