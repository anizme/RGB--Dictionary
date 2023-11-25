package controller.panes.games;

import algorithms.Sort;
import com.jfoenix.controls.JFXButton;
import controller.panes.GameController;
import dictionary.Word;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.List;

class Pair {
    public int x;
    public int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class ChaoticWord extends GameController implements Initializable {
    @FXML
    private AnchorPane downAnchorPane;

    @FXML
    private AnchorPane upAnchorPane;

    @FXML
    private Rectangle downRectangle;

    @FXML
    private Rectangle upRectangle;

    private int numsOfLetter = 0;
    private final int sizeCardX = 50;
    private final int sizeCardY = 50;
    private final int padding = 5;
    private List<Pair> position;
    private List<Word> listWord;
    private List<Integer> upCheckIndex;
    private List<Integer> downCheckIndex;
    private Set<Integer> checkExists;
    private String wordTarget;
    private String wordMeaning;
    private String wordPlay;
    private int sizeOfHBox;

    public ChaoticWord() throws FileNotFoundException {
        listWord = new ArrayList<>();
        checkExists = new HashSet<>();
        position = new ArrayList<>();
        upCheckIndex = new ArrayList<>();
        downCheckIndex = new ArrayList<>();
        File dictionaryFile = new File("src/main/resources/data/dictionaries.txt");
        Scanner sc = new Scanner(dictionaryFile);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split("\t");
            System.out.println(words.length);
            Word newword = new Word(words[0], words[1]);
            listWord.add(newword);
        }
        Sort.sortDictionaryInAlphabeticalOrder(listWord);
        sc.close();
    }

    public void initGame() {
        Random random = new Random();
        int randomIndex = random.nextInt(listWord.size());
        wordTarget = listWord.get(randomIndex).getWord_target();
        wordMeaning = listWord.get(randomIndex).getWord_explain();
        wordPlay = "";
        int randomChar = random.nextInt(wordTarget.length());

        upAnchorPane.setPrefWidth(wordTarget.length() * (sizeCardX + padding));
        upAnchorPane.setPrefHeight(sizeCardY + padding * 2);
        upRectangle.setWidth(wordTarget.length() * (sizeCardX + padding));
        upRectangle.setHeight(sizeCardY + padding * 2);
        upAnchorPane.setLayoutX((double) 850 / 2 - upAnchorPane.getPrefWidth() / 2);

        downAnchorPane.setPrefWidth(wordTarget.length() * (sizeCardX + padding));
        downAnchorPane.setPrefHeight(sizeCardY + padding * 2);
        downRectangle.setWidth(wordTarget.length() * (sizeCardX + padding));
        downRectangle.setHeight(sizeCardY + padding * 2);
        downAnchorPane.setLayoutX((double) 850 / 2 - upAnchorPane.getPrefWidth() / 2);
        for (int i = 0; i < wordTarget.length(); i++) {
            while (checkExists.contains(randomChar)) {
                randomChar = random.nextInt(wordTarget.length());
            }
            checkExists.add(randomChar);
            wordPlay += Character.toString(wordTarget.charAt(randomChar));
            Pair pair = new Pair(sizeCardX * i + padding * (i + 1), padding);
            position.add(pair);
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
        }
    }

    @FXML
    public void play(ActionEvent event) throws Exception {
        ;
    }

    private boolean isButtonInNamedHBox(JFXButton button, String hboxName) {
        HBox parentHBox = (HBox) button.getParent();
        return parentHBox != null && parentHBox.getId().equals(hboxName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ;
    }
}
