package controller.panes;

import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.VoiceRSS;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingController extends ActionController implements Initializable {
    private static final String SLIDER_STYLE_FORMAT =
            "-slider-track-color: linear-gradient(to right, -slider-filled-track-color 0%%, "
                    + "-slider-filled-track-color %1$f%%, -fx-base %1$f%%, -fx-base 100%%);";
    String[] voiceUS = {"Linda", "Amy", "Mary", "John", "Mike"};
    String[] voiceUK = {"Alice", "Nancy", "Lily", "Harry"};
    @FXML
    private ImageView backgroundView;
    @FXML
    private JFXToggleButton switchMode;

    @FXML
    private ChoiceBox<String> choiceBoxUK;

    @FXML
    private ChoiceBox<String> choiceBoxUS;

    @FXML
    private AnchorPane settingPane;

    @FXML
    private Slider slider;

    public AnchorPane getSettingPane() {
        return settingPane;
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public JFXToggleButton getSwitchMode() {
        return switchMode;
    }

    @FXML
    void saveVoice() {
        VoiceRSS.speed = slider.getValue();
        VoiceRSS.voiceNameUS = choiceBoxUS.getValue();
        VoiceRSS.voiceNameUK = choiceBoxUK.getValue();
    }

    @FXML
    void voice() throws Exception {
        VoiceRSS.speed = slider.getValue();
        VoiceRSS.speakWord("a bottle of water");
    }

    @FXML
    protected void voiceuk() throws Exception {
        VoiceRSS.Name = choiceBoxUK.getValue();
        VoiceRSS.language = "en-gb";
        VoiceRSS.speed = slider.getValue();
        VoiceRSS.speakWord("a bottle of water");
    }

    @FXML
    protected void voiceus() throws Exception {
        VoiceRSS.Name = choiceBoxUS.getValue();
        VoiceRSS.language = "en-us";
        VoiceRSS.speed = slider.getValue();
        VoiceRSS.speakWord("a bottle of water");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.styleProperty().bind(Bindings.createStringBinding(() -> {
            double percentage = (slider.getValue() - slider.getMin()) / (slider.getMax() - slider.getMin()) * 100.0;
            return String.format(Locale.US, SLIDER_STYLE_FORMAT, percentage);
        }, slider.valueProperty(), slider.minProperty(), slider.maxProperty()));

        slider.setValue(VoiceRSS.speed);
        choiceBoxUK.getItems().addAll(voiceUK);
        choiceBoxUS.getItems().addAll(voiceUS);
        choiceBoxUK.setValue(VoiceRSS.voiceNameUK);
        choiceBoxUS.setValue(VoiceRSS.voiceNameUS);
    }
}
