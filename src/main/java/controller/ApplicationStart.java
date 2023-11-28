package controller;

import controller.panes.ContainerController;
import dictionary.DictionaryManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.database.DictionaryDB;
import services.database.FavoriteDB;
import services.database.HistoryDB;

import java.io.IOException;
import java.util.Objects;

public class ApplicationStart extends Application {
    public static DictionaryDB dictionaryDB = new DictionaryDB();
    public static HistoryDB historyDB = new HistoryDB();
    public static FavoriteDB favoriteDB = new FavoriteDB();
    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();

    static {
        try {
            dictionaryManagement.insertFromFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("container.fxml"));
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/RGB.png"))));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(fxmlLoader.load(), 930, 600);
        ContainerController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
}