package controller.panes.games;

import com.jfoenix.controls.JFXButton;
import controller.ApplicationStart;
import controller.panes.ActionController;
import dictionary.Word;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import services.ImageViewSprite;
import services.SpriteAnimation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class Hangman extends ActionController implements Initializable {
    private String answer;
    private String guessed;
    private final int MAX_WRONG_GUESSES = 7;
    private int wrongGuesses = 0;
    private boolean isNewGame = false;
    private Word answerWord;
    @FXML
    private JFXButton playButton;
    @FXML
    private GridPane letterPane;
    private boolean[] isClicked;
    @FXML
    private HBox healthBar;
    @FXML
    private Label wordLabel;
    @FXML
    private AnchorPane resultPane;
    @FXML
    private Label resultLabel;
    @FXML
    private JFXButton replayButton;
    @FXML
    private ImageView backgroundView1;
    private Image backgroundLayer1;
    private ImageViewSprite backgroundViewSprite1;
    @FXML
    private ImageView backgroundView2;
    private Image backgroundLayer2;
    private ImageViewSprite backgroundViewSprite2;
    @FXML
    private ImageView backgroundView3;
    private Image backgroundLayer3;
    private ImageViewSprite backgroundViewSprite3;
    private ImageViewSprite shieldViewSprite;
    @FXML
    private ImageView shipView;
    private Image shipFullHealth;
    private Image shipSlightDamaged;
    private Image shipDamaged;
    private Image shipVeryDamaged;
    @FXML
    private ImageView shieldView;
    private Image shieldImage;
    @FXML
    private ImageView engineView;
    private Image engineImage;
    private ImageViewSprite engineViewSprite;
    @FXML
    private AnchorPane instruction;
    @FXML
    private JFXButton instructionButton;
    private Image asteroidImage;
    @FXML
    private ImageView asteroidView;
    private TranslateTransition asteroidMovement;
    private SpriteAnimation shipCollisionAnimation;
    @FXML
    private ImageView shipCollision;
    @FXML
    private Rectangle shipCollisionPosition;
    @FXML
    private ImageView shieldCollision;
    @FXML
    private Rectangle shieldCollisionPosition;
    private SpriteAnimation shieldCollisionAnimation;
    private SpriteAnimation explosionAnimation;
    @FXML
    private ImageView explosionView;
    private Image explosionImage;
    @FXML
    private ImageView earthView;
    private Image earthImage;
    private ImageViewSprite earthViewSprite;
    @FXML
    private ImageView welcomeHomeView;
    private Timeline timeline;
    private boolean isReady;
    private PauseTransition delay;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setGuessed(String guessed) {
        this.guessed = guessed;
    }

    public void updateGuessed(char c) {
        String newGuessed = "";
        for (int i = 0; i < guessed.length(); i++) {
            if (answer.toUpperCase().charAt(i) == c) {
                newGuessed += c;
            } else {
                newGuessed += guessed.charAt(i);
            }
        }
        setGuessed(newGuessed);
    }

    public boolean checkGuess(char c) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.toUpperCase().charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void letterChoose(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        if (isReady && !isClicked[(int) button.getText().charAt(0) - 65]) {
            isReady = false;
            isClicked[(int) button.getText().charAt(0) - 65] = true;
            delay.play();
            if (isNewGame) {
                wrongGuesses = 0;
                isNewGame = false;
            }
            asteroidView.setVisible(true);
            asteroidMovement = new TranslateTransition(Duration.millis(500), asteroidView);
            asteroidMovement.setByY(160);
            asteroidMovement.play();
            if (!checkGuess(button.getText().charAt(0)) && !guessed.equals(answer.toUpperCase())) {
                if (wrongGuesses < MAX_WRONG_GUESSES) {
                    wrongGuesses++;
                    button.setStyle("-fx-background-color: lightsalmon; -fx-background-radius: 10px; " +
                            "-fx-text-fill: black; -fx-border-radius: 10px; -fx-border-width: 2px;");

                    //asteroids.add(newAsteroid);

                    ObservableBooleanValue colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {

                        @Override
                        public Boolean call() throws Exception {
                            return asteroidView.getBoundsInParent().intersects(shipCollisionPosition.getBoundsInParent());
                        }

                    }, asteroidView.boundsInParentProperty(), shipCollisionPosition.boundsInParentProperty());

                    colliding.addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> obs,
                                            Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                System.out.println("Colliding");

                                healthBar.getChildren().get(MAX_WRONG_GUESSES - wrongGuesses).setStyle("-fx-fill: transparent");
                                String healthColor = "";
                                switch (wrongGuesses) {
                                    case 0:
                                    case 1:
                                        healthColor = "#0fc618";
                                        shipView.setImage(shipFullHealth);
                                        break;
                                    case 2:
                                    case 3:
                                        healthColor = "#edf500";
                                        shipView.setImage(shipSlightDamaged);
                                        break;
                                    case 4:
                                    case 5:
                                        healthColor = "#ff7b00";
                                        shipView.setImage(shipDamaged);
                                        break;
                                    case 6:
                                        healthColor = "red";
                                        shipView.setImage(shipVeryDamaged);
                                        break;
                                }
                                for (int i = 0; i < MAX_WRONG_GUESSES - wrongGuesses; i++) {
                                    healthBar.getChildren().get(i).setStyle("-fx-fill: " + healthColor);
                                }
                                asteroidMovement.stop();
                                asteroidView.setVisible(false);
                                asteroidMovement = new TranslateTransition(Duration.millis(1), asteroidView);
                                asteroidMovement.setByY(-160);
                                asteroidMovement.play();
                                shipCollision.setVisible(true);
                                shipCollisionAnimation.play();
                                if (wrongGuesses == MAX_WRONG_GUESSES) {
                                    shipView.setVisible(false);
                                    engineView.setVisible(false);
                                    explosionView.setVisible(true);
                                    explosionAnimation.play();
                                    explosionAnimation.setOnFinished(event -> {
                                        explosionView.setVisible(false);
                                    });

                                    resultLabel.setText("YOU LOSE !\n" + "The answer is " + answer.toUpperCase() + "\nMeaning : " + answerWord.getWord_explain());
                                    resultPane.setDisable(false);
                                    resultPane.setVisible(true);
                                    letterPane.setDisable(true);
                                    letterPane.setVisible(false);
                                }
                            } else {
                                System.out.println("Not colliding");
                            }
                        }
                    });

                }

            } else if (checkGuess(button.getText().charAt(0))) {
                button.setStyle("-fx-background-color: mediumspringgreen; -fx-background-radius: 10px; " +
                        "-fx-text-fill: black; -fx-border-radius: 10px; -fx-border-width: 2px;");
                updateGuessed(button.getText().charAt(0));
                wordLabel.setText(guessed);
                shieldView.setVisible(true);
                ObservableBooleanValue colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return asteroidView.getBoundsInParent().intersects(shieldCollisionPosition.getBoundsInParent());
                    }
                }, asteroidView.boundsInParentProperty(), shieldCollisionPosition.boundsInParentProperty());

                colliding.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs,
                                        Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            if (shieldView.isVisible()) {
                                System.out.println("Colliding");
                                asteroidMovement.stop();
                                asteroidView.setVisible(false);
                                asteroidMovement = new TranslateTransition(Duration.millis(1), asteroidView);
                                asteroidMovement.setByY(-121);
                                asteroidMovement.play();
                                shieldCollision.setVisible(true);
                                shieldCollisionAnimation.play();
                                shieldCollisionAnimation.setOnFinished(event1 -> {
                                    shieldView.setVisible(false);
                                    if (guessed.equals(answer.toUpperCase())) {
                                        resultLabel.setText("YOU WIN !\n" + "The answer is " + answer.toUpperCase() + "\nMeaning : " + answerWord.getWord_explain());
                                        resultPane.setDisable(false);
                                        resultPane.setVisible(true);
                                        letterPane.setDisable(true);
                                        letterPane.setVisible(false);
                                        earthView.setVisible(true);
                                        welcomeHomeView.setVisible(true);
                                        //timeline.play();
                                    }
                                });
                            }
                        } else {
                            System.out.println("Not colliding");
                        }
                    }
                });
            }
        }
    }

    @FXML
    void play(ActionEvent event) {
        isClicked = new boolean[26];
        instruction.setDisable(true);
        instruction.setVisible(false);
        instructionButton.setDisable(false);
        instructionButton.setVisible(true);
        earthView.setVisible(false);
        welcomeHomeView.setVisible(false);

        isNewGame = true;
        wordLabel.setVisible(true);
        shipView.setImage(shipFullHealth);
        shipView.setVisible(true);
        engineView.setVisible(true);

        for (int i = 0; i < healthBar.getChildren().size(); i++) {
            healthBar.getChildren().get(i).setStyle("-fx-fill: #0fc618");
        }
        Random r = new Random();
        answerWord = ApplicationStart.dictionaryManagement.getDictionary().getListOfWords()
                .get(r.nextInt(ApplicationStart.dictionaryManagement.getDictionary().getListOfWords().size()));
        setAnswer(answerWord.getWord_target());
        guessed = "";
        for (int i = 0; i < answer.length(); i++) {
            guessed += "_";
        }
        wordLabel.setText(guessed);

        letterPane.setDisable(false);
        letterPane.setVisible(true);
        resultPane.setDisable(true);
        resultPane.setVisible(false);

        for (int i = 0; i < letterPane.getChildren().size(); i++) {
            letterPane.getChildren().get(i).setStyle("-fx-background-color: transparent; -fx-background-radius: 10px; " +
                    "-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 10px; -fx-border-width: 2px;");
        }

        playButton.setDisable(true);
        playButton.setVisible(false);
    }

    @FXML
    void showInstruction(ActionEvent event) {
        if (instruction.isDisabled()) {
            instruction.setDisable(false);
            instruction.setVisible(true);
            wordLabel.setVisible(false);
        } else {
            instruction.setDisable(true);
            instruction.setVisible(false);
            wordLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundLayer1 = new Image(this.getClass().getResourceAsStream("/images/background_layer1.png"));
        backgroundLayer2 = new Image(this.getClass().getResourceAsStream("/images/background_layer2.png"));
        backgroundLayer3 = new Image(this.getClass().getResourceAsStream("/images/background_layer3.png"));

        backgroundViewSprite1 = new ImageViewSprite(backgroundView1, backgroundLayer1,
                9, 1, 9, 640, 360, 10);
        backgroundViewSprite2 = new ImageViewSprite(backgroundView2, backgroundLayer2,
                9, 1, 9, 640, 360, 10);
        backgroundViewSprite3 = new ImageViewSprite(backgroundView3, backgroundLayer3,
                9, 1, 9, 640, 360, 10);
        backgroundViewSprite1.start();
        backgroundViewSprite2.start();
        backgroundViewSprite3.start();

        shipFullHealth = new Image(this.getClass().getResourceAsStream("/images/ship_full_health.png"));
        shipSlightDamaged = new Image(this.getClass().getResourceAsStream("/images/ship_slight_damaged.png"));
        shipDamaged = new Image(this.getClass().getResourceAsStream("/images/ship_damaged.png"));
        shipVeryDamaged = new Image(this.getClass().getResourceAsStream("/images/ship_very_damaged.png"));

        engineImage = new Image(this.getClass().getResourceAsStream("/images/engine.png"));
        engineViewSprite = new ImageViewSprite(engineView, engineImage,
                4, 1, 4, 48, 48, 10);
        engineViewSprite.start();

        shieldImage = new Image(this.getClass().getResourceAsStream("/images/shield.png"));
        shieldViewSprite = new ImageViewSprite(shieldView, shieldImage,
                12, 1, 12, 64, 64, 50);
        shieldViewSprite.start();

        asteroidImage = new Image(this.getClass().getResourceAsStream("/images/asteroid.png"));

        asteroidView.setImage(asteroidImage);
        asteroidView.setViewport(new Rectangle2D(0, 0, 96, 96));
        asteroidView.setLayoutX(87);
        asteroidView.setLayoutY(-30);

        shipCollision.setImage(asteroidImage);
        shipCollision.setViewport(new Rectangle2D(0, 0, 96, 96));
        shipCollision.setVisible(false);
        shipCollisionAnimation = new SpriteAnimation(shipCollision, Duration.millis(1000),
                8, 8, 0, 0, 96, 96);
        shipCollisionAnimation.setCycleCount(1);

        shieldCollision.setImage(asteroidImage);
        shieldCollision.setViewport(new Rectangle2D(0, 0, 96, 96));
        shieldCollision.setVisible(false);
        shieldCollisionAnimation = new SpriteAnimation(shieldCollision, Duration.millis(1000),
                8, 8, 0, 0, 96, 96);
        shieldCollisionAnimation.setCycleCount(1);

        explosionImage = new Image(this.getClass().getResourceAsStream("/images/explosion.png"));
        explosionView.setImage(explosionImage);
        explosionView.setViewport(new Rectangle2D(0, 0, 96, 96));
        explosionView.setVisible(false);
        explosionAnimation = new SpriteAnimation(explosionView, Duration.millis(2000),
                12, 12, 0, 0, 96, 96);
        explosionAnimation.setCycleCount(1);

        earthImage = new Image(this.getClass().getResourceAsStream("/images/earths.png"));
        earthViewSprite = new ImageViewSprite(earthView, earthImage,
                77, 1, 77, 96, 96, 30);
        earthViewSprite.start();

        isReady = true;
        delay = new PauseTransition(Duration.millis(1200));
        delay.setOnFinished(event -> {
            isReady = true;
        });

        isClicked = new boolean[26];

        wordLabel.setVisible(false);
    }
}
