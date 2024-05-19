package control;

import datatable.temp_table;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.menejemenproduksifx.ManagementProduksi;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class TambahBarangController implements Initializable {

    @FXML
    private ImageView viewer;
    @FXML
    private  ComboBox <String> barang_box;
    @FXML
    private  ComboBox <String> kategori_box;
    @FXML
    private  TextField kode_barang;
    @FXML
    private  TextField nama_barang;
    @FXML
    private  TextField harga_satuan;
    @FXML
    private  TextField jumlah_txt;
    @FXML
    private  TextField kode_txt;
    @FXML
    private Text total;

    @FXML
    private TableView<temp_table> Jtable;

    @FXML
    private TableColumn<temp_table, String> kode_row;
    @FXML
    private TableColumn<temp_table, String> nama_row;
    @FXML
    private TableColumn<temp_table, String> kategori_row;
    @FXML
    private TableColumn<temp_table, String> kode_detail;
    @FXML
    private TableColumn<temp_table, String> harga_row;
    @FXML
    private ComboBox<String> keterangan;

    private ObservableList<temp_table> dataList = FXCollections.observableArrayList();





    private ObservableList<String> namaBarangList = FXCollections.observableArrayList();
    private ObservableList<String> idBarangList = FXCollections.observableArrayList();
    private ObservableList<String> idDetailBarangList = FXCollections.observableArrayList();

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
        FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("SeluruhRiwayatProduksi.fxml"));
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
    void simpan_btn(ActionEvent event) {
    String kode = kode_barang.getText();
    String nama = nama_barang.getText();
    String kategori = kategori_box.getSelectionModel().getSelectedItem();
    String harga = harga_satuan.getText();
        try (Connection connection = koneksi.koneksi.getConnection()) {
            String insertProduksiSQL = "INSERT INTO barang (id_barang, nama_barang, jumlah ,kategori, harga_satuan) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement produksiStatement = connection.prepareStatement(insertProduksiSQL);) {
                produksiStatement.setString(1, kode);
                produksiStatement.setString(2, nama);
                produksiStatement.setString(3, "0");
                produksiStatement.setString(4, kategori);
                produksiStatement.setString(5, harga);

                connection.setAutoCommit(false);
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void kode_txt (ActionEvent event) {

    }
    public static String generateID() {
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
    void simpan_btn1(ActionEvent event){
        try {
            Connection connection = koneksi.koneksi.getConnection();
            String id_transaksi = generateID();
            String tanggal_transaksi = LocalDate.now().toString();
            String tipe_transaksi = keterangan.getValue(); // Mendapatkan item yang dipilih dari ComboBox
            String total_transaksi = total.getText();

            // Jika tipe transaksi adalah "penjualan", lakukan penambahan dan tampilkan window nota.fxml
            if ("Penjualan".equals(tipe_transaksi)) {
                String sql = "INSERT INTO transaksi (id_transaksi, tanggal_transaksi, tipe_transaksi, total_transaksi) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Mengatur nilai parameter untuk statement SQL
                statement.setString(1, id_transaksi);
                statement.setString(2, tanggal_transaksi);
                statement.setString(3, tipe_transaksi);
                statement.setString(4, total_transaksi);
                statement.executeUpdate();
                
                ObservableList<temp_table> allItems = Jtable.getItems();
                for (temp_table rowData : allItems) {
                    String id_detail_barang = rowData.getKode_barang();
                    String sqlDeleteDetailBarang = "DELETE FROM detail_barang WHERE id_detail_barang = ?";
                    PreparedStatement statementDeleteDetailBarang = connection.prepareStatement(sqlDeleteDetailBarang);
                    statementDeleteDetailBarang.setString(1, id_detail_barang);
                    statementDeleteDetailBarang.executeUpdate();
                }

                // Menutup koneksi dan statement
                statement.close();
                connection.close();

                // Menampilkan window nota.fxml
                FXMLLoader loader = new FXMLLoader(ManagementProduksi.class.getResource("nota.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL); // Set modality agar window nota berinteraksi dengan window utama
                stage.initOwner(((Node) event.getTarget()).getScene().getWindow()); // Set window utama sebagai owner
                Scene secondScene = new Scene(root);
                stage.setScene(secondScene);
                stage.show();
            }
            dataList.clear();



        } catch (SQLException | IOException e) {
            // Tangani kesalahan jika terjadi
            e.printStackTrace(); // Tambahkan ini untuk mencetak jejak kesalahan

            // Menampilkan pesan error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
            alert.showAndWait();
        }

    }


    private void loaderidbarang() {
        try (Connection connection = koneksi.koneksi.getConnection()) {
            String query = "SELECT * from barang";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idBarang = resultSet.getString("id_barang");
                String namaBarang = resultSet.getString("nama_barang");



                idBarangList.add(idBarang);
                namaBarangList.add(namaBarang);

            }

            barang_box.setItems(namaBarangList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private String generateKode() {
        try (Connection connection = koneksi.koneksi.getConnection()) {
            // Mengambil ID barang dari ComboBox yang dipilih
            String kodeDariList = "";
            int selectedIndex = barang_box.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1 && !idBarangList.isEmpty()) {
                kodeDariList = idBarangList.get(selectedIndex); // Ambil ID barang dari indeks terpilih
            }

            // Mendapatkan nilai maksimum dari kolom id_produksi
            String maxIdQuery = "SELECT MAX(Right(id_detail_barang,3)) AS max_id FROM detail_barang WHERE id_detail_barang LIKE ?";
            try (PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery)) {
                maxIdStatement.setString(1, kodeDariList + "%");
                try (ResultSet resultSet = maxIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int urutan = resultSet.getInt("max_id") + 1;

                        // Format urutan menjadi 3 digit dengan leading zeros
                        String urutanStr = String.format("%03d", urutan);

                        return kodeDariList + urutanStr;
                    } else {
                        // Jika tidak ada entri sebelumnya dalam tabel
                        return kodeDariList + "001";
                    }
                }
            } catch (SQLException e) {
                // Tangani kesalahan eksekusi query
                e.printStackTrace();
                showAlert("Error", "Failed to execute query: " + e.getMessage());
                return ""; // Mengembalikan string kosong jika terjadi kesalahan
            }
        } catch (SQLException e) {
            // Tangani kesalahan koneksi
            e.printStackTrace();
            showAlert("Error", "Failed to establish connection: " + e.getMessage());
            return ""; // Mengembalikan string kosong jika terjadi kesalahan
        }
    }











//kurang memperbaiki format "B110001"
    //revisi memperbaiki page dijadikan satu




    @FXML
    void generate_btn(ActionEvent event) {
        String jumlahText = jumlah_txt.getText();
        int jumlah = Integer.parseInt(jumlahText);
        String kodeDariList = "";
        int selectedIndex = barang_box.getSelectionModel().getSelectedIndex();
        kodeDariList = idBarangList.get(selectedIndex);

        boolean alertShown = false; // Menandai apakah alert telah ditampilkan

        try (Connection connection = koneksi.koneksi.getConnection()) {
            for (int i = 0; i < jumlah; i++) {
                String kode = generateKode(); // Generate kode unik di awal iterasi
                String insertProduksiSQL = "INSERT INTO detail_barang (id_detail_barang, id_barang ,tanggal, keterangan) VALUES (?, ?, ?, ?)";

                try (PreparedStatement produksiStatement = connection.prepareStatement(insertProduksiSQL)) {
                    produksiStatement.setString(1, kode);
                    produksiStatement.setString(2, kodeDariList);
                    produksiStatement.setString(3, LocalDate.now().toString());
                    produksiStatement.setString(4, "Masuk");

                    // Set auto commit to false to start transaction
                    connection.setAutoCommit(false);
                    produksiStatement.executeUpdate();

                    // Commit the transaction
                    connection.commit();

                    try {
                        Barcode barcode = BarcodeFactory.createCode128(kode);

                        // Mengubah barcode menjadi gambar
                        java.awt.Image awtImage = BarcodeImageHandler.getImage(barcode);

                        // Mengubah gambar menjadi javafx.scene.image.Image
                        Image fxImage = SwingFXUtils.toFXImage((java.awt.image.BufferedImage) awtImage, null);

                        // Menampilkan gambar di ImageView
                        viewer.setImage(fxImage);
                    } catch (Exception e) {
                        // Tangani kesalahan jika terjadi
                        e.printStackTrace(); // Untuk debugging, cetak stack trace
                        showAlert("Error", "An error occurred: " + e.getMessage());
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Gagal menambahkan data: " + e.getMessage());
                    alert.showAndWait();
                }
            }

            // Setelah iterasi selesai, panggil printImages
            Paper customPaper = Paper.A6;
            printImages(viewer, jumlah, customPaper);

            // Tampilkan alert informasi sekali saja
            if (!alertShown) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Informasi");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Data berhasil ditambahkan");
                successAlert.showAndWait();
                alertShown = true; // Tandai bahwa alert telah ditampilkan
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void kode_txt1(KeyEvent event) {
        String kode = kode_txt.getText();
        try (Connection connection = koneksi.koneksi.getConnection()) {
            String query = "SELECT b.id_barang, b.nama_barang, b.kategori, b.harga_satuan " +
                    "FROM detail_barang db " +
                    "JOIN barang b ON db.id_barang = b.id_barang " +
                    "WHERE db.id_detail_barang = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, kode);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idBarang = resultSet.getString("id_barang");
                String namaBarang = resultSet.getString("nama_barang");
                String kategori = resultSet.getString("kategori");
                int hargaSatuan = resultSet.getInt("harga_satuan");
                int jumlah = 1;

                // Mencari apakah id_barang sudah ada dalam tabel temp di database
                String queryCheckExisting = "SELECT COUNT(*) FROM temp WHERE kode_barang = ?";
                PreparedStatement checkExistingStatement = connection.prepareStatement(queryCheckExisting);
                checkExistingStatement.setString(1, idBarang);
                ResultSet existingResult = checkExistingStatement.executeQuery();
                existingResult.next();
                int existingCount = existingResult.getInt(1);
                checkExistingStatement.close();

                // Menambahkan item baru ke dataList
                temp_table newItem = new temp_table(new SimpleStringProperty(kode),
                        new SimpleStringProperty(idBarang),
                        new SimpleStringProperty(namaBarang),
                        new SimpleStringProperty(kategori), 1,
                        hargaSatuan);

                dataList.add(newItem);

                // Jika item "Penjualan" dipilih di ComboBox keterangan
                if ("Penjualan".equals(keterangan.getValue())) {
                    if (existingCount > 0) {
                        // Jika id_barang sudah ada dalam tabel temp, lakukan update
                        String updateQuery = "UPDATE temp SET jumlah = jumlah + 1 WHERE kode_barang = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setString(1, idBarang);
                        updateStatement.executeUpdate();
                        updateStatement.close();
                    } else {
                        // Jika id_barang belum ada dalam tabel temp, tambahkan baru
                        String insertQuery = "INSERT INTO temp (kode_barang, nama_barang, kategori, jumlah, harga_satuan) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                        insertStatement.setString(1, idBarang);
                        insertStatement.setString(2, namaBarang);
                        insertStatement.setString(3, kategori);
                        insertStatement.setInt(4, jumlah);
                        insertStatement.setInt(5, hargaSatuan);
                        insertStatement.executeUpdate();
                        insertStatement.close();
                    }
                }

                // Menghapus teks dari kode_txt setelah data ditambahkan ke dataList
                kode_txt.clear();
            } else {
                System.out.println("Data tidak ditemukan.");
            }

            // Menutup statement dan resultSet
            statement.close();
            resultSet.close();

            // Menghitung jumlah total dari semua item dalam dataList
            int totalHarga = 0;
            for (temp_table item : dataList) {
                totalHarga += item.getJumlah() * item.getHarga_satuan();
            }

            // Menampilkan jumlah total pada TextField total
            total.setText(String.valueOf(totalHarga));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


















    // Method untuk mencetak gambar
    private void printImages(ImageView imageView, int numberOfPages, Paper paper) {
        double margin = 55; // Misalnya, margin 1 inci (36 piksel) di semua sisi
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(viewer.getScene().getWindow())) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(paper, PageOrientation.PORTRAIT, margin, margin, margin, margin);
            job.getJobSettings().setPageLayout(pageLayout);
            for (int i = 0; i < numberOfPages; i++) {
                boolean success = job.printPage(imageView);
                if (!success) {
                    System.out.println("Printing failed for page " + (i + 1));
                    return; // Keluar dari method jika pencetakan gagal untuk halaman apapun
                }
            }
            job.endJob();
        } else {
            System.out.println("Failed to create printer job or show print dialog.");
        }
    }





    // Method untuk menampilkan alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
    loaderidbarang();
        kode_row.setCellValueFactory(cellData -> cellData.getValue().kode_barangProperty());
        kode_detail.setCellValueFactory(cellData -> cellData.getValue().kode_detailProperty());
        nama_row.setCellValueFactory(cellData -> cellData.getValue().nama_barangProperty());
        kategori_row.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        harga_row.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHarga_satuan())));

        ObservableList<String> options = FXCollections.observableArrayList("Jadi", "Mentah");
        kategori_box.setItems(options);

        ObservableList<String> options1 = FXCollections.observableArrayList("Penjualan", "Produksi");
        keterangan.setItems(options1);

        Jtable.setItems(dataList);

    }
}
