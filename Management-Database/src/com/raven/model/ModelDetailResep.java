/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

public class ModelDetailResep {
    private int idDResep;
    private int idResep;
    private int idBarang;
    private String namaResep;
    private String kategori;
    private String namaItem;
    private double hargat;
    private int jumlah;
    private String tanggal;
    
    public ModelDetailResep(int idDResep, int idResep, int idBarang,String namaResep, String kategori, String namaItem, int jumlah,double hargat, String tanggal) {
        this.idDResep = idDResep;
        this.idResep = idResep;
        this.idBarang = idBarang;
        this.namaResep = namaResep;
        this.kategori = kategori;
        this.namaItem = namaItem;
        this.jumlah = jumlah;
        this.hargat = hargat;
        this.tanggal = tanggal;
    }

    public Object[] toRowTable() {
        return new Object[]{
            //idDResep,
            idResep,
           // idBarang,
            namaResep,
          //  kategori,
            namaItem,
            jumlah,
            hargat,
            tanggal,
        };
    }

    // Getter & Setter
    public int getIdDResep() {
        return idDResep;
    }

    public void setIdDResep(int idDResep) {
        this.idDResep = idDResep;
    }
    
    public int getIdResep() {
        return idResep;
    }

    public void setIdResep(int idResep) {
        this.idResep = idResep;
    }
    
    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaResep() {
        return namaResep;
    }

    public void setNamaResep(String namaResep) {
        this.namaResep = namaResep;
    }
    
    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }
    
    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    public double gethargat(){
        return hargat;
    }
    public void sethargat(double hargat){
        this.hargat = hargat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}

