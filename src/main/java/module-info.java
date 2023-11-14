module controller.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.jfoenix;
    requires voicerss.tts;
    requires java.desktop;
    requires org.json;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens controller to javafx.fxml;
    exports controller;
    exports controller.panes;
    opens controller.panes to javafx.fxml;
}