package controller.panes.games;

import algorithms.Sort;
import com.jfoenix.controls.JFXButton;
import controller.panes.ActionController;
import controller.panes.GameController;
import dictionary.Word;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

public class CrossWord extends GameController implements Initializable {

    private final List<Word> wordList;
    private final int numsWord = 3;
    private final int row = 10;
    private final int col = 10;
    private final double xDefault = 64.0;
    private final double yDefault = 455.0;
    private final double xPlanet1 = 43.0;
    private final double yPlanet1 = 251.0;
    private final double xPlanet2 = 109.0;
    private final double yPlanet2 = 107.0;
    private final String gocolor = "-fx-background-color: #ff9130;";
    private final String cocolor = "-fx-background-color: #85E6C5;";
    private final String dfcolor = "-fx-background-color: #FAFEFF;";
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
    private ImageView spaceShip;
    @FXML
    private ImageView starWay1;
    @FXML
    private ImageView starWay2;
    @FXML
    private ImageView starWay3;
    @FXML
    private ImageView fireBall;
    @FXML
    private ImageView heart1;
    @FXML
    private ImageView heart2;
    @FXML
    private ImageView heart3;
    @FXML
    private TextField meaning;
    @FXML
    private TextField label1;
    @FXML
    private TextField label2;
    @FXML
    private Rectangle bgButtonGrid;
    @FXML
    private ImageView guideCrossWord;
    @FXML
    private ImageView winner;
    @FXML
    private ImageView loser;
    @FXML
    private Rectangle flash;
    private List<Word> wordPlay;
    private List<List<Character>> charBoard;
    //    private List<List<Integer>> posXY;
    private List<Integer> tmpPos;
    private int count = 0;
    private int noPlanet = 0;
    private int noWrongAns = 0;
    private boolean isRunning = false;
    private boolean isTimelineRunning = false;

    public CrossWord() throws Exception {
        wordList = new ArrayList<>();
        tmpPos = new ArrayList<>();
        File dictionaryFile = new File("src/main/resources/data/dictionaries.txt");
        Scanner sc = new Scanner(dictionaryFile);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split("\t");
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

//    public List<Integer> posButtonGo(Button button) {
//        List<Integer> res = new ArrayList<>();
//        button.setOnMouseEntered(event -> {
//            res.add(GridPane.getColumnIndex(button));
//            res.add(GridPane.getRowIndex(button));
//        });
//        button.setOnMouseExited(event -> {
//            res.remove(1);
//            res.remove(0);
//        });
//        return res;
//    }

    /**
     * Check valid panel.
     */
    private boolean checkValid(int posX, int posY, int HoV, int sizeWord) {
        if (HoV == 1) {
            if (posX + sizeWord - 1 >= col) {
                return true;
            } else {
                for (int i = posX; i < posX + sizeWord; i++) {
                    if (charBoard.get(posY).get(i) != '_') {
                        return true;
                    }
                }
            }
        } else if (HoV == 2) {
            if (posY + sizeWord - 1 >= row) {
                return true;
            } else {
                for (int i = posY; i < posY + sizeWord; i++) {
                    if (charBoard.get(i).get(posX) != '_') {
                        return true;
                    }
                }
            }
        } else {
            if (posX + sizeWord - 1 >= row || posY + sizeWord - 1 >= col) {
                return true;
            } else {
                for (int i = 0; i < sizeWord; i++) {
                    if (charBoard.get(posY + i).get(posX + i) != '_') {
                        return true;
                    }
                }
            }
        }
        return false;
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
    void setInitBoard(ActionEvent event) {
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
        guideCrossWord.setVisible(false);
        heart1.setVisible(true);
        heart2.setVisible(true);
        heart3.setVisible(true);
        Random rand = new Random();
        ObservableList<Node> panelList = initBoard.getChildren();
        for (Node x : panelList) {
            x.setStyle(dfcolor);
        }
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
            int randHorV = rand.nextInt(3) + 1;
            if (randHorV == 1) {
                while (checkValid(posX, posY, randHorV, tmp.length())) {
                    posX = rand.nextInt(row);
                    posY = rand.nextInt(col);
                }
                int k = 0;
                for (int j = posX; j < posX + tmp.length(); j++) {
                    char letter = tmp.charAt(k);
                    charBoard.get(posY).set(j, letter);
                    k++;
                }
            } else if (randHorV == 2) {
                while (checkValid(posX, posY, randHorV, tmp.length())) {
                    posX = rand.nextInt(row);
                    posY = rand.nextInt(col);
                }
                int k = 0;
                for (int j = posY; j < posY + tmp.length(); j++) {
                    char letter = tmp.charAt(k);
                    charBoard.get(j).set(posX, letter);
                    k++;
                }
            } else {
                while (checkValid(posX, posY, randHorV, tmp.length())) {
                    posX = rand.nextInt(row);
                    posY = rand.nextInt(col);
                }
                int k = 0;
                for (int j = 0; j < tmp.length(); j++) {
                    char letter = tmp.charAt(k);
                    charBoard.get(posY + j).set(posX + j, letter);
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
            if (tmp instanceof Button button) {
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
    void replay(ActionEvent event) {
        isRunning = false;
        PLAYAGAIN.setVisible(false);
        answer.setVisible(false);
        PLAY.setVisible(true);
        YES.setVisible(false);
        NO.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        meaning.setVisible(false);
        noPlanet = 0;
        spaceShipGo();
        count = 0;
        noWrongAns = 0;
        initBoard.setVisible(true);
        winner.setVisible(false);
        loser.setVisible(false);
        fireBall.setVisible(true);
        bgButtonGrid.setVisible(true);
        guideCrossWord.setVisible(true);
        heart1.setVisible(false);
        heart2.setVisible(false);
        heart3.setVisible(false);
        isTimelineRunning = false;
    }

    @FXML
    void getAns(ActionEvent event) {
        if (isTimelineRunning) {
            return;
        }
        YES.setVisible(false);
        NO.setVisible(false);
        Button button = (Button) event.getSource();
        int x, y;
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
            StringBuilder yourWord = new StringBuilder();
            if (x1 == x2 && y1 == y2) {
                Button button1 = (Button) listNode.get(y1 * col + x1);
                button1.setStyle(dfcolor);
                addHoverEffect(button1);
                tmpPos = new ArrayList<>();
                return;
            } else if (x1 == x2) {
                for (int i = y1; i <= y2; i++) {
                    Button button1 = (Button) listNode.get(i * col + x1);
                    yourWord.append(button1.getText());
                }
            } else if (y1 == y2) {
                for (int i = x1; i <= x2; i++) {
                    Button button1 = (Button) listNode.get(y1 * col + i);
                    yourWord.append(button1.getText());
                }
            } else if (y2 - y1 == x2 - x1) {
                int j = y1;
                for (int i = x1; i <= x2; i++) {
                    Button button1 = (Button) listNode.get(j * col + i);
                    yourWord.append(button1.getText());
                    j++;
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
            answer.setText(yourWord.toString());
            checkAns(yourWord.toString(), tmpPos);
            tmpPos = new ArrayList<>();
        }
    }

    void spaceShipGo() {
        double x, y;
        x = spaceShip.getLayoutX();
        y = spaceShip.getLayoutY();
        isTimelineRunning = true;
        if (noPlanet == 1) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), spaceShip);
            translateTransition.setToX(xPlanet1 - x);
            translateTransition.setToY(yPlanet1 - y);
            translateTransition.play();

            fireBall.setOpacity(1);
            fireBall.setTranslateX(0);
            Timeline timeline = new Timeline();

            KeyFrame slideIn = new KeyFrame(Duration.seconds(2),
                    new KeyValue(fireBall.translateXProperty(), xDefault - 200 - fireBall.getLayoutX()),
                    new KeyValue(fireBall.translateYProperty(), yDefault + 50 - fireBall.getLayoutY()));

            timeline.getKeyFrames().add(slideIn);
            timeline.setOnFinished(e -> {
                isTimelineRunning = false;
                fireBall.setTranslateX(0);
                fireBall.setTranslateY(0);
                fireBall.setOpacity(1);
            });

            timeline.play();
            starWay1.setVisible(false);
        } else if (noPlanet == 2) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), spaceShip);
            translateTransition.setToX(xPlanet2 - x);
            translateTransition.setToY(yPlanet2 - y);
            translateTransition.play();

            fireBall.setOpacity(1);
            fireBall.setTranslateX(0);
            fireBall.setRotate(22.0);
            Timeline timeline = new Timeline();

            KeyFrame slideIn = new KeyFrame(Duration.seconds(2),
                    new KeyValue(fireBall.translateXProperty(), xPlanet1 - 200 - fireBall.getLayoutX()),
                    new KeyValue(fireBall.translateYProperty(), yPlanet1 + 50 - fireBall.getLayoutY()));

            timeline.getKeyFrames().add(slideIn);
            timeline.setOnFinished(e -> {
                isTimelineRunning = false;
                fireBall.setTranslateX(0);
                fireBall.setTranslateY(0);
                fireBall.setOpacity(1);
            });

            timeline.play();
            starWay2.setVisible(false);
        } else if (noPlanet == 3) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), spaceShip);
            double xPlanet3 = 54.0;
            translateTransition.setToX(xPlanet3 - x);
            double yPlanet3 = 0.0;
            translateTransition.setToY(yPlanet3 - y);
            translateTransition.play();

            fireBall.setOpacity(1);
            fireBall.setTranslateX(0);
            fireBall.setRotate(53.0);
            Timeline timeline = new Timeline();

            KeyFrame slideIn = new KeyFrame(Duration.seconds(2),
                    new KeyValue(fireBall.translateXProperty(), xPlanet2 - 250 - fireBall.getLayoutX()),
                    new KeyValue(fireBall.translateYProperty(), yPlanet2 + 10 - fireBall.getLayoutY()));

            timeline.getKeyFrames().add(slideIn);
            timeline.setOnFinished(e -> {
                isTimelineRunning = false;
                fireBall.setTranslateX(0);
                fireBall.setTranslateY(0);
                fireBall.setOpacity(1);
                initBoard.setVisible(false);
                bgButtonGrid.setVisible(false);
                fireBall.setVisible(false);
                initBoard.setVisible(false);
                bgButtonGrid.setVisible(false);
                winner.setVisible(true);
            });

            timeline.play();
            starWay3.setVisible(false);
        } else {
            starWay1.setVisible(true);
            starWay2.setVisible(true);
            starWay3.setVisible(true);
            spaceShip.setTranslateX(0);
            spaceShip.setTranslateY(0);
            fireBall.setTranslateX(0);
            fireBall.setTranslateY(0);
            fireBall.setRotate(0);
        }
    }

    void spaceShipStop() {
        isTimelineRunning = true;
        if (noPlanet == 0) {
            fireBall.setOpacity(1);
            fireBall.setTranslateX(0);
            Timeline timeline = new Timeline();

            KeyFrame slideIn = new KeyFrame(Duration.seconds(2),
                    new KeyValue(fireBall.translateXProperty(), xDefault - fireBall.getLayoutX()),
                    new KeyValue(fireBall.translateYProperty(), yDefault - fireBall.getLayoutY()));

            timeline.getKeyFrames().add(slideIn);
            timeline.setOnFinished(e -> {
                flash.setVisible(true);
                flash.setOpacity(1);
                fireBall.setTranslateX(0);
                fireBall.setTranslateY(0);
                fireBall.setOpacity(1);
                Timeline timeline2 = new Timeline();

                KeyFrame slideIn2 = new KeyFrame(Duration.seconds(1),
                        new KeyValue(flash.opacityProperty(), 0));

                timeline2.getKeyFrames().add(slideIn2);
                timeline2.setOnFinished(event -> {
                    isTimelineRunning = false;
                    flash.setVisible(false);
                    if (noWrongAns == 1) {
                        heart3.setVisible(false);
                    } else if (noWrongAns == 2) {
                        heart2.setVisible(false);
                    } else if (noWrongAns == 3) {
                        heart1.setVisible(false);
                        initBoard.setVisible(false);
                        bgButtonGrid.setVisible(false);
                        fireBall.setVisible(false);
                        loser.setVisible(true);
                    }
                });
                timeline2.play();
            });

            timeline.play();
        } else if (noPlanet == 1) {
            fireBall.setOpacity(1);
            fireBall.setTranslateX(0);
            fireBall.setRotate(22.0);
            Timeline timeline = new Timeline();

            KeyFrame slideIn = new KeyFrame(Duration.seconds(2),
                    new KeyValue(fireBall.translateXProperty(), xPlanet1 - fireBall.getLayoutX()),
                    new KeyValue(fireBall.translateYProperty(), yPlanet1 - fireBall.getLayoutY()));

            timeline.getKeyFrames().add(slideIn);
            timeline.setOnFinished(e -> {
                flash.setVisible(true);
                flash.setOpacity(1);
                fireBall.setTranslateX(0);
                fireBall.setTranslateY(0);
                fireBall.setOpacity(1);
                Timeline timeline2 = new Timeline();

                KeyFrame slideIn2 = new KeyFrame(Duration.seconds(1),
                        new KeyValue(flash.opacityProperty(), 0));

                timeline2.getKeyFrames().add(slideIn2);
                timeline2.setOnFinished(event -> {
                    isTimelineRunning = false;
                    flash.setVisible(false);
                    if (noWrongAns == 1) {
                        heart3.setVisible(false);
                    } else if (noWrongAns == 2) {
                        heart2.setVisible(false);
                    } else if (noWrongAns == 3) {
                        heart1.setVisible(false);
                        initBoard.setVisible(false);
                        bgButtonGrid.setVisible(false);
                        fireBall.setVisible(false);
                        loser.setVisible(true);
                    }
                });
                timeline2.play();
            });

            timeline.play();
        } else if (noPlanet == 2) {
            fireBall.setOpacity(1);
            fireBall.setTranslateX(0);
            fireBall.setRotate(53.0);
            Timeline timeline = new Timeline();

            KeyFrame slideIn = new KeyFrame(Duration.seconds(2),
                    new KeyValue(fireBall.translateXProperty(), xPlanet2 - fireBall.getLayoutX()),
                    new KeyValue(fireBall.translateYProperty(), yPlanet2 - fireBall.getLayoutY()));

            timeline.getKeyFrames().add(slideIn);
            timeline.setOnFinished(e -> {
                flash.setVisible(true);
                flash.setOpacity(1);
                fireBall.setTranslateX(0);
                fireBall.setTranslateY(0);
                fireBall.setOpacity(1);
                Timeline timeline2 = new Timeline();

                KeyFrame slideIn2 = new KeyFrame(Duration.seconds(1),
                        new KeyValue(flash.opacityProperty(), 0));

                timeline2.getKeyFrames().add(slideIn2);
                timeline2.setOnFinished(event -> {
                    isTimelineRunning = false;
                    flash.setVisible(false);
                    if (noWrongAns == 1) {
                        heart3.setVisible(false);
                    } else if (noWrongAns == 2) {
                        heart2.setVisible(false);
                    } else if (noWrongAns == 3) {
                        heart1.setVisible(false);
                        initBoard.setVisible(false);
                        bgButtonGrid.setVisible(false);
                        fireBall.setVisible(false);
                        loser.setVisible(true);
                    }
                });
                timeline2.play();
            });

            timeline.play();
        }
    }

    void checkAns(String yourWord, List<Integer> tmpPos) {
        ObservableList<Node> listNode = initBoard.getChildren();
        int x1 = tmpPos.get(0);
        int y1 = tmpPos.get(1);
        int x2 = tmpPos.get(2);
        int y2 = tmpPos.get(3);
        for (int i = 0; i < wordPlay.size(); i++) {
            String tmp = wordPlay.get(i).getWord_target();
            tmp = tmp.toUpperCase();
            System.out.println(yourWord);
            if (tmp.equals(yourWord)) {
                System.out.println(wordPlay.get(i).getWord_target());
                YES.setVisible(true);
                NO.setVisible(false);
                meaning.setText(wordPlay.get(i).getWord_explain());
                noPlanet++;
                wordPlay.remove(i);
                i--;
                spaceShipGo();
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
                } else if (y2 - y1 == x2 - x1) {
                    int h = y1;
                    for (int k = x1; k <= x2; k++) {
                        Button button1 = (Button) listNode.get(h * col + k);
                        button1.setStyle(cocolor);
                        addHoverEffect(button1);
                        h++;
                    }
                }
                return;
            }
        }
        NO.setVisible(true);
        YES.setVisible(false);
        noWrongAns++;
        spaceShipStop();
        String wrcolor = "-fx-background-color: #FF6969;";
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
        } else if (y2 - y1 == x2 - x1) {
            int h = y1;
            for (int k = x1; k <= x2; k++) {
                Button button1 = (Button) listNode.get(h * col + k);
                button1.setStyle(wrcolor);
                h++;
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
        } else if (y2 - y1 == x2 - x1) {
            int h = y1;
            for (int k = x1; k <= x2; k++) {
                Button button1 = (Button) listNode.get(h * col + k);
                KeyValue keyValue = new KeyValue(button1.styleProperty(), dfcolor);
                KeyFrame keyFrame = new KeyFrame(duration, keyValue);
                timeline.getKeyFrames().add(keyFrame);
                h++;
            }
        }
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
