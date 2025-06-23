/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.model;

import java.math.BigDecimal;

/**
 *
 * @author herus
 */

public class ModelKaryawan {
    private int idKaryawan;
    private String namaKaryawan;
    private String jabatan;
    private String notelpon;
    private BigDecimal gaji; // Ubah dari int ke BigDecimal
    private String alamat;

    // Konstruktor sesuai form DataKaryawan
    public ModelKaryawan(int idKaryawan, String namaKaryawan, String jabatan, BigDecimal gaji, String notelpon, String alamat) {
        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.jabatan = jabatan;
        this.gaji = gaji;
        this.notelpon = notelpon;
        this.alamat = alamat;
    }

    // Menghapus konstruktor yang tidak digunakan atau tidak relevan
    // public ModelKaryawan(int aInt, String string, int aInt0, String string0, String string1) { ... }

    // Method untuk ditampilkan dalam tabel
    public Object[] toRowTable() {
        return new Object[]{
            idKaryawan,
            namaKaryawan,
            jabatan,
            gaji, // Menampilkan gaji bertipe BigDecimal
            notelpon,
            alamat
        };
    }

    // Getter dan Setter Lengkap
    public int getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(int idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNotelpon() {
        return notelpon;
    }

    public void setNotelpon(String notelpon) {
        this.notelpon = notelpon;
    }

    public BigDecimal getGaji() {
        return gaji;
    }

    public void setGaji(BigDecimal gaji) {
        this.gaji = gaji;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}