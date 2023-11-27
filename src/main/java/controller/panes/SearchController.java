package controller.panes;

import algorithms.AutoCorrect;
import com.jfoenix.controls.JFXButton;
import controller.Alert.ConfirmationAlert;
import controller.Alert.DetailAlert;
import controller.Alert.NoOptionAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.scene.control.Label;
import services.API.VoiceRSS;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static controller.ApplicationStart.*;

public class SearchController extends ActionController implements Initializable {

    @FXML
    private ImageView backgroundView;

    @FXML
    private AnchorPane correctPane;

    @FXML
    private Label correctWord;

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
    private JFXButton noStared;

    @FXML
    private JFXButton stared;

    @FXML
    private Button btSave;

    private boolean isUpdate = false;

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public AnchorPane getSearchPane() {
        return searchPane;
    }

    public TextField getTfSearchWord() {
        return tfSearchWord;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public ListView<String> getListView() {
        return lvSearchWordsList;
    }

    public WebView getWebView() {
        return wvMeaning;
    }

    public HTMLEditor getHtmlEditor() {
        return htmlUpdateMeaning;
    }

    @FXML
    void usSpeak(ActionEvent event) throws Exception {
        if (tfSearchWord.getText().isEmpty()) {
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Nothing to speak", "Error");
            alert.alertAction();
            return;
        }
        VoiceRSS.Name = VoiceRSS.voiceNameUS;
        speak("en-us");
    }

    @FXML
    void ukSpeak(ActionEvent event) throws Exception {
        if (tfSearchWord.getText().isEmpty()) {
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Nothing to speak", "Error");
            alert.alertAction();
            return;
        }
        VoiceRSS.Name = VoiceRSS.voiceNameUK;
        speak("en-gb");
    }

    private void speak(String lang) throws Exception {
        VoiceRSS.language = lang;
        VoiceRSS.speakWord(tfSearchWord.getText().toLowerCase());
    }

    @FXML
    void lookup(ActionEvent event) throws SQLException {
        if (ContainerController.isLightMode) {
            wvMeaning.getEngine().loadContent("<body style='background-color: #def3f6; color: black;'/>"
                    + dictionaryDB.getMeaning(tfSearchWord.getText()));
        } else {
            wvMeaning.getEngine().loadContent("<body style='background-color: #2f4f4f; color: white;'/>"
                    + dictionaryDB.getMeaning(tfSearchWord.getText()));
        }

        noStared.setVisible(false);
        stared.setVisible(false);
        String wordText = tfSearchWord.getText().toLowerCase();
        wordText = wordText.toLowerCase();
        historyDB.removeHistoryWord(wordText);
        if (dictionaryDB.isInDictionary(wordText)) {
            correctPane.setVisible(false);
            historyDB.insertHistory(wordText);
            if (favoriteDB.isWordInFavorite(wordText)) {
                noStared.setVisible(false);
                stared.setVisible(true);
            } else {
                htmlUpdateMeaning.setVisible(false);
                wvMeaning.setVisible(true);
                btSave.setVisible(false);
                noStared.setVisible(true);
                stared.setVisible(false);
            }
        } else {
            if (!tfSearchWord.getText().isEmpty()) {
                handleCorrect();
            }
        }
    }

    private void handleCorrect() throws SQLException {
        correctWord.setText(AutoCorrect.findNearestWord(tfSearchWord.getText().trim()));
        correctPane.setVisible(true);
    }

    @FXML
    void setCorrectWord(MouseEvent event) throws SQLException {
        tfSearchWord.setText(correctWord.getText());
        correctPane.setVisible(false);
        lookup(null);
    }

    @FXML
    void update(ActionEvent event) {
        if (tfSearchWord.textProperty().isEqualTo("").get()) {
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Error...",
                    "Nothing to update");
            alert.alertAction();
            return;
        }
        if (htmlUpdateMeaning.isVisible()) {
            isUpdate = false;
            htmlUpdateMeaning.setVisible(false);
            btSave.setVisible(false);
            wvMeaning.setVisible(true);
//            if (dictionaryDB.isInDictionary(tfSearchWord.getText().toLowerCase())) {
//                noStared.setVisible(false);
//                stared.setVisible(true);
//            } else {
//                noStared.setVisible(true);
//                stared.setVisible(false);
//            }
        } else {
            isUpdate = true;
            htmlUpdateMeaning.setVisible(true);
            btSave.setVisible(true);
            wvMeaning.setVisible(false);
            if (ContainerController.isLightMode) {
                htmlUpdateMeaning.setHtmlText("<body style='background-color: #def3f6; color: black'/>"
                        + dictionaryDB.getMeaning(tfSearchWord.getText()));
            } else {
                htmlUpdateMeaning.setHtmlText("<body style='background-color: #2f4f4f; color: white'/>"
                        + dictionaryDB.getMeaning(tfSearchWord.getText()));
            }
            noStared.setVisible(false);
            stared.setVisible(false);
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Error...",
                "Nothing to update");
        if (htmlUpdateMeaning.getHtmlText().isEmpty()) {
            alert.alertAction();
        } else {
            try {
                dictionaryDB.updateWord(tfSearchWord.getText(), htmlUpdateMeaning.getHtmlText());
                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
                        "Updated new meaning for this word.");
                alert.alertAction();
                htmlUpdateMeaning.setVisible(false);
                wvMeaning.setVisible(true);
                btSave.setVisible(false);
                wvMeaning.getEngine().loadContent(htmlUpdateMeaning.getHtmlText());
                noStared.setVisible(true);
            } catch (Exception e) {
                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
                        "Failed to update new meaning for this word.");
                alert.alertAction();
            }
        }
    }

    @FXML
    void remove(ActionEvent event) throws SQLException {
        if (tfSearchWord.textProperty().isEqualTo("").get()) {
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Error...",
                    "Nothing to delete");
            alert.alertAction();
            return;
        }
        DetailAlert confirmationAlert = new ConfirmationAlert("CONFIRM..."
                , "Make sure you want to remove this word from the dictionary.");
        if (confirmationAlert.alertAction()) {
            //boolean canRemove = dictionaryManagement.dictionaryRemove(tfSearchWord.getText());
            boolean canRemove = !lvSearchWordsList.getItems().isEmpty();
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.INFORMATION, "Notification"
                    , "Removed " + tfSearchWord.getText());
            if (canRemove) {
                String wordToDelete = tfSearchWord.getText().trim().toLowerCase();
                dictionaryDB.deleteWord(wordToDelete);
                historyDB.removeHistoryWord(wordToDelete);
                favoriteDB.removeFavoriteWord(wordToDelete);
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

    public void addFavorite(ActionEvent event) throws Exception {
        String word = tfSearchWord.getText();
        if (!favoriteDB.isWordInFavorite(word)) {
            favoriteDB.insertFavorite(word);
        }
        noStared.setVisible(false);
        stared.setVisible(true);
    }

    @FXML
    public void removeFavorite(ActionEvent event) throws Exception {
        removeFavorite(tfSearchWord.getText().toLowerCase());
    }

    public void removeFavorite(String word) throws SQLException {
        favoriteDB.removeFavoriteWord(word);
        noStared.setVisible(true);
        stared.setVisible(false);
    }

    public void updateHistoryInListView() {
        List<String> tmp = historyDB.getHistory();
        Collections.reverse(tmp);
        lvSearchWordsList.getItems().addAll(tmp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        correctPane.setVisible(false);
        wvMeaning.getEngine().loadContent("<body style='background-color: #def3f6; color: black;'/>");
        tfSearchWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            correctPane.setVisible(false);
            wvMeaning.getEngine().loadContent("");
            if (tfSearchWord.getText() != null) {
                String searchWord = tfSearchWord.getText();
                if (!searchWord.isEmpty()) {
                    lvSearchWordsList.getItems().addAll(dictionaryDB.getListWordTargets(searchWord));
                    if (lvSearchWordsList.getItems().isEmpty()) {
                        try {
                            handleCorrect();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    List<String> tmp = historyDB.getHistory();
                    Collections.reverse(tmp);
                    lvSearchWordsList.getItems().addAll(tmp);
                }
            }
        });

        lvSearchWordsList.setOnMouseClicked(mouseEvent -> {
            if (!lvSearchWordsList.getSelectionModel().isEmpty()) {
                tfSearchWord.setText(lvSearchWordsList.getSelectionModel().getSelectedItem());
                if (ContainerController.isLightMode) {
                    wvMeaning.getEngine().loadContent("<body style='background-color: #def3f6; color: black;'/>"
                            + dictionaryDB.getMeaning(tfSearchWord.getText()));
                } else {
                    wvMeaning.getEngine().loadContent("<body style='background-color: #2f4f4f; color: white;'/>"
                            + dictionaryDB.getMeaning(tfSearchWord.getText()));
                }
                htmlUpdateMeaning.setVisible(false);
                wvMeaning.setVisible(true);
                btSave.setVisible(false);
                try {
                    if (favoriteDB.isWordInFavorite(tfSearchWord.getText().toLowerCase())) {
                        noStared.setVisible(false);
                        stared.setVisible(true);
                    } else {
                        noStared.setVisible(true);
                        stared.setVisible(false);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String wordText = tfSearchWord.getText().toLowerCase();
                historyDB.removeHistoryWord(wordText);
                if (dictionaryDB.isInDictionary(wordText)) {
                    historyDB.insertHistory(wordText);
                } else {
                    noStared.setVisible(false);
                    stared.setVisible(false);
                }
            }
        });

        tfSearchWord.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    lookup(null);
                    htmlUpdateMeaning.setVisible(false);
                    wvMeaning.setVisible(true);
                    btSave.setVisible(false);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        htmlUpdateMeaning.setVisible(false);
        btSave.setVisible(false);
    }
}
