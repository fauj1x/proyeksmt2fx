package main.menejemenproduksifx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagementProduksi extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("MenejemenProduksiFX/src/main/resources/font/Montserrat-VariableFont_wght.ttf"), 12);
        FXMLLoader fxmlLoader = new FXMLLoader(ManagementProduksi.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Aplikasi Manajemen Produksi");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}