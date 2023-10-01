module controller.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens controller to javafx.fxml;
    exports controller;
    exports controller.panes;
    opens controller.panes to javafx.fxml;
}