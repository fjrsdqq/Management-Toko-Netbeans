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
    private String id_supplier;
    private String nama_supplier;
    private String kontak;
    private String alamat;
    private String perusahaan;
    private String keterangan;
    
    public ModelDataSupplier(String id_supplier, String nama_supplier,String perusahaan,String kontak, String alamat, String keterangan) {
        
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
         
}
