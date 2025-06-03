/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author ferdi
 */
public class ModelDataPenjualan {
    private String id_penjualan;
    private String id_pelanggan;
    private String tanggal;
    private Double total;
    private String keterangan;
    private String nama_pelanggan;
    
    

    public ModelDataPenjualan(String id_penjualan, String id_pelanggan, String tanggal, String nama_pelanggan, Double total, String keterangan) {
        
        this.id_penjualan = id_penjualan;
        this.id_pelanggan = id_pelanggan;
        this.tanggal = tanggal;
        this.total = total;
        this.keterangan = keterangan;
        this.nama_pelanggan = nama_pelanggan;
        
        
    }

    /**public ModelDataPenjualan(String id, String nama, String keterangan, String kontak, String total, String keterangan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }**/

    
    public String getid_penjualan() {
        return id_penjualan;
    }

    public void setid_penjualan(String id_penjualan) {
        this.id_penjualan = id_penjualan;
    }

    public String getid_pelanggan() {
        return id_pelanggan;
    }

    public void setid_pelanggan(String id_pelanggan) {
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
    
    
   

    public Object[] toRowTable() {
    return new Object[]{ id_penjualan, id_pelanggan, tanggal, nama_pelanggan, total, keterangan};
}
    
}