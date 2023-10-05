package controller.Alert;

import javafx.scene.control.Alert;

public class DetailAlert {
    protected Alert alert = new Alert(Alert.AlertType.NONE);

    public DetailAlert(String contentText, String headerText) {
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
    }

    public void setAlertInfo(String contentText, String headerText) {
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
    }

    public boolean alertAction() {
        alert.showAndWait();
        return true;
    }

    public void setAlertFullInfo(Alert.AlertType alertType, String notification, String s) {
        
    }
}
