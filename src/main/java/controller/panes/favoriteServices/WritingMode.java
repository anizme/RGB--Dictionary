package controller.panes.favoriteServices;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import services.DatabaseConnect;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Delayed;
import java.util.zip.InflaterInputStream;

public class WritingMode extends FavoriteAction implements Initializable {
    private int numOfQuestions = 0;
    private int curQuestion = 0;
    private int correctQ = 0;
    private int wrongQ = 0;

    private String currentAns = "";

    @FXML
    private Label lbFalse;

    @FXML
    private Label lbTrue;

    @FXML
    private AnchorPane blurPane;

    @FXML
    private Label lbCorrect;

    @FXML
    private Label lbWrong;

    @FXML
    private Pane resPane;

    @FXML
    private TextArea taAnswerWord;

    @FXML
    private TextArea taMeaning;

    @FXML
    void writingSubmit(ActionEvent event) throws SQLException {
        if (taAnswerWord.getText().toLowerCase().trim().equals(currentAns.toLowerCase().trim())) {
            correctQ++;
            lbTrue.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                lbTrue.setVisible(false);
            });
            pause.play();
        } else {
            wrongQ++;
            lbFalse.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                lbFalse.setVisible(false);
            });
            pause.play();
        }
        if (curQuestion + 1 < numOfQuestions) {
            curQuestion++;
            getNextQuestion();
        } else {
            showResult();
        }
        taAnswerWord.clear();
    }

    void showResult() {
        taAnswerWord.clear();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
        });
        pause.pause();

        blurPane.setVisible(true);
        resPane.setVisible(true);
        lbCorrect.setText(String.valueOf(correctQ));
        lbWrong.setText(String.valueOf(wrongQ));
    }

    void getNextQuestion() throws SQLException {
        taMeaning.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(1));
        currentAns = DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(0);
    }

    @FXML
    void reStart(ActionEvent event) throws SQLException {
        numOfQuestions = DatabaseConnect.getFavorite().size();
        curQuestion = 0;
        correctQ = 0;
        wrongQ = 0;
        blurPane.setVisible(false);
        resPane.setVisible(false);
        taAnswerWord.clear();
        try {
            taMeaning.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(1));
            currentAns = DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbTrue.setVisible(false);
        lbWrong.setVisible(false);
        try {
            taMeaning.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(1));
            currentAns = DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        taAnswerWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (!resPane.isVisible() && event.getCode() == KeyCode.ENTER) {
                    try {
                        writingSubmit(new ActionEvent());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}