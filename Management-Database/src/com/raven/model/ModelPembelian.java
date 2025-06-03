package com.raven.model;

public class ModelPembelian {
    private int id; 
    private int id_supp; 
    private String namaSupplier; 
    private String nPerusahaan; 
    private String noTelp; 
    private String tanggal; 
    private double totalH; 
    private String keterangan; 

    public ModelPembelian(int id, int id_supp, String namaSupplier, String nPerusahaan, String noTelp, String tanggal, double totalH, String keterangan) {
        this.id = id;
        this.id_supp = id_supp;
        this.namaSupplier = namaSupplier;
        this.nPerusahaan = nPerusahaan;
        this.noTelp = noTelp;
        this.tanggal = tanggal;
        this.totalH = totalH;
        this.keterangan = keterangan;
    }

    public Object[] toRowTable() {
        return new Object[]{
            id,
            id_supp,
            namaSupplier,
            nPerusahaan,
            noTelp,
            tanggal,
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}