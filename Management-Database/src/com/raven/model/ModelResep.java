/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

public class ModelResep {
    private int idResep;
    private String namaResep;
    private String namaPelanggan;
    private String tanggal;
    private String keterangan;
    //new Object[]{"ID","IDS", "Nama Ikan", "Tanggal","Harga/kg", "Stok(kg)", "Keterangan"}
    public ModelResep(int idResep, String namaResep, String namaPelanggan, String tanggal, String keterangan) {
        this.idResep = idResep;
        this.namaResep = namaResep;
        this.namaPelanggan =namaPelanggan;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public Object[] toRowTable() {
        return new Object[]{
            idResep,
            namaResep,
            namaPelanggan,
            tanggal,
            keterangan
        };
    }

    // Getter & Setter
    public int getIdResep() {
        return idResep;
    }

    public void setIdResep(int idResep) {
        this.idResep = idResep;
    }
    
    public String getnamaResep() {
        return namaResep;
    }

    public void setnamaResep(String namaResep) {
        this.namaResep = namaResep;
    }
    public String getnamaPelanggan() {
        return namaPelanggan;
    }

    public void setnamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

