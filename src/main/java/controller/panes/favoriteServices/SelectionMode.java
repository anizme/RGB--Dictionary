package controller.panes.favoriteServices;

import com.jfoenix.controls.JFXButton;
import controller.panes.ActionController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import static controller.ApplicationStart.favoriteDB;

public class SelectionMode extends ActionController implements Initializable {

    List<JFXButton> options = new ArrayList<>();
    private int numOfQuestions = 0;
    private int curQuestion = 0;
    private int correctQ = 0;
    private int wrongQ = 0;
    private String currentAns = "";

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
    }

    void showResult() {
        blurPane.setVisible(true);
        resPane.setVisible(true);
    }

    void getNextQuestion() throws SQLException {
        taMeaning.setText(FavoriteUtils.getFavoriteShortMeaningAt(curQuestion));
        currentAns = FavoriteUtils.getFavoriteWordAt(curQuestion);
        genOptions();
    }

    void genOptions() throws SQLException {
        Random random = new Random();
        int randomAns = random.nextInt(4);
        options.get(randomAns).setText(currentAns);
        int[] op = new int[3];
        op[0] = random.nextInt(numOfQuestions);
        while (FavoriteUtils.getFavoriteWordAt(op[0]).equals(currentAns)) {
            op[0] = random.nextInt(numOfQuestions);
        }
        op[1] = random.nextInt(numOfQuestions);
        while (op[1] == op[0] ||
                FavoriteUtils.getFavoriteWordAt(op[1]).equals(currentAns)) {
            op[1] = random.nextInt(numOfQuestions);
        }
        op[2] = random.nextInt(numOfQuestions);
        while (op[2] == op[1] || op[2] == op[0] |
                FavoriteUtils.getFavoriteWordAt(op[2]).equals(currentAns)) {
            op[2] = random.nextInt(numOfQuestions);
        }
        int j = 0;
        for (int i = 0; i < 4; i++) {
            if (i != randomAns) {
                options.get(i).setText(FavoriteUtils.getFavoriteWordAt(op[j]));
                j++;
            }
        }
    }

    @FXML
    void reStart(ActionEvent event) throws SQLException {
        numOfQuestions = favoriteDB.getFavoriteWord().size();
        curQuestion = 0;
        correctQ = 0;
        wrongQ = 0;
        blurPane.setVisible(false);
        resPane.setVisible(false);
        curQ.setText(curQuestion + "/" + numOfQuestions);
        try {
            taMeaning.setText(FavoriteUtils.getFavoriteShortMeaningAt(curQuestion));
            currentAns = FavoriteUtils.getFavoriteWordAt(curQuestion);
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
