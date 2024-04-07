package datatable;

import javafx.beans.property.SimpleStringProperty;

public class data_barang {

 SimpleStringProperty id_barang;
 SimpleStringProperty nama_barang;
 SimpleStringProperty jumlah;
 SimpleStringProperty kategori;

 public data_barang(SimpleStringProperty id_barang, SimpleStringProperty nama_barang, SimpleStringProperty jumlah, SimpleStringProperty kategori) {
  this.id_barang = id_barang;
  this.nama_barang = nama_barang;
  this.jumlah = jumlah;
  this.kategori = kategori;
 }

 public String getId_barang() {
  return id_barang.get();
 }

 public SimpleStringProperty id_barangProperty() {
  return id_barang;
 }

 public void setId_barang(String id_barang) {
  this.id_barang.set(id_barang);
 }

 public String getNama_barang() {
  return nama_barang.get();
 }

 public SimpleStringProperty nama_barangProperty() {
  return nama_barang;
 }

 public void setNama_barang(String nama_barang) {
  this.nama_barang.set(nama_barang);
 }

 public String getJumlah() {
  return jumlah.get();
 }

 public SimpleStringProperty jumlahProperty() {
  return jumlah;
 }

 public void setJumlah(String jumlah) {
  this.jumlah.set(jumlah);
 }

 public String getKategori() {
  return kategori.get();
 }

 public SimpleStringProperty kategoriProperty() {
  return kategori;
 }

 public void setKategori(String kategori) {
  this.kategori.set(kategori);
 }
}
