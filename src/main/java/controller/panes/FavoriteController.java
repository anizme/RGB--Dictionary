package controller.panes;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.DatabaseConnect;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class FavoriteController extends ActionController implements Initializable{

    @FXML
    private ListView<String> favoriteList;
    @FXML
    private TextField favoriteTextField;

    public FavoriteController() {
        if (favoriteList != null) {
            favoriteList.getItems().addAll(SearchController.favorite.keySet());
        }
    }

    public void updateListView(ActionEvent event) throws Exception {
        Set<String> favoriteKey = SearchController.favorite.keySet();
        for (String x : favoriteKey) {
            String s = SearchController.favorite.get(x);
            s += "\n" + "/" + DatabaseConnect.getShortPro(s) + "/" + "\n" + "- " + DatabaseConnect.getShortMeaning(s);
            favoriteList.getItems().add(s);
        }
    }

    public void searchFavorite(ActionEvent event) throws Exception {
        String tmp = favoriteTextField.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
