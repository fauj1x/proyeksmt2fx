package control;


import datatable.data_produksi;
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

public class SeluruhRiwayatProduksiController implements Initializable {

    @FXML
    private TableColumn<data_produksi, String> id_produksi;

    @FXML
    private TableColumn<data_produksi, String> nama_produksi;

    @FXML
    private TableColumn<data_produksi, String> tanggal_mulai;

    @FXML
    private TableColumn<data_produksi, String> tanggal_selesai;
    @FXML
    private TableColumn<data_produksi, String> penanggung_jawab;

    @FXML
    private TableColumn<data_produksi, String> status;
    @FXML
    private TableView<data_produksi> Jtable1;
    @FXML
    private TableColumn<data_produksi, String> id_produksi1;

    @FXML
    private TableColumn<data_produksi, String> nama_produksi1;

    @FXML
    private TableColumn<data_produksi, String> tanggal_mulai1;

    @FXML
    private TableColumn<data_produksi, String> tanggal_selesai1;
    @FXML
    private TableColumn<data_produksi, String> penanggung_jawab1;

    @FXML
    private TableColumn<data_produksi, String> status1;
    @FXML
    private TableView<data_produksi> Jtable2;

    public static ObservableList<data_produksi> loadData() {
        Connection connection = null;
        ObservableList<data_produksi> list = FXCollections.observableArrayList();

        try {
            connection = koneksi.koneksi.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM produksi where status = 'Berjalan'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new data_produksi(
                        new SimpleStringProperty(rs.getString("id_produksi")),
                        new SimpleStringProperty(rs.getString("nama_produksi")),
                        new SimpleStringProperty(rs.getString("tgl_mul")),
                        new SimpleStringProperty(rs.getString("tgl_sel")),
                        new SimpleStringProperty(rs.getString("penanggung_jawab")),
                        new SimpleStringProperty(rs.getString("status"))
                ));
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
    private void hapus(ActionEvent event) {
        // Mengambil item yang dipilih dari Jtable1 dan Jtable2
        data_produksi selectedItem1 = Jtable1.getSelectionModel().getSelectedItem();
        data_produksi selectedItem2 = Jtable2.getSelectionModel().getSelectedItem();

        if (selectedItem1 != null) {
            hapusData(selectedItem1, Jtable1);
        } else if (selectedItem2 != null) {
            hapusData(selectedItem2, Jtable2);
        } else {
            showErrorAlert("Pilih item yang ingin dihapus");
        }
    }

    private void hapusData(data_produksi selectedItem, TableView<data_produksi> tableView) {
        try (Connection connection = koneksi.koneksi.getConnection()) {
            // Query SQL untuk menghapus data dari database
            String sql = "DELETE FROM produksi WHERE id_produksi = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedItem.getId_produksi());
            int rowsDeleted = statement.executeUpdate();

            // Memeriksa apakah data berhasil dihapus
            if (rowsDeleted > 0) {
                showSuccessAlert("Data berhasil dihapus");
                // Menghapus item dari tabel setelah berhasil dihapus dari database
                tableView.getItems().remove(selectedItem);
            } else {
                showErrorAlert("Gagal menghapus data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Terjadi kesalahan saat menghapus data");
        }
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
    private void export(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data produksi");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < Jtable2.getColumns().size(); i++) {
                headerRow.createCell(i).setCellValue(Jtable2.getColumns().get(i).getText());
            }

            // Data
            for (int i = 0; i < Jtable2.getItems().size(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < Jtable2.getColumns().size(); j++) {
                    Object cellValue = Jtable2.getColumns().get(j).getCellObservableValue(i).getValue();
                    Cell cell = row.createCell(j);
                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            FileOutputStream fileOut = new FileOutputStream("D://data_produksi.xlsx");
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
    private void dashboard_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("Dashboard.fxml"));
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
    public static ObservableList<data_produksi> loadData2() {
        Connection connection = null;
        ObservableList<data_produksi> list = FXCollections.observableArrayList();

        try {
            connection = koneksi.koneksi.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM produksi where status = 'Selesai'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new data_produksi(
                        new SimpleStringProperty(rs.getString("id_produksi")),
                        new SimpleStringProperty(rs.getString("nama_produksi")),
                        new SimpleStringProperty(rs.getString("tgl_mul")),
                        new SimpleStringProperty(rs.getString("tgl_sel")),
                        new SimpleStringProperty(rs.getString("penanggung_jawab")),
                        new SimpleStringProperty(rs.getString("status"))));
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<data_produksi> datalist = loadData();
        Jtable1.setItems(datalist);

        id_produksi.setCellValueFactory(new PropertyValueFactory<>("id_produksi"));
        nama_produksi.setCellValueFactory(new PropertyValueFactory<>("nama_produksi"));
        tanggal_mulai.setCellValueFactory(new PropertyValueFactory<>("tanggal_mulai"));
        tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
        penanggung_jawab.setCellValueFactory(new PropertyValueFactory<>("penaggung_jawab"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<data_produksi> datalist2 = loadData2();
        Jtable2.setItems(datalist2);

        id_produksi1.setCellValueFactory(new PropertyValueFactory<>("id_produksi"));
        nama_produksi1.setCellValueFactory(new PropertyValueFactory<>("nama_produksi"));
        tanggal_mulai1.setCellValueFactory(new PropertyValueFactory<>("tanggal_mulai"));
        tanggal_selesai1.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
        penanggung_jawab1.setCellValueFactory(new PropertyValueFactory<>("penaggung_jawab"));
        status1.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
