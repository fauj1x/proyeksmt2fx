package control;

import datatable.data_chart;
import datatable.data_chart2;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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

public class DashboardController implements Initializable {
    @FXML
    private TableView<data_karyawan> jTable1;

    @FXML
    private TableColumn<data_karyawan, String> namaKaryawanColumn;

    @FXML
    private TableColumn<data_karyawan, String> alamatKaryawanColumn;


    @FXML
    private TableColumn<data_karyawan, String> jabatanKaryawanColumn;

    @FXML
    private LineChart<String, Number> chart_produksi;
    @FXML
    private BarChart<String, Number> riwayat_produksi;


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



    public static ObservableList<data_chart> loadDataChart() {
        Connection connection = koneksi.koneksi.getConnection();
        ObservableList<data_chart> list = FXCollections.observableArrayList();

        try {
            String query = "SELECT p.id_produksi, p.tgl_mul, dp.target_produksi " +
                    "FROM produksi p " +
                    "JOIN detail_produksi dp ON p.id_produksi = dp.id_produksi " +
                    "WHERE DATE(p.tgl_mul) = CURDATE()";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new data_chart(
                        new SimpleStringProperty(rs.getString("tgl_mul")),
                        new SimpleStringProperty(rs.getString("target_produksi"))
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

    public static ObservableList<data_chart2> loadDataChart2() {
        Connection connection = koneksi.koneksi.getConnection();
        ObservableList<data_chart2> list = FXCollections.observableArrayList();

        try {
            String query = "SELECT p.id_produksi, p.tgl_mul, dp.target_produksi " +
                    "FROM produksi p " +
                    "JOIN detail_produksi dp ON p.id_produksi = dp.id_produksi ";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new data_chart2(
                        new SimpleStringProperty(rs.getString("tgl_mul")),
                        new SimpleStringProperty(rs.getString("target_produksi"))
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
    @FXML
    private void riwayat_transaksi (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("RiwayatTransaksi.fxml"));
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


        ObservableList<data_chart> productionData = loadDataChart();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (data_chart dp : productionData) {
            series.getData().add(new XYChart.Data<>(dp.getTanggal_produksi(), Integer.parseInt(dp.getTarget()), dp.getTanggal_produksi()));
        }

        chart_produksi.getData().add(series);

        ObservableList<data_chart2> productionData2 = loadDataChart2();

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        for (data_chart2 dp : productionData2) {
            series2.getData().add(new XYChart.Data<>(dp.getTanggal_produksi(), Integer.parseInt(dp.getTarget()), dp.getTanggal_produksi()));
        }

        riwayat_produksi.getData().add(series2);

    }
}