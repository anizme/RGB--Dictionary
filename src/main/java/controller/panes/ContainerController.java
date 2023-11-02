package controller.panes;

import com.jfoenix.controls.JFXToggleButton;
import controller.ApplicationStart;
import dictionary.DictionaryManagement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import services.ImageViewSprite;
import services.SpriteAnimation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {

    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private AnchorPane searchPane = null;
    private SearchController searchController;
    private AnchorPane addPane = null;
    private AddController addController;
    private AnchorPane gamePane = null;
    private GameController gameController;
    private AnchorPane settingPane = null;
    private SettingController settingController;
    private AnchorPane translatePane = null;
    private TranslateAPIController translateController;

    @FXML
    private JFXButton btAdd;

    private boolean isAdd;

    @FXML
    private ImageView btAddView;

    private Image addImage;

    private Image addImageDark;

    private ImageViewSprite btAddViewSprite;

    @FXML
    private JFXButton btGame;

    private boolean isGame;

    @FXML
    private ImageView btGameView;

    private Image gameImage;

    private Image gameImageDark;

    private ImageViewSprite btGameViewSprite;

    @FXML
    private JFXButton btSearch;

    private boolean isSearch;

    @FXML
    private ImageView btSearchView;

    private Image searchImage;

    private Image searchImageDark;

    private ImageViewSprite btSearchViewSprite;

    @FXML
    private JFXButton btTranslate;

    private boolean isTranslate;

    @FXML
    private ImageView btTranslateView;

    private Image translateImage;

    private ImageViewSprite btTranslateViewSprite;

    @FXML
    private JFXButton btSetting;

    private boolean isSetting;

    @FXML
    private ImageView btSettingView;

    private Image settingImage;

    private Image settingImageDark;

    private ImageViewSprite btSettingViewSprite;

    @FXML
    private JFXToggleButton switchMode;
    private boolean isLightMode;

    private Image lightBackground;
    private Image darkBackground;
    private SpriteAnimation lightToDarkAnimation, lightToDarkAnimationSetting;
    private SpriteAnimation darkToLightAnimation, darkToLightAnimationSetting;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane menuDetails;

    @FXML
    private VBox navBar;

    @FXML
    void add(ActionEvent event) {
        isAdd = true;
        isGame = false;
        isSearch = false;
        isSetting = false;
        isTranslate = false;
        menuDetails.setVisible(false);
        resetNavButton();
        showAddPane();
        addController.initData(this);
//        if (isLightMode) {
//            btAdd.setStyle("-fx-background-color: whitesmoke;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: black;" +
//                    "-fx-border-radius: 10px;");
//        } else {
//            btAdd.setStyle("-fx-background-color: black;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: white;" +
//                    "-fx-border-radius: 10px;");
//        }
    }

    @FXML
    void game(ActionEvent event) {
        isAdd = false;
        isGame = true;
        isSearch = false;
        isSetting = false;
        isTranslate = false;
        menuDetails.setVisible(false);
        resetNavButton();
        showGamePane();
        gameController.initData(this);
//        if (isLightMode) {
//            btGame.setStyle("-fx-background-color: whitesmoke;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: black;" +
//                    "-fx-border-radius: 10px;");
//        } else {
//            btGame.setStyle("-fx-background-color: black;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: white;" +
//                    "-fx-border-radius: 10px;");
//        }
    }

    @FXML
    void search(ActionEvent event) {
        isAdd = false;
        isGame = false;
        isSearch = true;
        isSetting = false;
        isTranslate = false;
        menuDetails.setVisible(false);
        resetNavButton();
        showSearchPane();
        searchController.initData(this);
//        if (isLightMode) {
//            btSearch.setStyle("-fx-background-color: whitesmoke;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: black;" +
//                    "-fx-border-radius: 10px;");
//        } else {
//            btSearch.setStyle("-fx-background-color: black;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: white;" +
//                    "-fx-border-radius: 10px;");
//        }
    }

    @FXML
    void setting(ActionEvent event) {
        isAdd = false;
        isGame = false;
        isSearch = false;
        isSetting = true;
        isTranslate = false;
        menuDetails.setVisible(false);
        resetNavButton();
        showSettingPane();
        settingController.initData(this);
//        if (isLightMode) {
//            btSetting.setStyle("-fx-background-color: whitesmoke;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: black;" +
//                    "-fx-border-radius: 10px;");
//        } else {
//            btSetting.setStyle("-fx-background-color: black;" +
//                    "-fx-background-radius: 10px;" +
//                    "-fx-border-color: white;" +
//                    "-fx-border-radius: 10px;");
//        }
    }

    @FXML
    void translate(ActionEvent event) {
        isAdd = false;
        isGame = false;
        isSearch = false;
        isSetting = false;
        isTranslate = true;
        menuDetails.setVisible(false);
        resetNavButton();
        showTranslatePane();
        translateController.initData(this);
//        if (isLightMode) {
//            btTranslate.setStyle("-fx-background-color: whitesmoke;" +
//                                 "-fx-background-radius: 10px;" +
//                                 "-fx-border-color: black;" +
//                                 "-fx-border-radius: 10px;");
//        } else {
//            btTranslate.setStyle("-fx-background-color: black;" +
//                                 "-fx-background-radius: 10px;" +
//                                 "-fx-border-color: white;" +
//                                 "-fx-border-radius: 10px;");
//        }


    }

    @FXML
    void menu(ActionEvent event) {
        menuDetails.setVisible(true);
    }

    private void setContentPane(AnchorPane contentPane) {
        this.contentPane.getChildren().setAll(contentPane);
    }

    private void resetNavButton() {
        if (isLightMode) {
//            btSearch.setStyle("-fx-background-color: #ffffff;");
//            btAdd.setStyle("-fx-background-color: #ffffff;");
//            btGame.setStyle("-fx-background-color: #ffffff;");
//            btSetting.setStyle("-fx-background-color: #ffffff;");
            if (isAdd) {
                btAdd.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;");
            } else {
                btAdd.setStyle("-fx-background-color: white");
            }
            if (isGame) {
                btGame.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;");
            } else {
                btGame.setStyle("-fx-background-color: white");
            }
            if (isSearch) {
                btSearch.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;");
            } else {
                btSearch.setStyle("-fx-background-color: white");
            }
            if (isSetting) {
                btSetting.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;");
            } else {
                btSetting.setStyle("-fx-background-color: white");
            }
            if (isTranslate) {
                btTranslate.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;");
            } else {
                btTranslate.setStyle("-fx-background-color: white");
            }
        } else {
//            btSearch.setStyle("-fx-background-color: #2f4f4f;");
//            btAdd.setStyle("-fx-background-color: #2f4f4f;");
//            btGame.setStyle("-fx-background-color: #2f4f4f;");
//            btSetting.setStyle("-fx-background-color: #2f4f4f;");
            if (isAdd) {
                btAdd.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10px;");
            } else {
                btAdd.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isGame) {
                btGame.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10px;");
            } else {
                btGame.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isSearch) {
                btSearch.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10px;");
            } else {
                btSearch.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isSetting) {
                btSetting.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10px;");
            } else {
                btSetting.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isTranslate) {
                btTranslate.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10px;");
            } else {
                btTranslate.setStyle("-fx-background-color: #2f4f4f;");
            }
        }
    }

    public void showSearchPane() {
        this.setContentPane(searchPane);
    }

    public void showAddPane() {
        this.setContentPane(addPane);
    }

    public void showGamePane() {
        this.setContentPane(gamePane);
    }

    public void showSettingPane() {
        this.setContentPane(settingPane);
    }

    public void showTranslatePane() {
        this.setContentPane(translatePane);
    }

    public void lightModeSetting() {

        mainPane.getStylesheets().removeAll(this.getClass().getResource("/controller/container_dark.css").toString());
        mainPane.getStylesheets().add(this.getClass().getResource("/controller/container.css").toString());
        btAddView.setImage(addImage);
        btGameView.setImage(gameImage);
        btSearchView.setImage(searchImage);
        btSettingView.setImage(settingImage);
        translateController.getTranslatePane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/translateAPI_dark.css").toString());
        translateController.getTranslatePane().getStylesheets().
                add(this.getClass().getResource("/controller/translateAPI.css").toString());
        resetNavButton();
        translateController.getBackgroundView().setImage(lightBackground);
        translateController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        settingController.getBackgroundView().setImage(lightBackground);
        settingController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        darkToLightAnimation = new SpriteAnimation(translateController.getBackgroundView(), Duration.millis(1000), 40, 5,
                0, 0, 800, 538);
//        else if (isSetting) darkToLightAnimation = new SpriteAnimation(settingController.getBackgroundView(), Duration.millis(1000), 40, 5,
//                0, 0, 800, 538);
        darkToLightAnimation.setCycleCount(1);
        darkToLightAnimation.play();
        darkToLightAnimationSetting = new SpriteAnimation(settingController.getBackgroundView(), Duration.millis(1000), 40, 5,
                0, 0, 800, 538);
        darkToLightAnimationSetting.setCycleCount(1);
        darkToLightAnimationSetting.play();
    }

    public void darkModeSetting() {

        mainPane.getStylesheets().removeAll(this.getClass().getResource("/controller/container.css").toString());
        mainPane.getStylesheets().add(this.getClass().getResource("/controller/container_dark.css").toString());
        btAddView.setImage(addImageDark);
        btGameView.setImage(gameImageDark);
        btSearchView.setImage(searchImageDark);
        btSettingView.setImage(settingImageDark);
        translateController.getTranslatePane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/translateAPI.css").toString());
        translateController.getTranslatePane().getStylesheets().
                add(this.getClass().getResource("/controller/translateAPI_dark.css").toString());
        translateController.getBackgroundView().setImage(darkBackground);
        translateController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        settingController.getBackgroundView().setImage(darkBackground);
        settingController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
//        if (isTranslate) {
        lightToDarkAnimation = new SpriteAnimation(translateController.getBackgroundView(), Duration.millis(1000), 40, 5,
                    0, 0, 800, 538);
//            settingController.getBackgroundView().setImage(lightBackground);
//            settingController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
//        }
//        else if (isSetting) {
//            lightToDarkAnimation = new SpriteAnimation(settingController.getBackgroundView(), Duration.millis(1000), 40, 5,
//                    0, 0, 800, 538);
//            translateController.getBackgroundView().setImage(lightBackground);
//            translateController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
//        }
        lightToDarkAnimation.setCycleCount(1);
        lightToDarkAnimation.play();
        lightToDarkAnimationSetting = new SpriteAnimation(settingController.getBackgroundView(), Duration.millis(1000), 40, 5,
                0, 0, 800, 538);
        lightToDarkAnimationSetting.setCycleCount(1);
        lightToDarkAnimationSetting.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDetails.setVisible(false);
        isLightMode = true;

        lightBackground = new Image(this.getClass().getResourceAsStream("/images/dark_to_light_animation.png"));
        darkBackground = new Image(this.getClass().getResourceAsStream("/images/light_to_dark_animation.png"));

        addImage = new Image(this.getClass().getResourceAsStream("/images/add_animation.png"));
        gameImage = new Image(this.getClass().getResourceAsStream("/images/game_animation.png"));
        searchImage = new Image(this.getClass().getResourceAsStream("/images/search_animation.png"));
        settingImage = new Image(this.getClass().getResourceAsStream("/images/setting_animation.png"));
        translateImage = new Image(this.getClass().getResourceAsStream("/images/translate_animation1.png"));

        addImageDark = new Image(this.getClass().getResourceAsStream("/images/add_animation_dark.png"));
        gameImageDark = new Image(this.getClass().getResourceAsStream("/images/game_animation_dark.png"));
        searchImageDark = new Image(this.getClass().getResourceAsStream("/images/search_animation_dark.png"));
        settingImageDark = new Image(this.getClass().getResourceAsStream("/images/setting_animation_dark.png"));


        switchMode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isLightMode) {
                    isLightMode = false;
                    darkModeSetting();
                    resetNavButton();
                } else {
                    isLightMode = true;
                    lightModeSetting();
                    resetNavButton();
                }
            }
        });

        btAdd.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btAddViewSprite = new ImageViewSprite(btAddView, isLightMode ? addImage : addImageDark,
                        4, 11, 44, 67, 67, 40);
//                btAddViewSprite = new ImageViewSprite(btAddView, addImage,
//                        4, 11, 44, 603, 603, 40);
                btAddViewSprite.start();
            }
        });
        btAdd.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btAddViewSprite.stop();
                btAddView.setImage(isLightMode ? addImage : addImageDark);
                //btAddView.setImage(addImage);
                btAddView.setViewport(new Rectangle2D(0, 0, 67, 67));
            }
        });

        btGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btGameViewSprite = new ImageViewSprite(btGameView, isLightMode ? gameImage : gameImageDark,
                        6, 19, 114, 173, 173, 63);
//                btGameViewSprite = new ImageViewSprite(btGameView, gameImage,
//                        6, 19, 114, 519, 519, 63);
                btGameViewSprite.start();
            }
        });
        btGame.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btGameViewSprite.stop();
                btGameView.setImage(isLightMode ? gameImage : gameImageDark);
                //btGameView.setImage(gameImage);
                btGameView.setViewport(new Rectangle2D(0, 0, 173, 173));
            }
        });

        btSearch.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btSearchViewSprite = new ImageViewSprite(btSearchView, isLightMode ? searchImage : searchImageDark,
                      5, 13, 65, 66, 66, 60);
//                btSearchViewSprite = new ImageViewSprite(btSearchView, searchImage,
//                        5, 13, 65, 66, 66, 60);
                btSearchViewSprite.start();
            }
        });
        btSearch.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btSearchViewSprite.stop();
                btSearchView.setImage(isLightMode ? searchImage : searchImageDark);
                //btSearchView.setImage(searchImage);
                btSearchView.setViewport(new Rectangle2D(0, 0, 66, 66));
            }
        });

        btSetting.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btSettingViewSprite = new ImageViewSprite(btSettingView, isLightMode ? settingImage : settingImageDark,
                        5, 11, 55, 66, 66, 60);
//                btSettingViewSprite = new ImageViewSprite(btSettingView, settingImage,
//                        5, 11, 55, 528, 528, 60);
                btSettingViewSprite.start();
            }
        });
        btSetting.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btSettingViewSprite.stop();
                btSettingView.setImage(isLightMode ? settingImage : settingImageDark);
                //btSettingView.setImage(settingImage);
                btSettingView.setViewport(new Rectangle2D(0, 0, 66, 66));
            }
        });

        btTranslate.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //imgView.setImage(new Image(this.getClass().getResourceAsStream("/images/add.png")));
                btTranslateViewSprite = new ImageViewSprite(btTranslateView, translateImage,
                        5, 9, 43, 68, 68, 30);
                btTranslateViewSprite.start();
            }
        });
        btTranslate.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btTranslateViewSprite.stop();
                btTranslateView.setImage(translateImage);
                btTranslateView.setViewport(new Rectangle2D(0, 0, 68, 68));
            }
        });

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("search.fxml"));
            searchPane = fxmlLoader.load();
            searchController = fxmlLoader.getController();
            searchController.initData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("add.fxml"));
            addPane = fxmlLoader.load();
            addController = fxmlLoader.getController();
            addController.initData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("game.fxml"));
            gamePane = fxmlLoader.load();
            gameController = fxmlLoader.getController();
            gameController.initData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("setting.fxml"));
            settingPane = fxmlLoader.load();
            settingController = fxmlLoader.getController();
            settingController.initData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("translateAPI.fxml"));
            translatePane = fxmlLoader.load();
            translateController = fxmlLoader.getController();
            translateController.initData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //search(null);
    }
}
