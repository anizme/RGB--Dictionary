package controller.panes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import controller.ApplicationStart;
import dictionary.DictionaryManagement;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import services.DatabaseConnect;
import services.ImageViewSprite;
import services.SpriteAnimation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {

    static boolean isLightMode;

    //private DictionaryManagement dictionaryManagement = new DictionaryManagement();
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
    private AnchorPane favoritePane = null;
    private FavoriteController favoriteController;
    @FXML
    private JFXButton btExit;

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
    private JFXButton btFavorite;
    private boolean isFavorite;
    @FXML
    private ImageView btFavoriteView;
    private Image favoriteImage;
    private Image favoriteImageDark;
    private ImageViewSprite btFavoriteViewSprite;
    private JFXToggleButton switchMode;
    private Image lightBackground;
    private Image darkBackground;
    private SpriteAnimation lightToDarkAnimation;
    private SpriteAnimation darkToLightAnimation;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private VBox navBar;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label menuLabel;

    private Timeline timeline;
    private KeyValue labelPosition;
    private KeyValue labelSize;
    private KeyValue labelText;
    private KeyFrame keyFrame;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handlePopUpButton() {
        stage.setIconified(false);
    }

    @FXML
    private void handleMinimizeButton() {
        stage.setIconified(true);
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void exit(ActionEvent event) throws SQLException {
        DatabaseConnect.close();
        stage.close();
        Platform.exit();
    }

    @FXML
    void add(ActionEvent event) {
        isAdd = true;
        isFavorite = false;
        isGame = false;
        isSearch = false;
        isSetting = false;
        isTranslate = false;
        resetNavButton();
        showAddPane();
        addController.initData(this);
    }

    @FXML
    void game(ActionEvent event) {
        isAdd = false;
        isFavorite = false;
        isGame = true;
        isSearch = false;
        isSetting = false;
        isTranslate = false;
        resetNavButton();
        showGamePane();
        gameController.initData(this);
    }

    @FXML
    void search(ActionEvent event) throws SQLException {
        isAdd = false;
        isFavorite = false;
        isGame = false;
        isSearch = true;
        isSetting = false;
        isTranslate = false;
        resetNavButton();
        showSearchPane();
        searchController.initData(this);
        searchController.updateHistoryInListView();
    }

    public SearchController getSearchController() {
        return searchController;
    }

    @FXML
    void setting(ActionEvent event) {
        isAdd = false;
        isFavorite = false;
        isGame = false;
        isSearch = false;
        isSetting = true;
        isTranslate = false;
        resetNavButton();
        showSettingPane();
        settingController.initData(this);
    }

    @FXML
    void translate(ActionEvent event) {
        isAdd = false;
        isFavorite = false;
        isGame = false;
        isSearch = false;
        isSetting = false;
        isTranslate = true;
        resetNavButton();
        showTranslatePane();
        translateController.initData(this);
    }

    @FXML
    void favorite(ActionEvent event) throws Exception {
        isAdd = false;
        isFavorite = true;
        isGame = false;
        isSearch = false;
        isSetting = false;
        isTranslate = false;
        resetNavButton();
        showFavoritePane();
        favoriteController.updateListView(new ActionEvent());
        favoriteController.initData(this);
    }
    @FXML
    void menu(ActionEvent event) {
        menuPane.setVisible(true);
        setContentPane(menuPane);
    }

    private void setContentPane(AnchorPane contentPane) {
        this.contentPane.getChildren().setAll(contentPane);
    }

    private void resetNavButton() {
        if (isLightMode) {
            if (isAdd) {
                btAdd.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btAdd.setStyle("-fx-background-color: white");
            }
            if (isFavorite) {
                btFavorite.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btFavorite.setStyle("-fx-background-color: white");
            }
            if (isGame) {
                btGame.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btGame.setStyle("-fx-background-color: white");
            }
            if (isSearch) {
                btSearch.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btSearch.setStyle("-fx-background-color: white");
            }
            if (isSetting) {
                btSetting.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btSetting.setStyle("-fx-background-color: white");
            }
            if (isTranslate) {
                btTranslate.setStyle("-fx-background-color: whitesmoke;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btTranslate.setStyle("-fx-background-color: white");
            }
        } else {
            if (isAdd) {
                btAdd.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #30cccc;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btAdd.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isFavorite) {
                btFavorite.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #30cccc;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btFavorite.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isGame) {
                btGame.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #30cccc;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btGame.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isSearch) {
                btSearch.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #30cccc;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
            } else {
                btSearch.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isSetting) {
                btSetting.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #30cccc;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-border-width: 2;");
            } else {
                btSetting.setStyle("-fx-background-color: #2f4f4f;");
            }
            if (isTranslate) {
                btTranslate.setStyle("-fx-background-color: black;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #30cccc;" +
                        "-fx-border-radius: 10px;"+
                        "-fx-border-width: 2;");
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

    public void showFavoritePane() {
        this.setContentPane(favoritePane);
    }

    public void lightModeSetting() {

        mainPane.getStylesheets().removeAll(this.getClass().getResource("/controller/container_dark.css").toString());
        mainPane.getStylesheets().add(this.getClass().getResource("/controller/container.css").toString());

        btAddView.setImage(addImage);
        btFavoriteView.setImage(favoriteImage);
        btGameView.setImage(gameImage);
        btSearchView.setImage(searchImage);
        btSettingView.setImage(settingImage);

        translateController.getTranslatePane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/translateAPI_dark.css").toString());
        translateController.getTranslatePane().getStylesheets().
                add(this.getClass().getResource("/controller/translateAPI.css").toString());
        translateController.getBackgroundView().setImage(darkBackground);
        translateController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));

        addController.getAddPane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/add_dark.css").toString());
        addController.getAddPane().getStylesheets().
                add(this.getClass().getResource("/controller/add.css").toString());
        addController.getBackgroundView().setImage(darkBackground);
        addController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        try {
            addController.getHtmlEditor().setHtmlText("<body style='background-color: #def3f6; color: black'/>"
                    + DatabaseConnect.getMeaning(addController.getTfAddWord().getText()));
            if (addController.isAddWord()) {
                addController.getHtmlEditor().setHtmlText("<body style='background-color: #def3f6; color: black'/>"
                        + String.format(addController.getDefaultText(), addController.getTfAddWord().getText()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        addController.getListView().setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                        if (getIndex() % 2 == 1)
                            setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        else
                            setStyle("-fx-background-color: #def3f6; -fx-text-fill: black");
                    }
                };
            }
        });


//        gameController.getBackgroundImage().setImage(darkBackground);
//        gameController.getBackgroundImage().setViewport(new Rectangle2D(0, 0, 800, 538));

        searchController.getSearchPane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/search_dark.css").toString());
        searchController.getSearchPane().getStylesheets().
                add(this.getClass().getResource("/controller/search.css").toString());
        searchController.getBackgroundView().setImage(darkBackground);
        searchController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        try {
            if (searchController.isUpdate()) {
                searchController.getHtmlEditor().setHtmlText("<body style='background-color: #def3f6; color: black'/>"
                        + DatabaseConnect.getMeaning(searchController.getTfSearchWord().getText()));
            }
            searchController.getWebView().getEngine().loadContent("<body style='background-color: #def3f6; color: black'/>"
                    + DatabaseConnect.getMeaning(searchController.getTfSearchWord().getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchController.getListView().setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                        if (getIndex() % 2 == 1)
                            setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        else
                            setStyle("-fx-background-color: #def3f6; -fx-text-fill: black");
                    }
                };
            }
        });

        favoriteController.getFavoritePane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/favorite_dark.css").toString());
        favoriteController.getFavoritePane().getStylesheets().
                add(this.getClass().getResource("/controller/favorite.css").toString());
        favoriteController.getBackgroundView().setImage(lightBackground);
        favoriteController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        favoriteController.getListView().setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                        if (getIndex() % 2 == 1)
                            setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        else
                            setStyle("-fx-background-color: #def3f6; -fx-text-fill: black");
                    }
                };
            }
        });

        settingController.getSettingPane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/setting_dark.css").toString());
        settingController.getSettingPane().getStylesheets().
                add(this.getClass().getResource("/controller/setting.css").toString());
        settingController.getBackgroundView().setImage(lightBackground);
        settingController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));

        darkToLightAnimation = new SpriteAnimation(settingController.getBackgroundView(), Duration.millis(1000), 40, 5,
                0, 0, 800, 538);
        darkToLightAnimation.setCycleCount(1);
        darkToLightAnimation.play();
    }

    public void darkModeSetting() {

        mainPane.getStylesheets().removeAll(this.getClass().getResource("/controller/container.css").toString());
        mainPane.getStylesheets().add(this.getClass().getResource("/controller/container_dark.css").toString());

        btAddView.setImage(addImageDark);
        btFavoriteView.setImage(favoriteImageDark);
        btGameView.setImage(gameImageDark);
        btSearchView.setImage(searchImageDark);
        btSettingView.setImage(settingImageDark);

        translateController.getTranslatePane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/translateAPI.css").toString());
        translateController.getTranslatePane().getStylesheets().
                add(this.getClass().getResource("/controller/translateAPI_dark.css").toString());
        translateController.getBackgroundView().setImage(lightBackground);
        translateController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));

        addController.getAddPane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/add.css").toString());
        addController.getAddPane().getStylesheets().
                add(this.getClass().getResource("/controller/add_dark.css").toString());
        addController.getBackgroundView().setImage(lightBackground);
        addController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        try {
            addController.getHtmlEditor().setHtmlText("<body style='background-color: #2f4f4f; color: white'/>"
                    + DatabaseConnect.getMeaning(addController.getTfAddWord().getText()));
            if (addController.isAddWord()) {
                addController.getHtmlEditor().setHtmlText("<body style='background-color: #2f4f4f; color: white'/>"
                        + String.format(addController.getDefaultText(), addController.getTfAddWord().getText()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        addController.getListView().setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                        if (getIndex() % 2 == 1)
                            setStyle("-fx-background-color: #2f4f3f; -fx-text-fill: white;");
                        else
                            setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white");
                    }
                };
            }
        });

//        gameController.getBackgroundImage().setImage(lightBackground);
//        gameController.getBackgroundImage().setViewport(new Rectangle2D(0, 0, 800, 538));

        searchController.getSearchPane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/search.css").toString());
        searchController.getSearchPane().getStylesheets().
                add(this.getClass().getResource("/controller/search_dark.css").toString());
        searchController.getBackgroundView().setImage(lightBackground);
        searchController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        try {
            if (searchController.isUpdate()) {
                searchController.getHtmlEditor().setHtmlText("<body style='background-color: #2f4f4f; color: white'/>"
                        + DatabaseConnect.getMeaning(searchController.getTfSearchWord().getText()));
            }
            searchController.getWebView().getEngine().loadContent("<body style='background-color: #2f4f4f; color: white; border-color: #30cccc'/>"
                    + DatabaseConnect.getMeaning(searchController.getTfSearchWord().getText()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchController.getListView().setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            if (getIndex() % 2 == 1) {
                                setStyle("-fx-background-color: #2f4f3f; -fx-text-fill: white;");
                            } else {
                                setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white");
                            }
                        } else {
                            setText(item);
                            if (getIndex() % 2 == 1) {
                                setStyle("-fx-background-color: #2f4f3f; -fx-text-fill: white;");
                            } else {
                                setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white");
                            }
                        }

                    }
                };
            }
        });

        favoriteController.getFavoritePane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/favorite.css").toString());
        favoriteController.getFavoritePane().getStylesheets().
                add(this.getClass().getResource("/controller/favorite_dark.css").toString());
        favoriteController.getBackgroundView().setImage(lightBackground);
        favoriteController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));
        favoriteController.getListView().setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            if (getIndex() % 2 == 1) {
                                setStyle("-fx-background-color: #2f4f3f; -fx-text-fill: white;");
                            } else {
                                setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white");
                            }
                        } else {
                            setText(item);
                            if (getIndex() % 2 == 1) {
                                setStyle("-fx-background-color: #2f4f3f; -fx-text-fill: white;");
                            } else {
                                setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white");
                            }
                        }

                    }
                };
            }
        });

        settingController.getSettingPane().getStylesheets().
                removeAll(this.getClass().getResource("/controller/setting.css").toString());
        settingController.getSettingPane().getStylesheets().
                add(this.getClass().getResource("/controller/setting_dark.css").toString());
        settingController.getBackgroundView().setImage(darkBackground);
        settingController.getBackgroundView().setViewport(new Rectangle2D(0, 0, 800, 538));

        lightToDarkAnimation = new SpriteAnimation(settingController.getBackgroundView(), Duration.millis(1000), 40, 5,
                0, 0, 800, 538);
        lightToDarkAnimation.setCycleCount(1);
        lightToDarkAnimation.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navBar.setOnMouseExited(mouseEvent -> {
            menuLabel.setDisable(true);
            menuLabel.setVisible(false);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 558);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 0);
            labelText = new KeyValue(menuLabel.textProperty(), "");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        menuPane.setVisible(false);
//        TranslateTransition transition = new TranslateTransition(Duration.millis(15000), welcomeLabel);
//        transition.setByX(-1400);
//        transition.setCycleCount(Animation.INDEFINITE);
//        transition.play();

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

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("favorite.fxml"));
            favoritePane = fxmlLoader.load();
            favoriteController = fxmlLoader.getController();
            translateController.initData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        switchMode = settingController.getSwitchMode();
        isLightMode = true;

        lightBackground = new Image(this.getClass().getResourceAsStream("/images/dark_to_light_animation.png"));
        darkBackground = new Image(this.getClass().getResourceAsStream("/images/light_to_dark_animation.png"));

        addImage = new Image(this.getClass().getResourceAsStream("/images/add_animation.png"));
        favoriteImage = new Image(this.getClass().getResourceAsStream("/images/favorite_animation.png"));
        gameImage = new Image(this.getClass().getResourceAsStream("/images/game_animation.png"));
        searchImage = new Image(this.getClass().getResourceAsStream("/images/search_animation.png"));
        settingImage = new Image(this.getClass().getResourceAsStream("/images/setting_animation.png"));
        translateImage = new Image(this.getClass().getResourceAsStream("/images/translate_animation.png"));

        addImageDark = new Image(this.getClass().getResourceAsStream("/images/add_animation_dark.png"));
        favoriteImageDark = new Image(this.getClass().getResourceAsStream("/images/favorite_animation_dark.png"));
        gameImageDark = new Image(this.getClass().getResourceAsStream("/images/game_animation_dark.png"));
        searchImageDark = new Image(this.getClass().getResourceAsStream("/images/search_animation_dark.png"));
        settingImageDark = new Image(this.getClass().getResourceAsStream("/images/setting_animation_dark.png"));

        switchMode.setOnMouseClicked(mouseEvent -> {
            if (isLightMode) {
                isLightMode = false;
                darkModeSetting();
                resetNavButton();
            } else {
                isLightMode = true;
                lightModeSetting();
                resetNavButton();
            }
        });

        btAdd.setOnMouseEntered(mouseEvent -> {
            btAddViewSprite = new ImageViewSprite(btAddView, isLightMode ? addImage : addImageDark,
                    4, 11, 44, 67, 67, 40);
            btAddViewSprite.start();
            menuLabel.setDisable(false);
            menuLabel.setVisible(true);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 158);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 50);
            labelText = new KeyValue(menuLabel.textProperty(), "ADD");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        btAdd.setOnMouseExited(mouseEvent -> {
            btAddViewSprite.stop();
            btAddView.setImage(isLightMode ? addImage : addImageDark);
            btAddView.setViewport(new Rectangle2D(0, 0, 67, 67));
        });

        btFavorite.setOnMouseEntered(mouseEvent -> {
            btFavoriteViewSprite = new ImageViewSprite(btFavoriteView, isLightMode ? favoriteImage : favoriteImageDark,
                    5, 10, 47, 75, 75, 60);
            btFavoriteViewSprite.start();
            menuLabel.setDisable(false);
            menuLabel.setVisible(true);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 398);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 100);
            labelText = new KeyValue(menuLabel.textProperty(), "FAVORITE");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        btFavorite.setOnMouseExited(mouseEvent -> {
            btFavoriteViewSprite.stop();
            btFavoriteView.setImage(isLightMode ? favoriteImage : favoriteImageDark);
            btFavoriteView.setViewport(new Rectangle2D(0, 0, 75, 75));
        });

        btGame.setOnMouseEntered(mouseEvent -> {
            btGameViewSprite = new ImageViewSprite(btGameView, isLightMode ? gameImage : gameImageDark,
                    6, 19, 114, 64, 64, 63);
            btGameViewSprite.start();
            menuLabel.setDisable(false);
            menuLabel.setVisible(true);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 238);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 70);
            labelText = new KeyValue(menuLabel.textProperty(), "GAME");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        btGame.setOnMouseExited(mouseEvent -> {
            btGameViewSprite.stop();
            btGameView.setImage(isLightMode ? gameImage : gameImageDark);
            btGameView.setViewport(new Rectangle2D(0, 0, 64, 64));
        });

        btSearch.setOnMouseEntered(mouseEvent -> {
            btSearchViewSprite = new ImageViewSprite(btSearchView, isLightMode ? searchImage : searchImageDark,
                    5, 13, 65, 66, 66, 60);
            btSearchViewSprite.start();
            menuLabel.setDisable(false);
            menuLabel.setVisible(true);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 78);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 80);
            labelText = new KeyValue(menuLabel.textProperty(), "SEARCH");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        btSearch.setOnMouseExited(mouseEvent -> {
            btSearchViewSprite.stop();
            btSearchView.setImage(isLightMode ? searchImage : searchImageDark);
            btSearchView.setViewport(new Rectangle2D(0, 0, 66, 66));
        });

        btSetting.setOnMouseEntered(mouseEvent -> {
            btSettingViewSprite = new ImageViewSprite(btSettingView, isLightMode ? settingImage : settingImageDark,
                    5, 11, 55, 66, 66, 60);
            btSettingViewSprite.start();
            menuLabel.setDisable(false);
            menuLabel.setVisible(true);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 478);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 90);
            labelText = new KeyValue(menuLabel.textProperty(), "SETTING");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        btSetting.setOnMouseExited(mouseEvent -> {
            btSettingViewSprite.stop();
            btSettingView.setImage(isLightMode ? settingImage : settingImageDark);
            btSettingView.setViewport(new Rectangle2D(0, 0, 66, 66));
        });

        btTranslate.setOnMouseEntered(mouseEvent -> {
            btTranslateViewSprite = new ImageViewSprite(btTranslateView, translateImage,
                    5, 9, 43, 68, 68, 30);
            btTranslateViewSprite.start();
            menuLabel.setDisable(false);
            menuLabel.setVisible(true);
            labelPosition = new KeyValue(menuLabel.layoutYProperty(), 318);
            labelSize = new KeyValue(menuLabel.prefWidthProperty(), 120);
            labelText = new KeyValue(menuLabel.textProperty(), "TRANSLATE");
            keyFrame = new KeyFrame(Duration.millis(300), labelPosition, labelSize, labelText);
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        });
        btTranslate.setOnMouseExited(mouseEvent -> {
            btTranslateViewSprite.stop();
            btTranslateView.setImage(translateImage);
            btTranslateView.setViewport(new Rectangle2D(0, 0, 68, 68));
        });

        //search(null);
    }
}
