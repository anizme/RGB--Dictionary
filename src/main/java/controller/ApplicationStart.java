package controller;

import dictionary.DictionaryManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("container.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 930, 600);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}