/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author kel3
 */
public class ModelDataPenjualan {
    private int id_penjualan;
    private int id_pelanggan;
    private String nama_pelanggan;
    private String nama_resep;
    private Double total;
    private String tanggal;
    private String keterangan;
    
    public ModelDataPenjualan(int id_penjualan, int id_pelanggan,  String nama_pelanggan, String resep, Double total, String tanggal, String keterangan) {
        
        this.id_penjualan = id_penjualan;
        this.id_pelanggan = id_pelanggan;
        this.tanggal = tanggal;
        this.total = total;
        this.keterangan = keterangan;
        this.nama_pelanggan = nama_pelanggan;
        this.nama_resep = resep;
    }

    /**public ModelDataPenjualan(String id, String nama, String keterangan, String kontak, String total, String keterangan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
     * @return 
    }**/

    public int getid_penjualan() {
        return id_penjualan;
    }

    public void setid_penjualan(int id_penjualan) {
        this.id_penjualan = id_penjualan;
    }

    public int getid_pelanggan() {
        return id_pelanggan;
    }

    public void setid_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String gettanggal() {
        return tanggal;
    }

    public void settanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    public String getiketerangan() {
        return keterangan;
    }

    public void setketerangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public Double gettotal() {
        return total;
    }

    public void settotal(Double total) {
        this.total = total;
    }
    
    public String getnama_pelanggan() {
        return nama_pelanggan;
    }

    public void setnama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }
    
    public String getresep() {
        return nama_resep;
    }

    public void setresep(String resep) {
        this.nama_resep = resep;
    }
    
    public Object[] toRowTable() {
    return new Object[]{ id_penjualan, id_pelanggan, nama_pelanggan, nama_resep, total, tanggal, keterangan};
}

}