package controller.panes;

import controller.Alert.ConfirmationAlert;
import controller.Alert.DetailAlert;
import controller.Alert.NoOptionAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.ApplicationStart.dictionaryDB;

public class AddController extends ActionController implements Initializable {

    private final String defaultText = "<h1>%s</h1><h3><i>/pronounce/</i></h3><h2>Word_Type1(Noun/Verb/...)</h2><ul><li>meaning_1:</li><li>meaning_2:</li></ul>";

    @FXML
    private AnchorPane addPane;

    @FXML
    private ImageView backgroundView;

    @FXML
    private HTMLEditor htmlAddMeaning;

    @FXML
    private ListView<String> lvSearchWordsList;

    @FXML
    private TextField tfAddWord;

    private boolean isSearch = false;
    private boolean isAddWord = false;

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public AnchorPane getAddPane() {
        return addPane;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public HTMLEditor getHtmlEditor() {
        return htmlAddMeaning;
    }

    public TextField getTfAddWord() {
        return tfAddWord;
    }

    public ListView<String> getListView() {
        return lvSearchWordsList;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public boolean isAddWord() {
        return isAddWord;
    }

    @FXML
    void addAction(ActionEvent event) throws SQLException {
        String addWord = tfAddWord.getText().toLowerCase();
        if (dictionaryDB.isInDictionary(addWord)) {
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "This word has already exists", "Error");
            alert.alertAction();
        } else if (htmlAddMeaning.getHtmlText().equals("")) {
            System.out.println("add");
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Missing meaning of word", "Error");
            alert.alertAction();
        } else {
            ConfirmationAlert alert = new ConfirmationAlert("Confirm to add new word", "Confirm?");
            if (alert.alertAction()) {
                dictionaryDB.insertWord(addWord, htmlAddMeaning.getHtmlText());
            }
        }
    }

    public void reset() {
        tfAddWord.clear();
        htmlAddMeaning.setHtmlText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        htmlAddMeaning.setHtmlText("<body style='background-color: #def3f6; color: black;'/>");
        tfAddWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            if (tfAddWord.getText() != null) {
                String searchWord = tfAddWord.getText();
                if (!searchWord.isEmpty()) {
                    lvSearchWordsList.getItems().addAll(dictionaryDB.getListWordTargets(searchWord));
                }
            }
        });

        lvSearchWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!lvSearchWordsList.getSelectionModel().isEmpty()) {
                    isSearch = true;
                    tfAddWord.setText(lvSearchWordsList.getSelectionModel().getSelectedItem());
                    if (ContainerController.isLightMode) {
                        htmlAddMeaning.setHtmlText("<body style='background-color: #def3f6; color: black;'/>"
                                + dictionaryDB.getMeaning(tfAddWord.getText()));
                    } else {
                        htmlAddMeaning.setHtmlText("<body style='background-color: #2f4f4f; color: white;'/>"
                                + dictionaryDB.getMeaning(tfAddWord.getText()));
                    }
                }
            }
        });

        tfAddWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    isAddWord = true;
                    if (!tfAddWord.getText().isEmpty()) {
                        htmlAddMeaning.setHtmlText("<body style='background-color: #def3f6; color: black;'/>" +
                                String.format(defaultText, tfAddWord.getText()));
                    } else {
                        htmlAddMeaning.setHtmlText("<body style='background-color: #def3f6; color: black;'/>" + defaultText);
                    }

                }
            }
        });

    }
}
