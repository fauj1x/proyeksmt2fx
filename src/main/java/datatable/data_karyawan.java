package datatable;

import javafx.beans.property.SimpleStringProperty;

public class data_karyawan {
    public String getId_karyawan() {
        return id_karyawan.get();
    }

    public SimpleStringProperty id_karyawanProperty() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan.set(id_karyawan);
    }

    public String getNama_karyawan() {
        return nama_karyawan.get();
    }

    public SimpleStringProperty nama_karyawanProperty() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan.set(nama_karyawan);
    }

    public String getUmur() {
        return umur.get();
    }

    public SimpleStringProperty umurProperty() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur.set(umur);
    }

    public String getJenis_kelamin() {
        return jenis_kelamin.get();
    }

    public SimpleStringProperty jenis_kelaminProperty() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin.set(jenis_kelamin);
    }

    public String getAlamat() {
        return alamat.get();
    }

    public SimpleStringProperty alamatProperty() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat.set(alamat);
    }

    public String getJabatan() {
        return jabatan.get();
    }

    public SimpleStringProperty jabatanProperty() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan.set(jabatan);
    }

    public String getKehadiran() {
        return kehadiran.get();
    }

    public SimpleStringProperty kehadiranProperty() {
        return kehadiran;
    }

    public void setKehadiran(String kehadiran) {
        this.kehadiran.set(kehadiran);
    }

    public data_karyawan(SimpleStringProperty id_karyawan, SimpleStringProperty nama_karyawan, SimpleStringProperty umur, SimpleStringProperty jenis_kelamin, SimpleStringProperty alamat, SimpleStringProperty jabatan, SimpleStringProperty kehadiran) {
        this.id_karyawan = id_karyawan;
        this.nama_karyawan = nama_karyawan;
        this.umur = umur;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.jabatan = jabatan;
        this.kehadiran = kehadiran;
    }

    SimpleStringProperty id_karyawan;
    SimpleStringProperty nama_karyawan;
    SimpleStringProperty umur;
    SimpleStringProperty jenis_kelamin;
    SimpleStringProperty alamat;
    SimpleStringProperty jabatan;
    SimpleStringProperty kehadiran;
}
