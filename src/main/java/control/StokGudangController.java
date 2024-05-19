package control;


import datatable.data_barang;
import datatable.data_detail_barang;
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

public class StokGudangController implements Initializable {

    @FXML
    private TableColumn<data_barang, String> id_barang;

    @FXML
    private TableColumn<data_barang, String> kategori;

    @FXML
    private TableColumn<data_barang, String> nama_barang;

    @FXML
    private TableColumn<data_barang, String> stok;

    @FXML
    private TableView<data_barang> Jtable;

    @FXML
    private TableColumn<data_detail_barang, String> id_detail_barang;

    @FXML
    private TableColumn<data_detail_barang, String> id_barang1;

    @FXML
    private TableColumn<data_detail_barang, String> tanggal;

    @FXML
    private TableColumn<data_detail_barang, String> keterangan;

    @FXML
    private TableView<data_detail_barang> Jtable1;

    public static ObservableList<data_barang> loadData() {
        Connection connection = null;
        ObservableList<data_barang> list = FXCollections.observableArrayList();

        try {
            connection = koneksi.koneksi.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM barang");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new data_barang(
                        new SimpleStringProperty(rs.getString("id_barang")),
                        new SimpleStringProperty(rs.getString("nama_barang")),
                        new SimpleStringProperty(rs.getString("jumlah")),
                        new SimpleStringProperty(rs.getString("kategori"))));
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
    public static ObservableList<data_detail_barang> loaddetailData() {
        Connection connection = null;
        ObservableList<data_detail_barang> list = FXCollections.observableArrayList();

        try {
            connection = koneksi.koneksi.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM detail_barang");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new data_detail_barang(
                        new SimpleStringProperty(rs.getString("id_detail_barang")),
                        new SimpleStringProperty(rs.getString("id_barang")),
                        new SimpleStringProperty(rs.getString("tanggal")),
                        new SimpleStringProperty(rs.getString("keterangan"))));
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
        // Mengambil item yang dipilih dari Jtable dan Jtable2
        data_barang selectedItem1 = Jtable.getSelectionModel().getSelectedItem();
        data_detail_barang selectedItem2 = Jtable1.getSelectionModel().getSelectedItem();

        if (selectedItem1 != null) {
            hapusData(selectedItem1, Jtable);
        } else if (selectedItem2 != null) {
            hapusData(selectedItem2, Jtable1);
        } else {
            showErrorAlert("Pilih item yang ingin dihapus");
        }
    }

    private void hapusData(Object selectedItem, TableView<?> tableView) {
        String idColumnName;
        String tableName;
        if (selectedItem instanceof data_barang) {
            idColumnName = "id_barang";
            tableName = "barang";
        } else if (selectedItem instanceof data_detail_barang) {
            idColumnName = "id_detail_barang";
            tableName = "detail_barang";
        } else {
            showErrorAlert("Tipe data tidak valid");
            return;
        }

        try (Connection connection = koneksi.koneksi.getConnection()) {
            // Query SQL untuk menghapus data dari database
            String sql = "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, getId(selectedItem));
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

    private String getId(Object item) {
        if (item instanceof data_barang) {
            return ((data_barang) item).getId_barang();
        } else if (item instanceof data_detail_barang) {
            return ((data_detail_barang) item).getId_detail_barang();
        } else {
            return null;
        }
    }



    @FXML
    private void export(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data barang");

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

            FileOutputStream fileOut = new FileOutputStream("D://data_barang.xlsx");
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
    private void riwayat_transaksi (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("RiwayatTransaksi.fxml"));
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.hide();
        Scene secondscene = new Scene(loader.load());
        stage.setScene(secondscene);
        stage.show();
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<data_barang> datalist = loadData();
        Jtable.setItems(datalist);

        id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
        nama_barang.setCellValueFactory(new PropertyValueFactory<>("nama_barang"));
        stok.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        ObservableList<data_detail_barang> datalist1 = loaddetailData();
        Jtable1.setItems(datalist1);

        id_detail_barang.setCellValueFactory(new PropertyValueFactory<>("id_detail_barang"));
        id_barang1.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
        tanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        keterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
    }
}
