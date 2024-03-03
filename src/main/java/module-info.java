module main.menejemenproduksifx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens main.menejemenproduksifx to javafx.fxml;
    exports main.menejemenproduksifx;
    exports control;
    opens control to javafx.fxml;
}