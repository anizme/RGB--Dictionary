package controller;

import controller.panes.ContainerController;
import dictionary.DictionaryManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.DatabaseConnect;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationStart extends Application {
//    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();
//     static {
//         try {
//             dictionaryManagement.insertFromFile();
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//     }

     static {
         if (DatabaseConnect.connection == null) {
             try {
                 DatabaseConnect.tryConnect();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
     }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("container.fxml"));
        //stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/RGB.png")));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(fxmlLoader.load(), 930, 600);
        ContainerController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}