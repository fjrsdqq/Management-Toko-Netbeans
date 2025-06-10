package com.raven.model;

public class ModelBumbu {
    private int idBarang;
    private int idSupplier;
    private String namaBahan;
    private String tanggal;
    private double hargap;
    private int jumlahStock;
    private String keterangan;
    //new Object[]{"ID","IDS", "Nama Ikan", "Tanggal","Harga/kg", "Stok(kg)", "Keterangan"}
    public ModelBumbu(int idBarang,int idSupplier, String namaBahan,double hargap, int jumlahStock, String tanggal, String keterangan) {
        this.idBarang = idBarang;
        this.idSupplier =idSupplier;
        this.namaBahan = namaBahan;
        this.tanggal = tanggal;
        this.hargap = hargap;
        this.jumlahStock = jumlahStock;
        this.keterangan = keterangan;
    }

    public Object[] toRowTable() {
        return new Object[]{
            idBarang,
            idSupplier,
            namaBahan,
             hargap,
            jumlahStock,
            tanggal,
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
    public int getidSupplier() {
        return idSupplier;
    }

    public void setidSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
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
    
    public double gethargap() {
        return hargap;
    }

    public void setJhargap(double hargap) {
        this.hargap = hargap;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
