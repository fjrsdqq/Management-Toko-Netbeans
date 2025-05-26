package com.raven.model;

public class ModelIkan {
    private int idBarang;
    private String namaBahan;
    private String tanggal;
    private int jumlahStock;
    private String keterangan;

    public ModelIkan(int idBarang, String namaBahan, int jumlahStock, String tanggal, String keterangan) {
        this.idBarang = idBarang;
        this.namaBahan = namaBahan;
        this.tanggal = tanggal;
        this.jumlahStock = jumlahStock;
        this.keterangan = keterangan;
    }

    public Object[] toRowTable() {
        return new Object[]{
            idBarang,
            namaBahan,
            tanggal,
            jumlahStock,
            keterangan
        };
    }

    // Getter & Setter
    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBahan() {
        return namaBahan;
    }

    public void setNamaBahan(String namaBahan) {
        this.namaBahan = namaBahan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlahStock() {
        return jumlahStock;
    }

    public void setJumlahStock(int jumlahStock) {
        this.jumlahStock = jumlahStock;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
