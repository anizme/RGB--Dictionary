package controller.panes;

import controller.Alert.ConfirmationAlert;
import controller.Alert.DetailAlert;
import controller.Alert.NoOptionAlert;
import controller.ApplicationStart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import services.DatabaseConnect;
import services.VoiceRSS;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.ApplicationStart.dictionaryManagement;

public class SearchController extends ActionController implements Initializable {

    @FXML
    private ListView<String> lvSearchWordsList;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private WebView wvMeaning;

    @FXML
    private TextField tfSearchWord;

    @FXML
    private HTMLEditor htmlUpdateMeaning;

    @FXML
    private Button btSave;

    @FXML
    void usSpeak(ActionEvent event) throws Exception {

        VoiceRSS.Name = VoiceRSS.voiceNameUS;
        speak("en-gb");
    }

    @FXML
    void ukSpeak(ActionEvent event) throws Exception {
        VoiceRSS.Name = VoiceRSS.voiceNameUS;
        speak("en-us");
    }

    private void speak(String lang) throws Exception {
        VoiceRSS.language = lang;
        VoiceRSS.speakWord(tfSearchWord.getText().toLowerCase());
    }

    @FXML
    void lookup(ActionEvent event) {
        wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(tfSearchWord.getText()));
        //wvMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
    }

    @FXML
    void update(ActionEvent event) {
        if (htmlUpdateMeaning.isVisible()) {
            htmlUpdateMeaning.setVisible(false);
            btSave.setVisible(false);
        } else {
            htmlUpdateMeaning.setVisible(true);
            btSave.setVisible(true);
            htmlUpdateMeaning.setHtmlText(DatabaseConnect.getMeaning(tfSearchWord.getText()));
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Error...",
                "Nothing to update");
        if (htmlUpdateMeaning.getHtmlText().isEmpty()) {
            alert.alertAction();
        } else {
            if (dictionaryManagement.dictionaryUpdate(tfSearchWord.getText(), htmlUpdateMeaning.getHtmlText())) {
                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
                        "Updated new meaning for this word.");
                alert.alertAction();
                htmlUpdateMeaning.setVisible(false);
                //wvMeaning.setText(htmlUpdateMeaning.getText());
                wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(htmlUpdateMeaning.getHtmlText()));
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
                wvMeaning.getEngine().loadContent("");
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
                String tmp = tfSearchWord.getText();
                String querry = String.format("SELECT word FROM av WHERE word LIKE '%s%%' ORDER BY word", tmp);
                lvSearchWordsList.getItems()
                        .addAll(DatabaseConnect.GetWord(querry));
            }
        });
        lvSearchWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!lvSearchWordsList.getSelectionModel().isEmpty()) {
                    tfSearchWord.setText(lvSearchWordsList.getSelectionModel().getSelectedItem());
                    //wvMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
                    wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(tfSearchWord.getText()));
                }
            }
        });
        htmlUpdateMeaning.setVisible(false);
        btSave.setVisible(false);
    }
}
