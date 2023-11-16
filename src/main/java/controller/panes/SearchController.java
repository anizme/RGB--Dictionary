package controller.panes;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import services.DatabaseConnect;
import services.VoiceRSS;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static controller.ApplicationStart.dictionaryManagement;

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

    public Stack<String> history = new Stack<>();

    static Map<String, String> favorite = new HashMap<>();

    private boolean check = true;

    public ImageView getBackgroundView() {
        return backgroundView;
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
        wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(tfSearchWord.getText()));
        //wvMeaning.setText(dictionaryManagement.dictionaryLookup(tfSearchWord.getText()));
        if (favorite.containsValue(tfSearchWord.getText().toLowerCase())) {
            noStared.setVisible(false);
            stared.setVisible(true);
        } else {
            noStared.setVisible(true);
            stared.setVisible(false);
        }
        history.push(tfSearchWord.getText());
    }

    @FXML
    void update(ActionEvent event) throws SQLException {
        if (htmlUpdateMeaning.isVisible()) {
            htmlUpdateMeaning.setVisible(false);
            btSave.setVisible(false);
            if (favorite.containsValue(tfSearchWord.getText())) {
                noStared.setVisible(false);
                stared.setVisible(true);
            } else {
                noStared.setVisible(true);
                stared.setVisible(false);
            }
        } else {
            htmlUpdateMeaning.setVisible(true);
            btSave.setVisible(true);
            htmlUpdateMeaning.setHtmlText(DatabaseConnect.getMeaning(tfSearchWord.getText()));
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

    public void addFavorite(ActionEvent event) throws Exception {
        String str = tfSearchWord.getText();
        favorite.put(str, str);
        noStared.setVisible(false);
        stared.setVisible(true);
    }

    public void removeFavorite(ActionEvent event) throws Exception {
        int size = favorite.size();
        for (String x : favorite.keySet()) {
            String s = favorite.get(x);
            if (s.equals(tfSearchWord.getText())) {
                favorite.remove(x);
                break;
            }
        }
        noStared.setVisible(true);
        stared.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noStared.setVisible(true);
        stared.setVisible(false);
        tfSearchWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            if (tfSearchWord.getText() != null) {
                String tmp = tfSearchWord.getText();
                if (!tmp.equals("")) {
                    String querry = String.format("SELECT word FROM av WHERE word LIKE '%s%%' ORDER BY word", tmp);
                    try {
                        lvSearchWordsList.getItems()
                                .addAll(DatabaseConnect.getAllWordTargets(querry));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    Collections.reverse(history);
                    lvSearchWordsList.getItems().addAll(history);
                    Collections.reverse(history);
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
                        wvMeaning.getEngine().loadContent(DatabaseConnect.getMeaning(tfSearchWord.getText()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (favorite.containsValue(tfSearchWord.getText().toLowerCase())) {
                        noStared.setVisible(false);
                        stared.setVisible(true);
                    } else {
                        noStared.setVisible(true);
                        stared.setVisible(false);
                    }
                    history.push(tfSearchWord.getText());
                }
            }
        });

        tfSearchWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tfSearchWord.getText().isEmpty() && check) {
                    Collections.reverse(history);
                    lvSearchWordsList.getItems().addAll(history);
                    Collections.reverse(history);
                    check = false;
                } else if (!tfSearchWord.getText().isEmpty()) {
                    check = true;
                }
            }
        });

        tfSearchWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        lookup(null);
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
