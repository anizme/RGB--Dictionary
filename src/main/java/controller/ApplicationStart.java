package controller;

import dictionary.DictionaryManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.DatabaseConnect;

import java.io.IOException;

public class ApplicationStart extends Application {
    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();
     static {
         try {
             dictionaryManagement.insertFromFile();
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }

    public static DatabaseConnect databaseConnect = new DatabaseConnect();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("container.fxml"));
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/RGB.png")));
        Scene scene = new Scene(fxmlLoader.load(), 930, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}