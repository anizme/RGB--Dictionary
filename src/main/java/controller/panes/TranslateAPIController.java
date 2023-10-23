package controller.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.TranslateAPI;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TranslateAPIController extends ActionController implements Initializable {
    String languageFrom = "";
    String languageTo = "vi";
    String nameFrom;
    String speakFrom;
    String nameTo;
    String speakTo;

    @FXML
    private Button langFromFirst;

    @FXML
    private Button langFromFourth;

    @FXML
    private Button langFromSecond;

    @FXML
    private Button langFromThird;

    @FXML
    private Button langToFifth;

    @FXML
    private Button langToFirst;

    @FXML
    private Button langToFourth;

    @FXML
    private Button langToSecond;

    @FXML
    private Button langToThird;

    @FXML
    private TextArea taTextToTrans;

    @FXML
    private TextField taTransText;

    @FXML
    private TextField tfDesLang;

    @FXML
    private TextField tfSrcLang;

    public void resetStyleLangFrom() {
        langFromFirst.getStyleClass().removeAll("active");
        langFromSecond.getStyleClass().removeAll("active");
        langFromThird.getStyleClass().removeAll("active");
        langFromFourth.getStyleClass().removeAll("active");
    }

    public void resetStyleLangTo() {
        langToFirst.getStyleClass().removeAll("active");
        langToSecond.getStyleClass().removeAll("active");
        langToThird.getStyleClass().removeAll("active");
        langToFourth.getStyleClass().removeAll("active");
        langToFifth.getStyleClass().removeAll("active");
    }

    @FXML
    void chinese(ActionEvent event) throws IOException {
        resetStyleLangTo();
        langToFifth.getStyleClass().add("active");
        languageTo = "zh";
        tfDesLang.setText("Tiếng Trung");
        nameTo = "Luli";
        speakTo = "zh-cn";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @FXML
    void detect(ActionEvent event) {
        resetStyleLangFrom();
        langFromFirst.getStyleClass().add("active");
        languageFrom = "";
        tfSrcLang.setText("Phát hiện n.ngữ");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void eng(ActionEvent event) {
        resetStyleLangFrom();
        langFromSecond.getStyleClass().add("active");
        languageFrom = "en";
        tfSrcLang.setText("Tiếng Anh");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void eng2(ActionEvent event) throws IOException {
        resetStyleLangTo();
        langToSecond.getStyleClass().add("active");
        tfDesLang.setText("Tiếng Anh");
        languageTo = "en";
        nameTo = "Linda";
        speakTo = "en-gb";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @FXML
    void korea(ActionEvent event) {
        resetStyleLangFrom();
        langFromFourth.getStyleClass().add("active");
        tfSrcLang.setText("Tiếng Hàn");
        languageFrom = "ko";
        nameFrom = "Nari";
        speakFrom = "ko-kr";
    }

    @FXML
    void korea2(ActionEvent event) throws IOException {
        resetStyleLangTo();
        langToThird.getStyleClass().add("active");
        tfDesLang.setText("Tiếng Hàn");
        languageTo = "ko";
        nameTo = "Nari";
        speakTo = "ko-kr";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTransText.getText()));
        }
    }

    @FXML
    void rus(ActionEvent event) throws IOException {
        resetStyleLangTo();
        langToFourth.getStyleClass().add("active");
        tfDesLang.setText("Tiếng Nga");
        languageTo = "ru";
        nameTo = "Marina";
        speakTo = "ru-ru";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @FXML
    void speak1(ActionEvent event) {

    }

    @FXML
    void speak2(ActionEvent event) {

    }

    @FXML
    void translate(ActionEvent event) throws IOException {
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @FXML
    void vie1(ActionEvent event) {
        resetStyleLangFrom();
        langFromThird.getStyleClass().add("active");
        tfSrcLang.setText("Tiếng Việt");
        languageFrom = "vi";
        nameFrom = "Chi";
        speakFrom = "vi-vn";
    }

    @FXML
    void vie2(ActionEvent event) throws IOException {
        resetStyleLangTo();
        langToFirst.getStyleClass().add("active");
        tfDesLang.setText("Tiếng Việt");
        languageTo = "vi";
        nameTo = "Chi";
        speakTo = "vi-vn";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langFromFirst.getStyleClass().add("active");
        langToFirst.getStyleClass().add("active");

        tfSrcLang.setText("Phát hiện n.ngữ");
        taTextToTrans.setText("");
        nameFrom = "Linda";
        speakFrom = "en-gb";
        languageFrom = "";

        tfDesLang.setText("Tiếng Việt");
        nameTo = "Chi";
        speakTo = "vi-vn";
        languageTo = "vi";
    }
}