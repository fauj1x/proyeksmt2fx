package control;


import datatable.data_barang;
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
        ObservableList<data_barang> datalist = loadData();
        Jtable.setItems(datalist);

        id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
        nama_barang.setCellValueFactory(new PropertyValueFactory<>("nama_barang"));
        stok.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
    }
}
