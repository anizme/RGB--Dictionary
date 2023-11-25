package controller.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.TranslateAPI;
import services.VoiceRSS;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TranslateAPIController extends ActionController implements Initializable {
    String languageFrom = "auto";
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
    private TextField taTextToTrans;

    @FXML
    private TextField taTransText;

    @FXML
    private TextField tfDesLang;

    @FXML
    private TextField tfSrcLang;
    @FXML
    private AnchorPane translatePane;
    @FXML
    private ImageView backgroundView;

    public AnchorPane getTranslatePane() {
        return translatePane;
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }

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
    void chinese(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToFifth.getStyleClass().add("active");
        languageTo = "zh";
        tfDesLang.setText("Chinese");
        nameTo = "Luli";
        speakTo = "zh-cn";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    void detect() throws Exception {
        resetStyleLangFrom();
        langFromFirst.getStyleClass().add("active");
        languageFrom = "auto";
        tfSrcLang.setText("Auto detect");
        nameFrom = "Linda";
        speakFrom = "en-gb";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            tfSrcLang.setText(TranslateAPI.googleDetect(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @FXML
    void eng(ActionEvent event) {
        resetStyleLangFrom();
        langFromSecond.getStyleClass().add("active");
        languageFrom = "en";
        tfSrcLang.setText("English");
        nameFrom = "Linda";
        speakFrom = "en-gb";
    }

    @FXML
    void eng2(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToSecond.getStyleClass().add("active");
        tfDesLang.setText("English");
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
        tfSrcLang.setText("Korean");
        languageFrom = "ko";
        nameFrom = "Nari";
        speakFrom = "ko-kr";
    }

    @FXML
    void korea2(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToThird.getStyleClass().add("active");
        tfDesLang.setText("Korean");
        languageTo = "ko";
        nameTo = "Nari";
        speakTo = "ko-kr";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTransText.getText()));
        }
    }

    @FXML
    void rus(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToFourth.getStyleClass().add("active");
        tfDesLang.setText("Russian");
        languageTo = "ru";
        nameTo = "Marina";
        speakTo = "ru-ru";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
        }
    }

    @FXML
    void speak1(ActionEvent event) throws Exception {
        VoiceRSS.Name = nameFrom;
        VoiceRSS.language = speakFrom;
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            VoiceRSS.speakWord(taTextToTrans.getText());
        }
    }

    @FXML
    void speak2(ActionEvent event) throws Exception {
        VoiceRSS.Name = nameTo;
        VoiceRSS.language = speakTo;
        if (!Objects.equals(taTransText.getText(), "")) {
            VoiceRSS.speakWord(taTransText.getText());
        }
    }

    @FXML
    void translate(ActionEvent event) throws Exception {
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            taTransText.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, taTextToTrans.getText()));
            if (languageFrom.equals("auto")) {
                detect();
            }
        }
    }

    @FXML
    void vie1(ActionEvent event) {
        resetStyleLangFrom();
        langFromThird.getStyleClass().add("active");
        tfSrcLang.setText("Vietnamese");
        languageFrom = "vi";
        nameFrom = "Chi";
        speakFrom = "vi-vn";
    }

    @FXML
    void vie2(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToFirst.getStyleClass().add("active");
        tfDesLang.setText("Vietnamese");
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

        tfSrcLang.setText("Auto detect");
        taTextToTrans.setText("");
        nameFrom = "Linda";
        speakFrom = "en-gb";
        languageFrom = "auto";

        tfDesLang.setText("Vietnamese");
        nameTo = "Chi";
        speakTo = "vi-vn";
        languageTo = "vi";
    }
}