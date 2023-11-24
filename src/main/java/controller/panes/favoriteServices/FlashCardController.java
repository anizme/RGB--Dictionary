package controller.panes.favoriteServices;

import controller.panes.ActionController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.ApplicationStart.favoriteDB;

public class FlashCardController extends ActionController implements Initializable {

    @FXML
    private Label cards;

    @FXML
    private Label sttLabel;

    private int stt = 0;

    //check = 0 means card turns to word
    //check = 1 means card turns to short meaning
    private int check = 0;

    @FXML
    void left(ActionEvent event) throws SQLException {
        if (stt > 0) {
            stt -= 1;
        }
        leftSlide(cards);
        check = 0;
        cards.setText(FavoriteUtils.getFavoriteSpecificPropertyAt(check, stt));
        sttLabel.setText(Integer.toString(stt));
    }

    @FXML
    void right(ActionEvent event) throws SQLException {
        if (stt < favoriteDB.getFavorite().size() - 1) {
            stt += 1;
        }
        rightSlide(cards);
        check = 0;
        cards.setText(FavoriteUtils.getFavoriteSpecificPropertyAt(check, stt));
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
            cards.setText(FavoriteUtils.getFavoriteSpecificPropertyAt(check, stt));
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
                        cards.setText(FavoriteUtils.getFavoriteSpecificPropertyAt(++check, stt));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (check == 1) {
                    try {
                        cards.setText(FavoriteUtils.getFavoriteSpecificPropertyAt(--check, stt));
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


