package controller.panes.games;

import algorithms.Sort;
import com.jfoenix.controls.JFXButton;
import controller.panes.GameController;
import dictionary.Word;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

public class CrossWord extends GameAction implements Initializable {

    @FXML
    private GridPane initBoard;

    @FXML
    private JFXButton PLAY;

    @FXML
    private JFXButton PLAYAGAIN;

    @FXML
    private TextField answer;

    @FXML
    private ImageView YES;

    @FXML
    private ImageView NO;

    @FXML
    private TextField meaning;

    @FXML
    private TextField label1;

    @FXML
    private TextField label2;

    @FXML
    private AnchorPane gamePane;

    private List<Word> wordList;
    private List<Word> wordPlay;
    private List<List<Character>> charBoard;
    private List<String> playerAnswer;
    private List<List<Integer>> posXY;
    private List<Integer> tmpPos;
    private int numsWord = 2;
    private int row = 10;
    private int col = 10;
    private int count = 0;
    private String gocolor = "-fx-background-color: #ff9130;";
    private String cocolor = "-fx-background-color: #85E6C5;";
    private String wrcolor = "-fx-background-color: #FF6969;";
    private String dfcolor = "-fx-background-color: #FBFFDC;";
    private boolean isRunning = false;
    private URL url;
    private ResourceBundle resourceBundle;

    public CrossWord() throws Exception {
        wordList = new ArrayList<>();
        tmpPos = new ArrayList<>();
        File dictionaryFile = new File("src/main/resources/data/dictionaries.txt");
        Scanner sc = new Scanner(dictionaryFile);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split("\t");
            System.out.println(words.length);
            Word newword = new Word(words[0], words[1]);
            wordList.add(newword);
        }
        Sort.sortDictionaryInAlphabeticalOrder(wordList);
        sc.close();
        if (PLAY != null)
            PLAY.setStyle("-fx-background-color: rgb(152, 238, 204);");
    }

    private void addHoverEffect(Button button) {
        String dfcolor = button.getStyle();
        button.setOnMouseEntered(event -> button.setStyle(gocolor));
        button.setOnMouseExited(event -> button.setStyle(dfcolor));
    }

    public List<Integer> posButtonGo(Button button) {
        List<Integer> res = new ArrayList<>();
        button.setOnMouseEntered(event -> {
            res.add(GridPane.getColumnIndex(button));
            res.add(GridPane.getRowIndex(button));
        });
        button.setOnMouseExited(event -> {
            res.remove(1);
            res.remove(0);
        });
        return res;
    }

    /**
     * Check valid panel.
     */
    boolean checkValid(int posX, int posY, int HoV, int sizeWord) {
        if (HoV == 1) {
            if (posX + sizeWord - 1 >= col) {
                return false;
            } else {
                for (int i = posX; i < posX + sizeWord; i++) {
                    if (charBoard.get(posY).get(i) != '_') {
                        return false;
                    }
                }
            }
        } else {
            if (posY + sizeWord - 1 >= row) {
                return false;
            } else {
                for (int i = posY; i < posY + sizeWord; i++) {
                    if (charBoard.get(i).get(posX) != '_') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Create list word, which will be this game.
     */
    void initWordPlay() {
        if (isRunning) {
            return;
        }
        wordPlay = new ArrayList<>();
        charBoard = new ArrayList<>();
        playerAnswer = new ArrayList<>();
        int lengthListWord = wordList.size();
        System.out.println(lengthListWord);
        Random rand = new Random();
        while (wordPlay.size() < numsWord) {
            int ranNum = rand.nextInt(lengthListWord - 1) + 1;
            if (wordList.get(ranNum).getWord_target().length() >= row) {
                continue;
            }
            wordPlay.add(wordList.get(ranNum));
        }

        for (int i = 0; i < row; i++) {
            List<Character> tmp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                tmp.add('_');
            }
            charBoard.add(tmp);
        }
    }

    @FXML
    void setInitBoard(ActionEvent event) throws Exception {
        YES.setVisible(false);
        NO.setVisible(false);
        initWordPlay();
        answer.setVisible(true);
        answer.setText("");
        meaning.setVisible(true);
        meaning.setText("");
        label1.setVisible(true);
        label2.setVisible(true);
        isRunning = true;
        PLAY.setVisible(false);
        PLAYAGAIN.setVisible(true);
        Random rand = new Random();
        ObservableList<Node> panelList = initBoard.getChildren();
        for (Node x : panelList) {
            addHoverEffect((Button) x);
        }
        // Create null board
        for (int i = 0; i < numsWord; i++) {
            String tmp = wordPlay.get(i).getWord_target();
            tmp = tmp.toUpperCase();
            System.out.println(tmp);
            int posX = rand.nextInt(row);
            int posY = rand.nextInt(col);
            int randHorV = rand.nextInt(2) + 1;
            if (randHorV == 1) {
                while (!checkValid(posX, posY, randHorV, tmp.length())) {
                    posX = rand.nextInt(row);
                    posY = rand.nextInt(col);
                }
                int k = 0;
                for (int j = posX; j < posX + tmp.length(); j++) {
                    char letter = tmp.charAt(k);
                    charBoard.get(posY).set(j, letter);
                    k++;
                }
            } else {
                while (!checkValid(posX, posY, randHorV, tmp.length())) {
                    posX = rand.nextInt(row);
                    posY = rand.nextInt(col);
                }
                int k = 0;
                for (int j = posY; j < posY + tmp.length(); j++) {
                    char letter = tmp.charAt(k);
                    charBoard.get(j).set(posX, letter);
                    k++;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (charBoard.get(i).get(j) == '_') {
                    int randNum = rand.nextInt(26) + 65;
                    char c = (char) randNum;
                    charBoard.get(i).set(j, c);
                }
            }
        }
        int x = 0, y = 0;
        for (Node tmp : panelList) {
            if (tmp instanceof Button) {
                Button button = (Button) tmp;
                button.setText(Character.toString(charBoard.get(y).get(x)));
                if (x < row - 1) {
                    x++;
                } else {
                    x = 0;
                    y++;
                }
            }
        }
    }

    @FXML
    void replay(ActionEvent event) throws Exception {
        isRunning = false;
        PLAYAGAIN.setVisible(false);
        answer.setVisible(false);
        PLAY.setVisible(true);
        YES.setVisible(false);
        NO.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        meaning.setVisible(false);
    }

    @FXML
    void getAns(ActionEvent event) throws Exception {
        YES.setVisible(false);
        NO.setVisible(false);
        Button button = (Button) event.getSource();
        int x = 0, y = 0;
        if (GridPane.getColumnIndex(button) != null && GridPane.getRowIndex(button) != null) {
            x = GridPane.getColumnIndex(button);
            y = GridPane.getRowIndex(button);
            tmpPos.add(x);
            tmpPos.add(y);
        } else if (GridPane.getColumnIndex(button) != null && GridPane.getRowIndex(button) == null) {
            x = GridPane.getColumnIndex(button);
            tmpPos.add(x);
            tmpPos.add(0);
        } else if (GridPane.getColumnIndex(button) == null && GridPane.getRowIndex(button) != null) {
            y = GridPane.getRowIndex(button);
            tmpPos.add(0);
            tmpPos.add(y);
        } else {
            tmpPos.add(0);
            tmpPos.add(0);
        }
        count++;
        if (count == 1) {
            int x1 = tmpPos.get(0);
            int y1 = tmpPos.get(1);
            ObservableList<Node> listNode = initBoard.getChildren();
            Button button1 = (Button) listNode.get(y1 * col + x1);
            button1.setStyle(gocolor);
            addHoverEffect(button1);
        }
        if (count == 2) {
            count = 0;
            ObservableList<Node> listNode = initBoard.getChildren();
            int x1 = tmpPos.get(0);
            int y1 = tmpPos.get(1);
            int x2 = tmpPos.get(2);
            int y2 = tmpPos.get(3);
            String yourWord = "";
            if (x1 == x2) {
                for (int i = y1; i <= y2; i++) {
                    Button button1 = (Button) listNode.get(i * col + x1);
                    yourWord += button1.getText();
                }
            } else if (y1 == y2) {
                for (int i = x1; i <= x2; i++) {
                    Button button1 = (Button) listNode.get(y1 * col + i);
                    yourWord += button1.getText();
                }
            }
            for (Node node : listNode) {
                Button button1 = (Button) node;
                if (button1.getStyle().equals(cocolor)) {
                    continue;
                }
                button1.setStyle(dfcolor);
                addHoverEffect(button1);
            }
            System.out.println("Your word: " + yourWord);
            if (yourWord != null) {
                playerAnswer.add(yourWord);
                answer.setText(yourWord);
                checkAns(yourWord, tmpPos);
            }
            tmpPos = new ArrayList<>();
        }
    }

    void checkAns(String yourWord, List<Integer> tmpPos) {
        ObservableList<Node> listNode = initBoard.getChildren();
        int x1 = tmpPos.get(0);
        int y1 = tmpPos.get(1);
        int x2 = tmpPos.get(2);
        int y2 = tmpPos.get(3);
        for (int i = 0; i < numsWord; i++) {
            String tmp = wordPlay.get(i).getWord_target();
            tmp = tmp.toUpperCase();
            if (tmp.equals(yourWord)) {
                System.out.println(wordPlay.get(i).getWord_target());
                YES.setVisible(true);
                NO.setVisible(false);
                meaning.setText(wordPlay.get(i).getWord_explain());
                if (x1 == x2) {
                    for (int k = y1; k <= y2; k++) {
                        Button button1 = (Button) listNode.get(k * col + x1);
                        button1.setStyle(cocolor);
                        addHoverEffect(button1);
                    }
                } else if (y1 == y2) {
                    for (int k = x1; k <= x2; k++) {
                        Button button1 = (Button) listNode.get(y1 * col + k);
                        button1.setStyle(cocolor);
                        addHoverEffect(button1);
                    }
                }
                return;
            }
        }
        NO.setVisible(true);
        YES.setVisible(false);
        if (x1 == x2) {
            for (int k = y1; k <= y2; k++) {
                Button button1 = (Button) listNode.get(k * col + x1);
                button1.setStyle(wrcolor);
            }
        } else if (y1 == y2) {
            for (int k = x1; k <= x2; k++) {
                Button button1 = (Button) listNode.get(y1 * col + k);
                button1.setStyle(wrcolor);
            }
        }
        Timeline timeline = new Timeline();
        Duration duration = Duration.seconds(0.5);
        if (x1 == x2) {
            for (int k = y1; k <= y2; k++) {
                Button button1 = (Button) listNode.get(k * col + x1);
                KeyValue keyValue = new KeyValue(button1.styleProperty(), dfcolor);
                KeyFrame keyFrame = new KeyFrame(duration, keyValue);
                timeline.getKeyFrames().add(keyFrame);
            }
        } else if (y1 == y2) {
            for (int k = x1; k <= x2; k++) {
                Button button1 = (Button) listNode.get(y1 * col + k);
                KeyValue keyValue = new KeyValue(button1.styleProperty(), dfcolor);
                KeyFrame keyFrame = new KeyFrame(duration, keyValue);
                timeline.getKeyFrames().add(keyFrame);
            }
        }
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
