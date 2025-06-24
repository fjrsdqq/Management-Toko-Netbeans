package com.raven.model;

public class ModelPembelian {
    private int id; 
    private int id_supp;
    private int idb;
    private String namaB; 
    private String namaSupplier; 
    private String nPerusahaan; 
    private String noTelp; 
    private String tanggal;
    private double hargap;
    private double totalH; 
    private String keterangan; 

    public ModelPembelian(int id, int id_supp,int idb,String namaB, String namaSupplier, String nPerusahaan, String noTelp, String tanggal,double hargap, double totalH, String keterangan) {
        this.id = id;
        this.id_supp = id_supp;
        this.idb = idb;
        this.namaB = namaB;
        this.namaSupplier = namaSupplier;
        this.nPerusahaan = nPerusahaan;
        this.noTelp = noTelp;
        this.tanggal = tanggal;
        this.hargap = hargap;
        this.totalH = totalH;
        this.keterangan = keterangan;
    }

    public Object[] toRowTable() {
        return new Object[]{
            id,
            id_supp,
            idb,
            namaB,
            namaSupplier,
            nPerusahaan,
            noTelp,
            tanggal,
            hargap,
            totalH,
            keterangan
        };
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_supp() {
        return id_supp;
    }

    public void setId_supp(int id_supp) {
        this.id_supp = id_supp;
    }
    
    public int getIdb() {
        return idb;
    }

    public void setIdb(int idb) {
        this.idb = idb;
    }
    
    public String getnamaB() {
        return namaB;
    }

    public void setnamaB(String namaB) {
        this.namaB = namaB;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getNPerusahaan() {
        return nPerusahaan;
    }

    public void setNPerusahaan(String nPerusahaan) {
        this.nPerusahaan = nPerusahaan;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    public double getTotalH() {
        return totalH;
    }

    public void setTotalH(double totalH) {
        this.totalH = totalH;
    }

    public double gethargap() {
        return hargap;
    }

    public void sethargap(double hargap) {
        this.hargap = hargap;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}