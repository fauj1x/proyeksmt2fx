package control;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.menejemenproduksifx.ManagementProduksi;

import javax.swing.*;
import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class RencanaProduksiController {

    @FXML
    private TextField anggarantxt;

    @FXML
    private TextField jumlah_karyawan;

    @FXML
    private TextField nama_prod;

    @FXML
    private TextField penanggung_jawab;

    @FXML
    private TextField targetProduksitxt;

    @FXML
    private DatePicker tanggalmulai;

    @FXML
    private DatePicker tanggalselesai;


    @FXML
    private void buatrencana (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("RencanaProduksi.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }
    public class Billing {

        // Method untuk mengupdate status produksi berdasarkan tanggal mulai dan selesai
        public void billing() {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            try {
                connection = koneksi.koneksi.getConnection();

                String query = "SELECT id_produksi, tgl_mul, tgl_sel, status FROM produksi WHERE status = 'Berjalan'";
                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String idProduksi = resultSet.getString("id_produksi");
                    LocalDate tanggalMulai = resultSet.getDate("tgl_mul").toLocalDate();
                    LocalDate tanggalSelesai = resultSet.getDate("tgl_sel").toLocalDate();
                    String status = resultSet.getString("status");

                    if (status.equals("Berjalan")) {
                        if (LocalDate.now().isAfter(tanggalSelesai)) {
                            String updateQuery = "UPDATE produksi SET status = 'Selesai' WHERE id_produksi = ?";
                            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                            updateStatement.setString(1, idProduksi);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResources(connection, statement, resultSet);
            }
        }

        // Method untuk menutup koneksi dan resource terkait
        private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static String generateNewID() {
        String newID = "PD0000";

        try (Connection connection = koneksi.koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_produksi) FROM produksi");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_produksi)");
                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newID = String.format("PD%04d", sequence);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error parsing ID: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error generating ID: " + e.getMessage());
        }

        return newID;
    }

    public static String generatedetID() {
        String newdetID = "DP0000";

        try (Connection connection = koneksi.koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_produksi) FROM produksi");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_produksi)");
                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newdetID = String.format("DP%04d", sequence);
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
        String nama = nama_prod.getText();
        String penanggung = penanggung_jawab.getText();
        String mulai = tanggalmulai.getValue() != null ? tanggalmulai.getValue().toString() : "";
        String selesai = tanggalselesai.getValue() != null ? tanggalselesai.getValue().toString() : "";
        String jlmhkaryawan = jumlah_karyawan.getText();
        String anggaran = anggarantxt.getText();
        String target = targetProduksitxt.getText();
        new Billing().billing();

        try (Connection connection = koneksi.koneksi.getConnection()) {
            String insertProduksiSQL = "INSERT INTO produksi (id_produksi, nama_produksi, tgl_mul, tgl_sel, penanggung_jawab,status) VALUES (?, ?, ?, ?, ?,?)";
            String insertDetailProduksiSQL = "INSERT INTO detail_produksi (id_detail_produksi, jumlah_karyawan, id_produksi, target_produksi, anggaran) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement produksiStatement = connection.prepareStatement(insertProduksiSQL);
                 PreparedStatement detailProduksiStatement = connection.prepareStatement(insertDetailProduksiSQL)) {

                String produksiID = generateNewID();
                String detailProduksiID = generatedetID();

                produksiStatement.setString(1, produksiID);
                produksiStatement.setString(2, nama);
                produksiStatement.setString(3, mulai);
                produksiStatement.setString(4, selesai);
                produksiStatement.setString(5, penanggung);
                produksiStatement.setString(6, "Berjalan");

                detailProduksiStatement.setString(1, detailProduksiID);
                detailProduksiStatement.setString(2, jlmhkaryawan);
                detailProduksiStatement.setString(3, produksiID);
                detailProduksiStatement.setString(4, target);
                detailProduksiStatement.setString(5, anggaran);

                connection.setAutoCommit(false);

                produksiStatement.executeUpdate();
                detailProduksiStatement.executeUpdate();

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void hpsbtn(ActionEvent event) {

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
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("SeluruhRiwayatProduksi.fxml"));
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

}
