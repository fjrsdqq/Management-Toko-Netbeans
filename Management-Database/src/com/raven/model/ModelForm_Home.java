/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.model;

/**
 *
 * @author ASUS
 */
public class ModelForm_Home {
    private String id;
    private String tanggal;
    private String tipe;
    private String keterangan;
    private double jumlah;

    public ModelForm_Home(String id, String tanggal, String tipe, String keterangan, double jumlah) {
        this.id = id;
        this.tanggal = tanggal;
        this.tipe = tipe;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }

    public Object[] toRowTable() {
        String pemasukan = "";
        String pengeluaran = "";

        if (tipe.equals("Pemasukan Harian")) {
            pemasukan = String.valueOf(jumlah);
        } else if (tipe.equals("Pengeluaran Harian")) {
            pengeluaran = String.valueOf(jumlah);
        }

        return new Object[]{id, tanggal, tipe, pemasukan, pengeluaran, keterangan};
    }


    
public String getid_keuangan() {
        return id;
    }

    public void setid_keuangan(String id_keuangan) {
        this.id = id_keuangan;
    }

    public String gettanggal() {
        return tanggal;
    }

    public void settanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String tipe() {
        return tipe;
    }

    public void settipe(String tipe) {
        this.tipe = tipe;
    }


    public String keterangan() {
        return keterangan;
    }

    public void keterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public double getjumlah() {
        return jumlah;
    }

    public void setjumlah(double jumlah) {
        this.jumlah = jumlah;
    }}


