module controller.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires voicerss.tts;
    requires java.desktop;
    requires org.json;

    opens controller to javafx.fxml;
    exports controller;
    exports controller.panes;
    opens controller.panes to javafx.fxml;
}