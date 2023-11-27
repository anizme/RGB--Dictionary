package controller.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.API.GoogleTranslateAPI;
import services.API.VoiceRSS;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TranslateAPIController extends ActionController implements Initializable {

    private final GoogleTranslateAPI googleTranslateAPI = new GoogleTranslateAPI();

    String languageFrom = "auto";
    String languageTo = "vi";
    String nameFrom;
    String speakFrom;
    String nameTo;
    String speakTo;

    @FXML
    private Button langFromFirst;
    @FXML
    private Button langFromSecond;
    @FXML
    private Button langFromThird;
    @FXML
    private Button langFromFourth;
    @FXML
    private Button langFromFifth;
    @FXML
    private Button langFromSixth;

    @FXML
    private Button langToFirst;
    @FXML
    private Button langToSecond;
    @FXML
    private Button langToThird;
    @FXML
    private Button langToFourth;
    @FXML
    private Button langToFifth;


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
        langFromFifth.getStyleClass().removeAll("active");
        langFromSixth.getStyleClass().removeAll("active");
    }

    public void resetStyleLangTo() {
        langToFirst.getStyleClass().removeAll("active");
        langToSecond.getStyleClass().removeAll("active");
        langToThird.getStyleClass().removeAll("active");
        langToFourth.getStyleClass().removeAll("active");
        langToFifth.getStyleClass().removeAll("active");
    }

    /*
     * LANG TO BE TRANSLATED
     */

    @FXML
    void setAuto(ActionEvent event) {
        resetStyleLangFrom();
        langFromFirst.getStyleClass().add("active");
        languageFrom = "auto";
        tfSrcLang.setText("Auto detect");
    }

    @FXML
    void vie(ActionEvent event) {
        resetStyleLangFrom();
        langFromSecond.getStyleClass().add("active");
        tfSrcLang.setText("Vietnamese");
        languageFrom = "vi";
        nameFrom = "Chi";
        speakFrom = "vi-vn";
    }

    @FXML
    void eng(ActionEvent event) {
        resetStyleLangFrom();
        langFromThird.getStyleClass().add("active");
        languageFrom = "en";
        tfSrcLang.setText("English");
        nameFrom = "Linda";
        speakFrom = "en-gb";
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
    void rus(ActionEvent event) {
        resetStyleLangFrom();
        langFromFifth.getStyleClass().add("active");
        tfDesLang.setText("Russian");
        languageFrom = "ru";
        nameFrom = "Marina";
        speakFrom = "ru-ru";
    }

    @FXML
    void chinese(ActionEvent event) {
        resetStyleLangFrom();
        langFromSixth.getStyleClass().add("active");
        languageFrom = "zh";
        tfDesLang.setText("Chinese");
        nameFrom = "Luli";
        speakFrom = "zh-cn";
    }


    /*
     * TRANSLATED LANGUAGE
     */
    @FXML
    void vie2(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToFirst.getStyleClass().add("active");
        tfDesLang.setText("Vietnamese");
        languageTo = "vi";
        nameTo = "Chi";
        speakTo = "vi-vn";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
            taTransText.setText(googleTranslateAPI.getTransLang());
        }
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
            handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
            taTransText.setText(googleTranslateAPI.getTransLang());
        }
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
            handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
            taTransText.setText(googleTranslateAPI.getTransLang());
        }
    }

    @FXML
    void rus2(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToFourth.getStyleClass().add("active");
        tfDesLang.setText("Russian");
        languageTo = "ru";
        nameTo = "Marina";
        speakTo = "ru-ru";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
            taTransText.setText(googleTranslateAPI.getTransLang());
        }
    }

    @FXML
    void chinese2(ActionEvent event) throws Exception {
        resetStyleLangTo();
        langToFifth.getStyleClass().add("active");
        languageTo = "zh";
        tfDesLang.setText("Chinese");
        nameTo = "Luli";
        speakTo = "zh-cn";
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
            taTransText.setText(googleTranslateAPI.getTransLang());
        }
    }

    /*
     * SPEAK
     */

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
            VoiceRSS.speakWord(googleTranslateAPI.getTransLang());
        }
    }

    @FXML
    void translate(ActionEvent event) throws Exception {
        if (!Objects.equals(taTextToTrans.getText(), "")) {
            handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
            taTransText.setText(googleTranslateAPI.getTransLang());
            if (languageFrom.equals("auto")) {
                detect();
            }
        } else {
            taTransText.setText("");
            if (languageFrom.equals("auto")) {
                tfSrcLang.setText(googleTranslateAPI.getDetectLang());
            }
        }
    }

    private void handleTranslate(String langFrom, String langTo, String text) throws Exception {
        googleTranslateAPI.handleTranslate(langFrom, langTo, text);
    }

    private void detect() throws Exception {
        resetStyleLangFrom();
        langFromFirst.getStyleClass().add("active");
        handleTranslate(languageFrom, languageTo, taTextToTrans.getText());
        taTransText.setText(googleTranslateAPI.getTransLang());
        tfSrcLang.setText(googleTranslateAPI.getDetectLang());
        setSpeakTo(googleTranslateAPI.getDetectLang());
    }

    private void setSpeakTo(String lang) {
        switch (lang) {
            case "Vietnamese":
                nameFrom = "Chi";
                speakFrom = "vi-vn";
                break;
            case "Korea":
                nameFrom = "Nari";
                speakFrom = "ko-kr";
                break;
            case "Russian":
                nameFrom = "Marina";
                speakFrom = "ru-ru";
                break;
            case "Chinese":
                nameFrom = "Luli";
                speakFrom = "zh-cn";
                break;
            default:
                nameFrom = "Linda";
                speakFrom = "en-gb";
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