package controller.panes.favoriteServices;

import controller.panes.ActionController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.ApplicationStart.favoriteDB;

public class WritingMode extends ActionController implements Initializable {
    private int numOfQuestions = 0;
    private int curQuestion = 0;
    private int correctQ = 0;
    private int wrongQ = 0;

    private String currentAns = "";

    @FXML
    private AnchorPane blurPane;

    @FXML
    private Label curQ;

    @FXML
    private Label lbCorrect;

    @FXML
    private Label lbFalse;

    @FXML
    private Label lbTrue;

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
            lbCorrect.setText(String.valueOf(correctQ));
            lbTrue.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> lbTrue.setVisible(false));
            pause.play();
        } else {
            wrongQ++;
            lbWrong.setText(String.valueOf(wrongQ));
            lbFalse.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> lbFalse.setVisible(false));
            pause.play();
        }
        curQuestion++;
        curQ.setText(curQuestion + "/" + numOfQuestions);
        if (curQuestion < numOfQuestions) {
            getNextQuestion();
        } else {
            showResult();
        }
        taAnswerWord.clear();
    }

    void showResult() {
        taAnswerWord.clear();
        blurPane.setVisible(true);
        resPane.setVisible(true);
    }

    void getNextQuestion() throws SQLException {
        taMeaning.setText(FavoriteUtils.getFavoriteShortMeaningAt(curQuestion));
        currentAns = FavoriteUtils.getFavoriteWordAt(curQuestion);
    }

    @FXML
    void reStart(ActionEvent event) throws SQLException {
        numOfQuestions = favoriteDB.getFavoriteWord().size();
        curQuestion = 0;
        correctQ = 0;
        wrongQ = 0;
        blurPane.setVisible(false);
        resPane.setVisible(false);
        taAnswerWord.clear();
        curQ.setText(curQuestion + "/" + numOfQuestions);
        try {
            taMeaning.setText(FavoriteUtils.getFavoriteShortMeaningAt(curQuestion));
            currentAns = FavoriteUtils.getFavoriteWordAt(curQuestion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reStart(new ActionEvent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        taAnswerWord.setOnKeyPressed(event -> {
            if (!resPane.isVisible() && event.getCode() == KeyCode.ENTER) {
                try {
                    writingSubmit(new ActionEvent());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}