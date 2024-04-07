package control;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.menejemenproduksifx.ManagementProduksi;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TambahKaryawanController implements Initializable {

    @FXML
    private TextField nama_txt;
    @FXML
    private TextField alamat_txt;
    @FXML
    private TextField umur_txt;
    @FXML
    private TextField jabatan_txt;
    @FXML
    private ComboBox<String> jenis_kelamin;






    @FXML
    private void buatrencana (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("RencanaProduksi.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }

    @FXML
    private void tambahbrg_btn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("Tambahbarang.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }

    @FXML
    private void tambahkrwn_btn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("TambahKaryawan.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }

    @FXML
    private void sriwprod_btn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("SeluruhRiwayatProduksi.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }

    @FXML
    private void stokgdg_btn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("StokGudang.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }

    @FXML
    private void slrhkar_btn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("DataSeluruhKaryawan.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }
    public static String generateID() {
        String newdetID = "K0000";

        try (Connection connection = koneksi.koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_karyawan) FROM karyawan1");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_karyawan)");
                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newdetID = String.format("K%04d", sequence);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error parsing ID: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error generating ID: " + e.getMessage());
        }

        return newdetID;
    }
    @FXML
    void simpan_btn(ActionEvent event) {
    String nama = nama_txt.getText();
    String umur = umur_txt.getText();
    String alamat = alamat_txt.getText();
    String jabatan = jabatan_txt.getText();
    String jeniskelamin = jenis_kelamin.getValue();

        try (Connection connection = koneksi.koneksi.getConnection()) {
            connection.setAutoCommit(false);

            String insertProduksiSQL = "INSERT INTO karyawan1 (id_karyawan, nama_karyawan, jenis_kelamin , umur, alamat, jabatan) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement produksiStatement = connection.prepareStatement(insertProduksiSQL);) {
                produksiStatement.setString(1, generateID());
                produksiStatement.setString(2, nama);
                produksiStatement.setString(3, jeniskelamin);
                produksiStatement.setString(4, umur);
                produksiStatement.setString(5, alamat);
                produksiStatement.setString(6, jabatan);


                produksiStatement.executeUpdate();

                connection.commit();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informasi");
                alert.setHeaderText(null);
                alert.setContentText("Data berhasil ditambahkan");
                alert.showAndWait();
            } catch (SQLException e) {
                connection.rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Gagal menambahkan data: " + e.getMessage());
                alert.showAndWait();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void hps_btn(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jenis_kelamin.getItems().addAll("Laki-Laki", "Perempuan");
    }
}
