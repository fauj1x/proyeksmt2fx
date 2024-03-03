package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.menejemenproduksifx.ManagementProduksi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private void buatrencana (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RencanaProduksi.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

