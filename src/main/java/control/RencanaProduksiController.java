package control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RencanaProduksiController implements Initializable {

    @FXML
    private TextField anggarantxt;

    @FXML
    private TextField jumlah_karyawan;

    @FXML
    private TextField nama_prod;


    @FXML
    private TextField targetProduksitxt;

    @FXML
    private DatePicker tanggalmulai;

    @FXML
    private DatePicker tanggalselesai;



    @FXML
    private ComboBox<String> jenis_barang;

    @FXML
    private ComboBox<String> penanggung_jawab;

    private ObservableList<String> namaKaryawanList = FXCollections.observableArrayList();
    private ObservableList<String> idKaryawanList = FXCollections.observableArrayList();

    private ObservableList<String> namabarangList = FXCollections.observableArrayList();
    private ObservableList<String> idbarangList = FXCollections.observableArrayList();

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
    private void riwayat_transaksi (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("RiwayatTransaksi.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
    }
    @FXML
    private void dashboard_btn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
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
    private void loaderidkaryawan() {
        try (Connection connection = koneksi.koneksi.getConnection()) {
            String query = "SELECT id_karyawan, nama_karyawan FROM karyawan1 where jabatan = 'boss'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idKaryawan = resultSet.getString("id_karyawan");
                String namaKaryawan = resultSet.getString("nama_karyawan");


                idKaryawanList.add(idKaryawan);
                namaKaryawanList.add(namaKaryawan);
            }

            penanggung_jawab.setItems(namaKaryawanList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onComboBoxItemSelected() {
        String selectedNamaKaryawan = penanggung_jawab.getSelectionModel().getSelectedItem();
        int selectedIndex = namaKaryawanList.indexOf(selectedNamaKaryawan);
        String selectedIdKaryawan = idKaryawanList.get(selectedIndex);

    }



    @FXML
    private void loaderidbarang() {
        try (Connection connection = koneksi.koneksi.getConnection()) {
            String query = "SELECT id_barang, nama_barang FROM barang WHERE kategori = 'Jadi'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idbarang = resultSet.getString("id_barang");
                String namabarang = resultSet.getString("nama_barang");

                idbarangList.add(idbarang);
                namabarangList.add(namabarang);
            }

            jenis_barang.setItems(namabarangList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML 
    private void onComboBoxItemSelected2() {
        String selectedbarang = jenis_barang.getSelectionModel().getSelectedItem().toString();
        int selectedIndex = namabarangList.indexOf(selectedbarang);
        String selectedIdbarang = idbarangList.get(selectedIndex);
    }

    public static String generateIDt() {
        String newdetID = "T0000";

        try (Connection connection = koneksi.koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_transaksi) FROM transaksi");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_transaksi)");
                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newdetID = String.format("T%04d", sequence);
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
        String penanggung = penanggung_jawab.getSelectionModel().getSelectedItem().toString();
        String selectedbarang = jenis_barang.getSelectionModel().getSelectedItem().toString();
        int selectedIndex = namabarangList.indexOf(selectedbarang);
        String selectedIdbarang = idbarangList.get(selectedIndex);
        String selectedNamaKaryawan = penanggung_jawab.getSelectionModel().getSelectedItem();
        int selectedIndex1 = namaKaryawanList.indexOf(selectedNamaKaryawan);
        String selectedIdKaryawan = idKaryawanList.get(selectedIndex1);
        String penanggung1 = selectedIdKaryawan;
        String mulai = tanggalmulai.getValue() != null ? tanggalmulai.getValue().toString() : "";
        String selesai = tanggalselesai.getValue() != null ? tanggalselesai.getValue().toString() : "";
        String jlmhkaryawan = jumlah_karyawan.getText();
        String anggaran = anggarantxt.getText();
        String target = targetProduksitxt.getText();
        String id_barang = selectedIdbarang;



        try (Connection connection = koneksi.koneksi.getConnection()) {
            String insertProduksiSQL = "INSERT INTO produksi (id_produksi, nama_produksi, tgl_mul, tgl_sel, penanggung_jawab,status) VALUES (?, ?, ?, ?, ?, ?)";
            String insertDetailProduksiSQL = "INSERT INTO detail_produksi (id_detail_produksi, jumlah_karyawan, id_produksi, target_produksi, anggaran, id_karyawan, id_barang) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String updateJumlahBarangSQL = "insert into transaksi (id_transaksi, tanggal_transaksi, tipe_transaksi, total_transaksi) values (?,?,?,?)";

            try (PreparedStatement produksiStatement = connection.prepareStatement(insertProduksiSQL);
                 PreparedStatement detailProduksiStatement = connection.prepareStatement(insertDetailProduksiSQL);
                 PreparedStatement updateJumlahBarangStatement = connection.prepareStatement(updateJumlahBarangSQL)) {

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
                detailProduksiStatement.setString(6, penanggung1);
                detailProduksiStatement.setString(7, id_barang);


                updateJumlahBarangStatement.setString(1, generateIDt());
                updateJumlahBarangStatement.setString(2, mulai);
                updateJumlahBarangStatement.setString(3, "Produksi");
                updateJumlahBarangStatement.setString(4, anggaran);

                connection.setAutoCommit(false);

                produksiStatement.executeUpdate();
                detailProduksiStatement.executeUpdate();
                updateJumlahBarangStatement.executeUpdate();

                connection.commit();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informasi");
                alert.setHeaderText(null);
                alert.setContentText("Data berhasil ditambahkan");
                alert.showAndWait();

                // Mengosongkan semua TextField
                anggarantxt.clear();
                jumlah_karyawan.clear();
                nama_prod.clear();
                targetProduksitxt.clear();

                // Mengosongkan semua DatePicker
                tanggalmulai.setValue(null);
                tanggalselesai.setValue(null);

                // Mengosongkan semua ComboBox
                jenis_barang.getSelectionModel().clearSelection();
                penanggung_jawab.getSelectionModel().clearSelection();
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

    private void updateProductionStatus() {
        // Ambil tanggal saat ini
        LocalDate currentDate = LocalDate.now();

        // Koneksi ke database
        try (Connection connection = koneksi.koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produksi SET status = 'Selesai' WHERE tgl_sel < ? AND status <> 'Selesai'");
        ) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(currentDate));
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Jumlah baris yang diperbarui: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hpsbtn(ActionEvent event) {
        // Mengosongkan semua TextField
        anggarantxt.clear();
        jumlah_karyawan.clear();
        nama_prod.clear();
        targetProduksitxt.clear();

        // Mengosongkan semua DatePicker
        tanggalmulai.setValue(null);
        tanggalselesai.setValue(null);

        // Mengosongkan semua ComboBox
        jenis_barang.getSelectionModel().clearSelection();
        penanggung_jawab.getSelectionModel().clearSelection();
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
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("StokGudang.fxml"));
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
    public void initialize(URL location, ResourceBundle resources){
        loaderidkaryawan();
        loaderidbarang();
        updateProductionStatus();


    }

}
