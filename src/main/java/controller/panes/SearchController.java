package controller.panes;

import com.jfoenix.controls.JFXButton;
import controller.Alert.ConfirmationAlert;
import controller.Alert.DetailAlert;
import controller.Alert.NoOptionAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import services.DatabaseConnect;
import services.VoiceRSS;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController extends ActionController implements Initializable {

    @FXML
    private ImageView backgroundView;

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

    private boolean check = true;

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
        VoiceRSS.Name = VoiceRSS.voiceNameUS;
        speak("en-us");
    }

    @FXML
    void ukSpeak(ActionEvent event) throws Exception {
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
                    + DatabaseConnect.getMeaning(tfSearchWord.getText()));
        } else {
            wvMeaning.getEngine().loadContent("<body style='background-color: #2f4f4f; color: white;'/>"
                    + DatabaseConnect.getMeaning(tfSearchWord.getText()));
        }
        //wvMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
        noStared.setVisible(false);
        stared.setVisible(false);
        String tmp = tfSearchWord.getText();
        tmp = tmp.toLowerCase();
        DatabaseConnect.clearHistoryWord(tmp);
        if (!DatabaseConnect.getMeaning(tfSearchWord.getText()).isEmpty()) {
            DatabaseConnect.insertHistory(tmp);
            String s = DatabaseConnect.getFavoriteWordByWord(tfSearchWord.getText().toLowerCase());
            if (s != null) {
                noStared.setVisible(false);
                stared.setVisible(true);
            } else {
                htmlUpdateMeaning.setVisible(false);
                wvMeaning.setVisible(true);
                btSave.setVisible(false);
                noStared.setVisible(true);
                stared.setVisible(false);
            }
        }
    }

    @FXML
    void update(ActionEvent event) throws SQLException {
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
            String s = DatabaseConnect.getFavoriteWordByWord(tfSearchWord.getText().toLowerCase());
            if (s != null) {
                noStared.setVisible(false);
                stared.setVisible(true);
            } else {
                noStared.setVisible(true);
                stared.setVisible(false);
            }
        } else {
            isUpdate = true;
            htmlUpdateMeaning.setVisible(true);
            btSave.setVisible(true);
            wvMeaning.setVisible(false);
            if (ContainerController.isLightMode) {
                htmlUpdateMeaning.setHtmlText("<body style='background-color: #def3f6; color: black'/>"
                        + DatabaseConnect.getMeaning(tfSearchWord.getText()));
            } else {
                htmlUpdateMeaning.setHtmlText("<body style='background-color: #2f4f4f; color: white'/>"
                        + DatabaseConnect.getMeaning(tfSearchWord.getText()));
            }
            noStared.setVisible(false);
            stared.setVisible(false);
        }
    }

    @FXML
    void updateAction(ActionEvent event) throws SQLException {
        DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Error...",
                "Nothing to update");
        if (htmlUpdateMeaning.getHtmlText().isEmpty()) {
            alert.alertAction();
        } else {
            try {
                DatabaseConnect.updateWord(tfSearchWord.getText(), htmlUpdateMeaning.getHtmlText());
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
//            if (dictionaryManagement.dictionaryUpdate(tfSearchWord.getText(), htmlUpdateMeaning.getHtmlText())) {
//                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
//                        "Updated new meaning for this word.");
//                alert.alertAction();
//                htmlUpdateMeaning.setVisible(false);
//                //wvMeaning.setText(htmlUpdateMeaning.getText());
//                wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(htmlUpdateMeaning.getHtmlText()));
//            } else {
//                alert.setAlertFullInfo(Alert.AlertType.INFORMATION, "Notification",
//                        "Failed to update new meaning for this word.");
//                alert.alertAction();
//            }
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
                DatabaseConnect.deleteWord(tfSearchWord.getText());
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
        String str = tfSearchWord.getText();
        if (DatabaseConnect.getMeaning(tfSearchWord.getText()) != null) {
            DatabaseConnect.insertFavorite(str);
        }
        noStared.setVisible(false);
        stared.setVisible(true);
    }

    @FXML
    public void removeFavorite(ActionEvent event) throws Exception {
        removeFavorite(tfSearchWord.getText().toLowerCase());
    }

    public void removeFavorite(String word) throws SQLException {
        DatabaseConnect.clearFavoriteWord(word);
        noStared.setVisible(true);
        stared.setVisible(false);
    }

    public void updateHistoryInListView() throws SQLException {
        List<String> tmp = DatabaseConnect.getHistory();
        Collections.reverse(tmp);
        lvSearchWordsList.getItems().addAll(tmp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noStared.setVisible(true);
        stared.setVisible(false);
        wvMeaning.getEngine().loadContent("<body style='background-color: #def3f6; color: black;'/>");
        tfSearchWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            if (tfSearchWord.getText() != null) {
                String searchWord = tfSearchWord.getText();
                if (!searchWord.equals("")) {
                    try {
                        lvSearchWordsList.getItems().addAll(DatabaseConnect.getListWordTargets(searchWord));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    //Collections.reverse(history);
                    try {
                        List<String> tmp = DatabaseConnect.getHistory();
                        Collections.reverse(tmp);
                        lvSearchWordsList.getItems().addAll(tmp);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //Collections.reverse(history);
                    check = false;
                }
            }
        });

        lvSearchWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!lvSearchWordsList.getSelectionModel().isEmpty()) {
                    tfSearchWord.setText(lvSearchWordsList.getSelectionModel().getSelectedItem());
                    //wvMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
                    try {
                        //wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(tfSearchWord.getText()));
                        if (ContainerController.isLightMode) {
                            wvMeaning.getEngine().loadContent("<body style='background-color: #def3f6; color: black;'/>"
                                    + DatabaseConnect.getMeaning(tfSearchWord.getText()));
                        } else {
                            wvMeaning.getEngine().loadContent("<body style='background-color: #2f4f4f; color: white;'/>"
                                    + DatabaseConnect.getMeaning(tfSearchWord.getText()));
                        }
                        htmlUpdateMeaning.setVisible(false);
                        wvMeaning.setVisible(true);
                        btSave.setVisible(false);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    String s = null;
                    try {
                        s = DatabaseConnect.getFavoriteWordByWord(tfSearchWord.getText().toLowerCase());
                        if (s != null) {
                            noStared.setVisible(false);
                            stared.setVisible(true);
                        } else {
                            noStared.setVisible(true);
                            stared.setVisible(false);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    String tmp = tfSearchWord.getText();
                    tmp = tmp.toLowerCase();
                    try {
                        DatabaseConnect.clearHistoryWord(tmp);
                        if (!DatabaseConnect.getMeaning(tfSearchWord.getText()).isEmpty()) {
                            DatabaseConnect.insertHistory(tmp);
                        } else {
                            noStared.setVisible(false);
                            stared.setVisible(false);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        tfSearchWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
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
            }
        });

        htmlUpdateMeaning.setVisible(false);
        btSave.setVisible(false);
    }
}
