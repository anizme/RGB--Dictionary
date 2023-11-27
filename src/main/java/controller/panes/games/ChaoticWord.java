package controller.panes.games;

import algorithms.Sort;
import com.jfoenix.controls.JFXButton;
import controller.panes.GameController;
import dictionary.Word;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

class Pair {
    public int x;
    public int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class ChaoticWord extends GameController implements Initializable {
    private static final String PRJ_PATH = System.getProperty("user.dir");
    private final String buttonColor = "-fx-background-color: #ef85ff;";
    private final String correctRectangle = "#35A29F";
    private final String defaultRectangle = "#a625f7";
    private final String wrongRectangle = "#D21312";
    private final double weighD = 350;
    private final double heightD = 60;
    private final int sizeCardX = 50;
    private final int sizeCardY = 50;
    private final int padding = 7;
    @FXML
    private AnchorPane downAnchorPane;
    @FXML
    private AnchorPane upAnchorPane;
    @FXML
    private AnchorPane defaultAnchorpane;
    @FXML
    private Rectangle downRectangle;
    @FXML
    private Rectangle upRectangle;
    @FXML
    private JFXButton playButton;
    @FXML
    private JFXButton replayButton;
    @FXML
    private JFXButton submitButton;
    @FXML
    private TextField meaningLabel;
    @FXML
    private TextField meaningTextField;
    @FXML
    private ImageView resultImageView;
    private final Image correct;
    private final Image wrong;
    private int numsOfLetter = 0;
    private List<Pair> position;
    private final List<Word> listWord;
    private List<Integer> upCheckIndex;
    private List<Integer> downCheckIndex;
    private List<String> rawAns;
    private List<JFXButton> allButton;
    private Set<Integer> checkExists;
    private Map<JFXButton, Integer> memoryPosition;
    private String wordTarget;
    private String wordMeaning;
    private String wordPlay;
    private String playerAns = "";
    private boolean isTimelineRunning = false;

    public ChaoticWord() throws FileNotFoundException {
        listWord = new ArrayList<>();
        checkExists = new HashSet<>();
        position = new ArrayList<>();
        upCheckIndex = new ArrayList<>();
        downCheckIndex = new ArrayList<>();
        memoryPosition = new HashMap<>();
        rawAns = new ArrayList<>();
        allButton = new ArrayList<>();
        File dictionaryFile = new File("src/main/resources/data/dictionaries.txt");
        Scanner sc = new Scanner(dictionaryFile);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split("\t");
            //System.out.println(words.length);
            Word newword = new Word(words[0], words[1]);
            listWord.add(newword);
        }
        Sort.sortDictionaryInAlphabeticalOrder(listWord);
        sc.close();
        String correctImage = PRJ_PATH + "/src/main/resources/images/correctgame3.png";
        correct = new Image(correctImage);
        String wrongImage = PRJ_PATH + "/src/main/resources/images/wronggame3.png";
        wrong = new Image(wrongImage);
    }

    public void initGame() {
        playButton.setVisible(false);
        replayButton.setVisible(true);
        submitButton.setVisible(true);
        meaningLabel.setVisible(true);
        meaningTextField.setVisible(true);
        Random random = new Random();
        int randomIndex = random.nextInt(listWord.size());
        wordTarget = listWord.get(randomIndex).getWord_target();
        wordMeaning = listWord.get(randomIndex).getWord_explain();
        wordPlay = "";
        numsOfLetter = wordTarget.length();
        int randomChar = random.nextInt(wordTarget.length());
        System.out.println("T1");
        upAnchorPane.setPrefWidth(wordTarget.length() * (sizeCardX + padding) + padding);
        upAnchorPane.setPrefHeight(sizeCardY + padding * 2);
        upRectangle.setWidth(wordTarget.length() * (sizeCardX + padding) + padding);
        upRectangle.setHeight(sizeCardY + padding * 2);
        upAnchorPane.setLayoutX((double) 850 / 2 - upAnchorPane.getPrefWidth() / 2);

        System.out.println("T2");
        downAnchorPane.setPrefWidth(wordTarget.length() * (sizeCardX + padding) + padding);
        downAnchorPane.setPrefHeight(sizeCardY + padding * 2);
        downRectangle.setWidth(wordTarget.length() * (sizeCardX + padding) + padding);
        downRectangle.setHeight(sizeCardY + padding * 2);
        downAnchorPane.setLayoutX((double) 850 / 2 - upAnchorPane.getPrefWidth() / 2);
        System.out.println("T3");
        for (int i = 0; i < wordTarget.length(); i++) {
            while (checkExists.contains(randomChar)) {
                randomChar = random.nextInt(wordTarget.length());
            }
            checkExists.add(randomChar);
            wordPlay += Character.toString(wordTarget.charAt(randomChar));
            Pair pair = new Pair(sizeCardX * i + padding * (i + 1), padding);
            position.add(pair);
            upCheckIndex.add(0);
            downCheckIndex.add(1);
            rawAns.add("_");
        }
        int k = 0;
        for (int i = 0; i < wordTarget.length(); i++) {
            JFXButton button = new JFXButton("_");
            button.setPrefSize(50, 50);
            JFXButton button1 = new JFXButton(Character.toString(wordPlay.charAt(k)));
            button1.setPrefSize(50, 50);
            button1.setOnAction(event -> {
                try {
                    play(event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            k++;
            upAnchorPane.getChildren().add(button);
            button.setLayoutX(position.get(i).x);
            button.setLayoutY(position.get(i).y);
            downAnchorPane.getChildren().add(button1);
            button1.setLayoutX(position.get(i).x);
            button1.setLayoutY(position.get(i).y);
            button.setStyle(buttonColor);
            button1.setStyle(buttonColor);
            allButton.add(button1);
            allButton.add(button);
            memoryPosition.put(button1, i);
        }
    }

    public void replay(ActionEvent event) {
        playButton.setVisible(true);
        resultImageView.setVisible(false);
        checkExists = new HashSet<>();
        position = new ArrayList<>();
        upCheckIndex = new ArrayList<>();
        downCheckIndex = new ArrayList<>();
        memoryPosition = new HashMap<>();
        rawAns = new ArrayList<>();
        playButton.setVisible(true);
        replayButton.setVisible(false);
        submitButton.setVisible(false);
        meaningLabel.setVisible(false);
        meaningTextField.setVisible(false);
        for (JFXButton button : allButton) {
            if (isButtonInNamedACPane(button, "defaultAnchorpane")) {
                defaultAnchorpane.getChildren().remove(button);
            } else if (isButtonInNamedACPane(button, "upAnchorPane")) {
                upAnchorPane.getChildren().remove(button);
            } else {
                downAnchorPane.getChildren().remove(button);
            }
        }
        upRectangle.setWidth(weighD);
        upRectangle.setHeight(heightD);
        upAnchorPane.setLayoutX(252);
        upAnchorPane.setLayoutY(126);
        downRectangle.setWidth(weighD);
        downRectangle.setHeight(heightD);
        downAnchorPane.setPrefWidth(weighD);
        downAnchorPane.setPrefHeight(heightD);
        downAnchorPane.setLayoutX(252);
        downAnchorPane.setLayoutY(290);
        upAnchorPane.setPrefWidth(weighD);
        upAnchorPane.setPrefHeight(heightD);
        allButton = new ArrayList<>();
    }

    public void submit(ActionEvent event) {
        playerAns = "";
        int upFirstEmpty = -1;
        for (int i = 0; i < numsOfLetter; i++) {
            if (upCheckIndex.get(i) == 0) {
                upFirstEmpty = i;
                break;
            }
        }
        if (upFirstEmpty == -1) {
            for (int i = 0; i < numsOfLetter; i++) {
                playerAns += rawAns.get(i);
            }
            if (playerAns.equals(wordTarget)) {
                resultImageView.setImage(correct);
                resultImageView.setVisible(true);
                meaningTextField.setText(wordMeaning);
                upRectangle.setFill(Paint.valueOf(correctRectangle));
                Timeline timeline = new Timeline();

                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(playButton.translateYProperty(), playButton.getLayoutY()));
                timeline.getKeyFrames().add(keyFrame);
                timeline.setOnFinished(e -> {
                    isTimelineRunning = false;
                    upRectangle.setFill(Paint.valueOf(defaultRectangle));
                });
                timeline.play();
            } else {
                resultImageView.setImage(wrong);
                resultImageView.setVisible(true);
                upRectangle.setFill(Paint.valueOf(wrongRectangle));
                Timeline timeline = new Timeline();

                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(playButton.translateYProperty(), playButton.getLayoutY()));
                timeline.getKeyFrames().add(keyFrame);
                timeline.setOnFinished(e -> {
                    isTimelineRunning = false;
                    upRectangle.setFill(Paint.valueOf(defaultRectangle));
                });
                timeline.play();
            }
        }
    }

    @FXML
    public void play(ActionEvent event) throws Exception {
        resultImageView.setVisible(false);
        if (isTimelineRunning) {
            return;
        }
        isTimelineRunning = true;
        JFXButton button = (JFXButton) event.getSource();
        int upFirstEmpty = -1;
        for (int i = 0; i < numsOfLetter; i++) {
            if (upCheckIndex.get(i) == 0) {
                upFirstEmpty = i;
                break;
            }
        }
        if (isButtonInNamedACPane(button, "defaultAnchorpane")) {
            int indexdownACPane = memoryPosition.get(button);
            double x = button.getLayoutX();
            double y = button.getLayoutY();
            System.out.println(x);
            final int k = (int) ((x - upAnchorPane.getLayoutX() - padding) / (sizeCardX + padding));
            System.out.println(k);
            Timeline timeline = new Timeline();

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(button.translateXProperty(), position.get(indexdownACPane).x + downAnchorPane.getLayoutX() - x),
                    new KeyValue(button.translateYProperty(), position.get(indexdownACPane).y + downAnchorPane.getLayoutY() - y));
            timeline.getKeyFrames().add(keyFrame);
            timeline.setOnFinished(e -> {
                isTimelineRunning = false;
                upCheckIndex.set(k, 0);
                rawAns.set(k, "_");
                downAnchorPane.getChildren().add(button);
                button.setLayoutX(position.get(indexdownACPane).x);
                button.setLayoutY(position.get(indexdownACPane).y);
                button.setTranslateY(0);
                button.setTranslateX(0);
            });
            timeline.play();
        } else if (isButtonInNamedACPane(button, "downAnchorPane")) {
            double x = button.getLayoutX() + downAnchorPane.getLayoutX();
            double y = button.getLayoutY() + downAnchorPane.getLayoutY();
            downAnchorPane.getChildren().remove(button);
            defaultAnchorpane.getChildren().add(button);
            button.setLayoutX(x);
            button.setLayoutY(y);
            final int k = upFirstEmpty;
            System.out.println(k);
            Timeline timeline = new Timeline();

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(button.translateXProperty(), position.get(upFirstEmpty).x + upAnchorPane.getLayoutX() - x),
                    new KeyValue(button.translateYProperty(), position.get(upFirstEmpty).y + upAnchorPane.getLayoutY() - y));
            timeline.getKeyFrames().add(keyFrame);
            timeline.setOnFinished(e -> {
                isTimelineRunning = false;
                upCheckIndex.set(k, 1);
                rawAns.set(k, button.getText());
                button.setLayoutX(position.get(k).x + upAnchorPane.getLayoutX());
                button.setLayoutY(position.get(k).y + upAnchorPane.getLayoutY());
                button.setTranslateX(0);
                button.setTranslateY(0);
            });
            timeline.play();
        }
    }

    private boolean isButtonInNamedACPane(JFXButton button, String anchorPaneName) {
        AnchorPane anchorPane = (AnchorPane) button.getParent();
        return anchorPane != null && anchorPane.getId().equals(anchorPaneName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
