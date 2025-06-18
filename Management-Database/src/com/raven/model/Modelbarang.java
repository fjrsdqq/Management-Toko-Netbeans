/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author ferdi
 */
public class Modelbarang {
    private String idBarang;
    private String namaBahan;
    private double hargapkg; // Tambahkan properti ini

    public Modelbarang(String idBarang, String namaBahan, double hargapkg) { // Sesuaikan konstruktor
        this.idBarang = idBarang;
        this.namaBahan = namaBahan;
        this.hargapkg = hargapkg;
    }

    // Getter untuk idBarang
    public String getIdBarang() {
        return idBarang;
    }

    // Getter untuk namaBahan
    public String getNamaBahan() {
        return namaBahan;
    }

    // Getter untuk hargapkg
    public double getHargapkg() {
        return hargapkg;
    }

    @Override
    public String toString() {
        return namaBahan; // Ini penting agar combo box menampilkan nama bahan
    }
}

