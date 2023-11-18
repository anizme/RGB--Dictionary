package controller.panes;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import services.DatabaseConnect;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class FavoriteController extends ActionController implements Initializable{

    @FXML
    private ListView<String> favoriteList;
    @FXML
    private TextField favoriteTextField;
    @FXML
    private JFXButton flashCardButton;
    @FXML
    private AnchorPane favoriteAnchorpane;
    @FXML
    private AnchorPane flashCardAnchorpane;
    @FXML
    private Label cards;
    @FXML
    private JFXButton rightButton;
    @FXML
    private JFXButton leftButton;
    @FXML
    private Label sttLabel;
    @FXML
    private ImageView backgroundView;

    private int stt = 0;
    private int check = 0;

    public FavoriteController() throws SQLException {
        if (favoriteList != null) {
            favoriteList.getItems().addAll(DatabaseConnect.getFavorite());
        }
    }

    public AnchorPane getFavoritePane() {
        return favoriteAnchorpane;
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public void playFlashCard() throws SQLException {
        favoriteAnchorpane.setVisible(false);
        flashCardAnchorpane.setVisible(true);
        cards.setText(DatabaseConnect.getFavoriteWord(stt+1).get(check));
        check = 1;
    }

    public void updateListView(ActionEvent event) throws Exception {
        if (favoriteList != null) {
            favoriteList.getItems().clear();
            favoriteList.getItems().addAll(DatabaseConnect.getFavoriteWordShortMeaning());
        }
    }

    public void searchFavorite(ActionEvent event) throws Exception {
        String tmp = favoriteTextField.getText();

    }

    public void right(ActionEvent event) throws Exception {
        if (stt < DatabaseConnect.getFavorite().size() - 1) {
            stt += 1;
        }
        rightSlide(cards);
        check = 0;
        cards.setText(DatabaseConnect.getFavoriteWord(stt+1).get(check));
        check = 1;
        sttLabel.setText(Integer.toString(stt));
    }

    public void left(ActionEvent event) throws Exception {
        if (stt > 0) {
            stt -= 1;
        }
        leftSlide(cards);
        check = 0;
        cards.setText(DatabaseConnect.getFavoriteWord(stt+1).get(check));
        check = 1;
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

    public void back(ActionEvent event) throws Exception {
        favoriteAnchorpane.setVisible(true);
        flashCardAnchorpane.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                        cards.setText(DatabaseConnect.getFavoriteWord(stt+1).get(check));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    check = 1;
                } else {
                    try {
                        cards.setText(DatabaseConnect.getFavoriteWord(stt+1).get(check));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    check = 0;
                }
                // Remove the Rotate transform
                cards.getTransforms().remove(rotate);
            });
            // Play the animation
            timeline.play();
        });
    }
}
