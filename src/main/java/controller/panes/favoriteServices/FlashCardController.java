package controller.panes.favoriteServices;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import services.DatabaseConnect;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FlashCardController extends FavoriteAction implements Initializable {

    @FXML
    private Label cards;

    @FXML
    private AnchorPane flashCardAnchorpane;

    @FXML
    private JFXButton leftButton;

    @FXML
    private JFXButton rightButton;

    @FXML
    private Label sttLabel;

    private int stt = 0;

    private int check = 0;

    @FXML
    void left(ActionEvent event) throws SQLException {
        if (stt > 0) {
            stt -= 1;
        }
        leftSlide(cards);
        check = 0;
        cards.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(stt)).get(check));
        sttLabel.setText(Integer.toString(stt));
    }

    @FXML
    void right(ActionEvent event) throws SQLException {
        if (stt < DatabaseConnect.getFavorite().size() - 1) {
            stt += 1;
        }
        rightSlide(cards);
        check = 0;
        cards.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(stt)).get(check));
        sttLabel.setText(Integer.toString(stt));
    }

    private void leftSlide(Label textField) {
        textField.setOpacity(0);
        textField.setTranslateX(-200);
        Timeline timeline = new Timeline();

        KeyFrame slideIn = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(textField.translateXProperty(), 0),
                new KeyValue(textField.opacityProperty(), 1));

        timeline.getKeyFrames().add(slideIn);

        timeline.play();
    }

    private void rightSlide(Label textField) {
        textField.setOpacity(0);
        textField.setTranslateX(200);
        Timeline timeline = new Timeline();

        KeyFrame slideIn = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(textField.translateXProperty(), 0),
                new KeyValue(textField.opacityProperty(), 1));

        timeline.getKeyFrames().add(slideIn);

        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cards.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(stt)).get(check));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        check = 0;
        cards.setOnMouseClicked(e -> {
            Rotate rotate = new Rotate();
            rotate.setAxis(Rotate.Y_AXIS);
            double duration = 0.4;

            // Flip from 0 to 180 degrees
            KeyValue keyValue1 = new KeyValue(rotate.angleProperty(), 0);
            KeyValue keyValue2 = new KeyValue(rotate.angleProperty(), 180);

            // Create a Timeline for the flip animation
            KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(duration / 2), keyValue1);
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(duration), keyValue2);
            Timeline timeline = new Timeline(keyFrame1, keyFrame2);

            // Set up the rotation axis
            rotate.setPivotX(cards.getWidth() / 2);
            rotate.setPivotY(cards.getHeight() / 2);
            cards.getTransforms().add(rotate);

            timeline.setOnFinished(evt -> {
                if (check == 0) {
                    try {
                        cards.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(stt)).get(++check));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (check == 1){
                    try {
                        cards.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(stt)).get(--check));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                // Remove the Rotate transform
                cards.getTransforms().remove(rotate);
            });
            // Play the animation
            timeline.play();
        });
    }
}


