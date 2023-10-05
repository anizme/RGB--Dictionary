package controller.panes;

import controller.ApplicationStart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.ApplicationStart.dictionaryManagement;

public class SearchController  extends ActionController implements Initializable {

    @FXML
    private ListView<String> lvSearchWordsList;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private TextArea taMeaning;

    @FXML
    private TextField tfSearchWord;

    @FXML
    void lookup(ActionEvent event) {
        System.out.println("Look up");
        taMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
    }

    @FXML
    void remove(ActionEvent event) {
        System.out.println("remove");
        Alert removeAlert = new Alert(Alert.AlertType.NONE);
        removeAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        removeAlert.setHeaderText("CONFIRM...");
        removeAlert.setContentText("Make sure you want to remove this word from the dictionary.");
        Optional<ButtonType> isCanRemove = removeAlert.showAndWait();
        if (isCanRemove.get() == ButtonType.OK) {
            boolean canRemove = dictionaryManagement.dictionaryRemove(tfSearchWord.getText());
            if(canRemove) {
                removeAlert.setAlertType(Alert.AlertType.INFORMATION);
                removeAlert.setHeaderText("Notification");
                removeAlert.setContentText("Removed " + tfSearchWord.getText());
                removeAlert.showAndWait();
                taMeaning.setText("");
                tfSearchWord.clear();
            } else {
                removeAlert.setAlertType(Alert.AlertType.ERROR);
                removeAlert.setHeaderText("Error...");
                removeAlert.setContentText("Can not find the word " + tfSearchWord.getText());
                removeAlert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfSearchWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            lvSearchWordsList.getItems().addAll(ApplicationStart.dictionaryManagement.dictionarySearch(tfSearchWord.getText()));
        });
    }
}
