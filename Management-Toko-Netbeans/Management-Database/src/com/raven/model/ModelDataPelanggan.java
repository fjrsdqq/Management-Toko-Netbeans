/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author ferdi
 */
public class ModelDataPelanggan {
    private String Id_Pelanggan;
    private String Nama_pelanggan;
    private String kontaks;
    private String Alamat;
    private String perusahaan;
    
    

    public ModelDataPelanggan(String Id_Pelanggan, String Nama_pelanggan,String perusahaan,String kontaks, String Alamat) {
        
        this.Id_Pelanggan = Id_Pelanggan;
        this.Nama_pelanggan = Nama_pelanggan;
        this.kontaks = kontaks;
        this.Alamat = Alamat;
         this.perusahaan = perusahaan;
        
        
    }

    public ModelDataPelanggan(String id, String nama, String alamat, String kontak, String perusahaan, String keterangan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public String getId_Pelanggan() {
        return Id_Pelanggan;
    }

    public void setId_Pelanggan(String Id_Pelanggan) {
        this.Id_Pelanggan = Id_Pelanggan;
    }

    public String getNama_pelanggan() {
        return Nama_pelanggan;
    }

    public void setNama_pelanggan(String Nama_pelanggan) {
        this.Nama_pelanggan = Nama_pelanggan;
    }

    public String getkontaks() {
        return kontaks;
    }

    public void setkontaks(String kontaks) {
        this.kontaks = kontaks;
    }


    public String getiAlamat() {
        return Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }
    
    public String getiperusahaan() {
        return perusahaan;
    }

    public void setperusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }
    
    
   

    public Object[] toRowTable() {
    return new Object[]{ Id_Pelanggan, Nama_pelanggan,perusahaan,kontaks, Alamat};
}
    
}