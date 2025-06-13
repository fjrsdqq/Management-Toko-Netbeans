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

    public Modelbarang(String idBarang, String namaBahan) {
        this.idBarang = idBarang;
        this.namaBahan = namaBahan;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getNamaBahan() {
        return namaBahan;
    }

    @Override
    public String toString() {
        return namaBahan; // Yang ditampilkan di combo box
    }
}

