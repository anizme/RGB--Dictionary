module controller.dictionaryrgb {
    requires javafx.controls;
    requires javafx.fxml;


    opens controller.dictionary to javafx.fxml;
    exports controller.dictionary;
}