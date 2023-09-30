package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Button btLookUp;

    @FXML
    private Button btRemove;

    @FXML
    private Button btUpdate;

    @FXML
    private TextArea taPronounce;

    @FXML
    private TextField tfSearchWord;

    @FXML
    private ListView<?> viewListWord;

    @FXML
    void searchMenuClicked(ActionEvent event) {

    }

    @FXML
    void addMenuClicked(ActionEvent event) {

    }

    @FXML
    void gameMenuClicked(ActionEvent event) {

    }

    @FXML
    void settingMenuClicked(ActionEvent event) {

    }

    @FXML
    void lookUp(ActionEvent event) {
        String searchWord = tfSearchWord.getText();
        taPronounce.setText(searchWord);
    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void searchWord(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }

}
