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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.menejemenproduksifx.ManagementProduksi;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TableView<data_karyawan> jTable1;

    @FXML
    private TableColumn<data_karyawan, String> namaKaryawanColumn;

    @FXML
    private TableColumn<data_karyawan, String> alamatKaryawanColumn;


    @FXML
    private TableColumn<data_karyawan, String> jabatanKaryawanColumn;

    public static ObservableList<data_karyawan> loaddata() {
        Connection connection = koneksi.koneksi.getConnection();
        ObservableList<data_karyawan> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id_karyawan, nama_karyawan, umur, jenis_kelamin, alamat, jabatan, kehadiran FROM karyawan1");
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
    ObservableList<data_karyawan> listm;
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<data_karyawan> dataList = loaddata();
        jTable1.setItems(dataList);

        namaKaryawanColumn.setCellValueFactory(new PropertyValueFactory<>("nama_karyawan"));
        alamatKaryawanColumn.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        jabatanKaryawanColumn.setCellValueFactory(new PropertyValueFactory<>("jabatan"));

    }
}