/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author ferdi
 */
public class ModelDataSupplier {
    private String id_supplier; // Ini String di model Anda
    private String nama_supplier;
    private String kontak; // Ini adalah no_hp dari DB
    private String alamat;
    private String perusahaan;
    private String keterangan;

    public ModelDataSupplier(String id_supplier, String nama_supplier, String perusahaan, String kontak, String alamat, String keterangan) {
        this.id_supplier = id_supplier;
        this.nama_supplier = nama_supplier;
        this.kontak = kontak;
        this.alamat = alamat;
        this.perusahaan = perusahaan;
        this.keterangan = keterangan;
    }

    public Object[] toRowTable() {
        return new Object[]{ id_supplier, nama_supplier, perusahaan, kontak, alamat, keterangan};
    }

    // --- Getter Methods ---
    // Tambahkan method ini jika belum ada
    public String getId_supplier() {
        return id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public String getKontak() { // Ini akan digunakan sebagai noTelp
        return kontak;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getPerusahaan() { // Ini akan digunakan sebagai NPerusahaan
        return perusahaan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    // --- Setter Methods (Optional, tambahkan jika Anda perlu mengubah nilai setelah objek dibuat) ---
    // public void setId_supplier(String id_supplier) { this.id_supplier = id_supplier; }
    // public void setNama_supplier(String nama_supplier) { this.nama_supplier = nama_supplier; }
    // ... dan seterusnya untuk setiap properti

    // --- toString() Method ---
    // Sangat penting agar JComboBox menampilkan nama supplier yang benar
    @Override
    public String toString() {
        return nama_supplier;
    }
}