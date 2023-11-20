package controller.panes.favoriteServices;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import services.DatabaseConnect;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class SelectionMode extends FavoriteAction implements Initializable {

    private int numOfQuestions = 0;
    private int curQuestion = 0;
    private int correctQ = 0;
    private int wrongQ = 0;
    private String currentAns = "";

    List<JFXButton> options = new ArrayList<>();

    @FXML
    private AnchorPane blurPane;

    @FXML
    private JFXButton btA;

    @FXML
    private JFXButton btB;

    @FXML
    private JFXButton btC;

    @FXML
    private JFXButton btD;

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
    private TextArea taMeaning;

    @FXML
    void submit(ActionEvent event) throws SQLException {
        if (currentAns.toLowerCase().trim().equals(((JFXButton) event.getSource()).getText().toLowerCase().trim())) {
            correctQ++;
            lbCorrect.setText(String.valueOf(correctQ));
            lbTrue.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                lbTrue.setVisible(false);
            });
            pause.play();
        } else {
            wrongQ++;
            lbWrong.setText(String.valueOf(wrongQ));
            lbFalse.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                lbFalse.setVisible(false);
            });
            pause.play();
        }
        curQuestion++;
        curQ.setText(String.valueOf(curQuestion) + "/" + String.valueOf(numOfQuestions));
        if (curQuestion < numOfQuestions) {
            getNextQuestion();
        } else {
            showResult();
        }
    }

    void showResult() {
        blurPane.setVisible(true);
        resPane.setVisible(true);
    }

    void getNextQuestion() throws SQLException {
        taMeaning.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(1));
        currentAns = DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(0);
        genOptions();
    }

    void genOptions() throws SQLException {
        Random random = new Random();
        int randomAns = random.nextInt(4);
        options.get(randomAns).setText(currentAns);
        for (int i = 0; i < 4; i++) {
            if (i != randomAns) {
                int distractAns = random.nextInt(numOfQuestions);
                while (DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(distractAns)).get(0).equals(currentAns)) {
                    distractAns = random.nextInt(numOfQuestions);
                }
                options.get(i).setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(distractAns)).get(0));
            }
        }
    }

    @FXML
    void reStart(ActionEvent event) throws SQLException {
        numOfQuestions = DatabaseConnect.getFavorite().size();
        curQuestion = 0;
        correctQ = 0;
        wrongQ = 0;
        blurPane.setVisible(false);
        resPane.setVisible(false);
        curQ.setText(String.valueOf(curQuestion) + "/" + String.valueOf(numOfQuestions));
        try {
            taMeaning.setText(DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(1));
            currentAns = DatabaseConnect.getFavoriteWord(DatabaseConnect.getFavorite().get(curQuestion)).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        genOptions();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        options.add(btA);
        options.add(btB);
        options.add(btC);
        options.add(btD);
        try {
            reStart(new ActionEvent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
