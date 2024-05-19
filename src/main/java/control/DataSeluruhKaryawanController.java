package control;


import datatable.data_karyawan;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import main.menejemenproduksifx.ManagementProduksi;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataSeluruhKaryawanController implements Initializable {

    @FXML
    private TableColumn<data_karyawan, String> alamat;

    @FXML
    private TableColumn<data_karyawan, String> umur;


    @FXML
    private TableColumn<data_karyawan, String> jabatan;

    @FXML
    private TableColumn<data_karyawan, String> jeniskelamin;

    @FXML
    private TableColumn<data_karyawan, String> kehadiran;

    @FXML
    private TableColumn<data_karyawan, String> nama;
    @FXML
    private TableView<data_karyawan> Jtable;

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
    private void export(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data karyawan");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < Jtable.getColumns().size(); i++) {
                headerRow.createCell(i).setCellValue(Jtable.getColumns().get(i).getText());
            }

            // Data
            for (int i = 0; i < Jtable.getItems().size(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < Jtable.getColumns().size(); j++) {
                    Object cellValue = Jtable.getColumns().get(j).getCellObservableValue(i).getValue();
                    Cell cell = row.createCell(j);
                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            FileOutputStream fileOut = new FileOutputStream("D://data_karyawan.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            JOptionPane.showMessageDialog(null, "Data berhasil diekspor ke Excel.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengekspor data ke Excel.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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

    @FXML
    private void edit(ActionEvent event) {

    }
    @FXML
    private void hapus(ActionEvent event) {

    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    public static ObservableList<data_karyawan> loaddata() {
        Connection connection = koneksi.koneksi.getConnection();
        ObservableList<data_karyawan> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM karyawan1");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new data_karyawan(
                        new SimpleStringProperty(rs.getString("id_karyawan")),
                        new SimpleStringProperty(rs.getString("nama_karyawan")),
                        new SimpleStringProperty(rs.getString("umur")),
                        new SimpleStringProperty(rs.getString("jenis_kelamin")),
                        new SimpleStringProperty(rs.getString("alamat")),
                        new SimpleStringProperty(rs.getString("jabatan")),
                        new SimpleStringProperty(rs.getString("kehadiran"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
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
    private void updateData(data_karyawan selectedItem) {
        try (Connection connection = koneksi.koneksi.getConnection()) {
            String sql = "UPDATE karyawan1 SET nama_karyawan = ?, umur = ?, jenis_kelamin = ?, alamat = ?, jabatan = ?, kehadiran = ? WHERE id_karyawan = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedItem.getNama_karyawan());
            statement.setString(2, selectedItem.getUmur());
            statement.setString(3, selectedItem.getJenis_kelamin());
            statement.setString(4, selectedItem.getAlamat());
            statement.setString(5, selectedItem.getJabatan());
            statement.setString(6, selectedItem.getKehadiran());
            statement.setString(7, selectedItem.getId_karyawan());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Berhasil mengupdate
                showSuccessAlert("Data berhasil diupdate");
            } else {
                // Gagal mengupdate
                showErrorAlert("Gagal mengupdate data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Terjadi kesalahan saat mengupdate data");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<data_karyawan> dataList = loaddata();
        Jtable.setItems(dataList);

        nama.setCellValueFactory(new PropertyValueFactory<>("nama_karyawan"));
        umur.setCellValueFactory(new PropertyValueFactory<>("umur"));
        jeniskelamin.setCellValueFactory(new PropertyValueFactory<>("jenis_kelamin"));
        alamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        jabatan.setCellValueFactory(new PropertyValueFactory<>("jabatan"));
        kehadiran.setCellValueFactory(new PropertyValueFactory<>("kehadiran"));

        // Mengatur agar kolom nama menjadi editable
        nama.setCellFactory(TextFieldTableCell.forTableColumn());
        nama.setOnEditCommit(event -> {
            data_karyawan selectedItem = event.getRowValue();
            selectedItem.setNama_karyawan(event.getNewValue());
            updateData(selectedItem); // Panggil method untuk menyimpan perubahan ke database
        });

        // Mengatur agar kolom umur menjadi editable
        umur.setCellFactory(TextFieldTableCell.forTableColumn());
        umur.setOnEditCommit(event -> {
            data_karyawan selectedItem = event.getRowValue();
            selectedItem.setUmur(event.getNewValue());
            updateData(selectedItem); // Panggil method untuk menyimpan perubahan ke database
        });

        // Mengatur agar kolom jenis kelamin menjadi editable
        jeniskelamin.setCellFactory(TextFieldTableCell.forTableColumn());
        jeniskelamin.setOnEditCommit(event -> {
            data_karyawan selectedItem = event.getRowValue();
            selectedItem.setJenis_kelamin(event.getNewValue());
            updateData(selectedItem); // Panggil method untuk menyimpan perubahan ke database
        });

        // Mengatur agar kolom alamat menjadi editable
        alamat.setCellFactory(TextFieldTableCell.forTableColumn());
        alamat.setOnEditCommit(event -> {
            data_karyawan selectedItem = event.getRowValue();
            selectedItem.setAlamat(event.getNewValue());
            updateData(selectedItem); // Panggil method untuk menyimpan perubahan ke database
        });

        // Mengatur agar kolom jabatan menjadi editable
        jabatan.setCellFactory(TextFieldTableCell.forTableColumn());
        jabatan.setOnEditCommit(event -> {
            data_karyawan selectedItem = event.getRowValue();
            selectedItem.setJabatan(event.getNewValue());
            updateData(selectedItem); // Panggil method untuk menyimpan perubahan ke database
        });

        // Mengatur agar kolom kehadiran menjadi editable
        kehadiran.setCellFactory(TextFieldTableCell.forTableColumn());
        kehadiran.setOnEditCommit(event -> {
            data_karyawan selectedItem = event.getRowValue();
            selectedItem.setKehadiran(event.getNewValue());
            updateData(selectedItem); // Panggil method untuk menyimpan perubahan ke database
        });
    }
}
