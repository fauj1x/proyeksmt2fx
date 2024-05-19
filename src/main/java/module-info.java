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
    requires java.sql;
    requires java.desktop;
    requires barbecue;
    requires javafx.swing;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens main.menejemenproduksifx to javafx.fxml;
    exports main.menejemenproduksifx;
    exports control;
    opens control to javafx.fxml;

    // Menambahkan deklarasi opens untuk paket datatable
    opens datatable;
}
