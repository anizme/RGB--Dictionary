package controller.Alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmationAlert extends DetailAlert {
    public ConfirmationAlert(String contentText, String headerText) {
        super(contentText, headerText);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
    }

    @Override
    public boolean alertAction() {
        Optional<ButtonType> isOK = alert.showAndWait();
        return isOK.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
}
