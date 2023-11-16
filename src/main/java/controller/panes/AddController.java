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
import services.DatabaseConnect;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public AnchorPane getAddPane() {
        return addPane;
    }


    @FXML
    void addAction(ActionEvent event) throws SQLException {
        if (!lvSearchWordsList.getItems().isEmpty()) {
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "This word has already exists", "Error");
            alert.alertAction();
        } else if (htmlAddMeaning.getHtmlText().equals("")) {
            System.out.println("add");
            ;
            DetailAlert alert = new NoOptionAlert(Alert.AlertType.ERROR, "Missing meaning of word", "Error");
            alert.alertAction();
        } else {
            ConfirmationAlert alert = new ConfirmationAlert("Confirm to add new word", "Confirm?");
            if (alert.alertAction()) {
                DatabaseConnect.insertWord(tfAddWord.getText(), htmlAddMeaning.getHtmlText());
            }
        }
//        Alert addAlert = new Alert(Alert.AlertType.NONE);
//        if (taNewWord.getText().isEmpty() || taWordMeaning.getText().isEmpty()) {
//            addAlert.setAlertType(Alert.AlertType.ERROR);
//            addAlert.setHeaderText("ERROR when adding");
//            addAlert.setContentText("Nothing to add");
//            addAlert.showAndWait();
//        }
//        boolean isExist = !dictionaryManagement.dictionaryLookup(taNewWord.getText()).equals("NO INFORMATION");
//        addAlert.setAlertType(Alert.AlertType.INFORMATION);
//        addAlert.setHeaderText("Notification");
//        if (dictionaryManagement.dictionaryConditionalAdd(taNewWord.getText(), taWordMeaning.getText(), isExist)) {
//           addAlert.setContentText("Added " + taNewWord.getText() + " with meaning " + taWordMeaning.getText());
//           addAlert.showAndWait();
//           taWordMeaning.clear();
//           taNewWord.clear();
//        } else {
//            addAlert.setContentText("Fail to add \"" + taNewWord.getText() + "\" with meaning \""
//                    + taWordMeaning.getText() + "\"");
//            addAlert.showAndWait();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        htmlAddMeaning.setHtmlText("");
        tfAddWord.textProperty().addListener(e -> {
            lvSearchWordsList.getItems().clear();
            if (tfAddWord.getText() != null) {
                String searchWord = tfAddWord.getText();
                if (!searchWord.isEmpty()) {
                    try {
                        lvSearchWordsList.getItems().addAll(DatabaseConnect.getListWordTargets(searchWord));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        lvSearchWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!lvSearchWordsList.getSelectionModel().isEmpty()) {
                    tfAddWord.setText(lvSearchWordsList.getSelectionModel().getSelectedItem());
                    try {
                        if (ContainerController.isLightMode) {
                            htmlAddMeaning.setHtmlText("<body style='background-color: white; color: black;'/>"
                                    + DatabaseConnect.getMeaning(tfAddWord.getText()));
                        } else {
                            htmlAddMeaning.setHtmlText("<body style='background-color: #2f4f4f; color: white;'/>"
                                    + DatabaseConnect.getMeaning(tfAddWord.getText()));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        tfAddWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!tfAddWord.getText().isEmpty()) {
                        htmlAddMeaning.setHtmlText(String.format(defaultText, tfAddWord.getText()));
                    } else {
                        htmlAddMeaning.setHtmlText(defaultText);
                    }

                }
            }
        });

    }
}
