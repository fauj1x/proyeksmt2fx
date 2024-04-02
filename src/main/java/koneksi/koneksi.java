package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class koneksi {
    public static Connection koneksi;

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/manajemenprod";
            String user = "root";
            String password = "";

            koneksi = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // Menampilkan pesan kesalahan menggunakan Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Koneksi Gagal");
            alert.setContentText("Koneksi gagal: " + e.getMessage());
            alert.showAndWait();
        }
        return koneksi;
    }
}
