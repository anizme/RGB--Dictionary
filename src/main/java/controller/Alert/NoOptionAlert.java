package controller.Alert;

import javafx.scene.control.Alert;

public class NoOptionAlert extends DetailAlert {
    public NoOptionAlert(String contentText, String headerText) {
        super(contentText, headerText);
    }

    public NoOptionAlert(Alert.AlertType alertType, String contentText, String headerText) {
        super(contentText, headerText);
        alert.setAlertType(alertType);
    }

    @Override
    public void setAlertFullInfo(Alert.AlertType alertType, String contentText, String headerText) {
        alert.setAlertType(alertType);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
    }

    @Override
    public boolean alertAction() {
        alert.showAndWait();
        return true;
    }
}
