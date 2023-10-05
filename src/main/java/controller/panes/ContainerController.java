package controller.panes;

import controller.ApplicationStart;
import dictionary.DictionaryManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.VBox;

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

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXButton btGame;

    @FXML
    private JFXButton btSearch;

    @FXML
    private JFXButton btSetting;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane menuDetails;

    @FXML
    private VBox navBar;

    @FXML
    void add(ActionEvent event) {
        menuDetails.setVisible(false);
        resetNavButton();
        showAddPane();
        addController.initData(this);
        btAdd.setStyle("-fx-background-color: #a2d2df;");
    }

    @FXML
    void game(ActionEvent event) {
        menuDetails.setVisible(false);
        resetNavButton();
        showGamePane();
        gameController.initData(this);
        btGame.setStyle("-fx-background-color: #a2d2df;");
    }

    @FXML
    void search(ActionEvent event) {
        menuDetails.setVisible(false);
        resetNavButton();
        showSearchPane();
        searchController.initData(this);
        btSearch.setStyle("-fx-background-color: #a2d2df;");
    }

    @FXML
    void setting(ActionEvent event) {
        menuDetails.setVisible(false);
        resetNavButton();
        showSettingPane();
        settingController.initData(this);
        btSetting.setStyle("-fx-background-color: #a2d2df;");
    }

    @FXML
    void menu(ActionEvent event) {
        menuDetails.setVisible(true);
    }

    private void setContentPane(AnchorPane contentPane) {
        this.contentPane.getChildren().setAll(contentPane);
    }

    private void resetNavButton() {
        btSearch.setStyle("-fx-background-color: #ffffff;");
        btAdd.setStyle("-fx-background-color: #ffffff;");
        btGame.setStyle("-fx-background-color: #ffffff;");
        btSetting.setStyle("-fx-background-color: #ffffff;");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDetails.setVisible(false);
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

        //search(null);
    }
}
