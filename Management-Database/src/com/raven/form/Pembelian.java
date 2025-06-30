package com.raven.form;

import java.util.Date;
import com.raven.model.ModelDataSupplier;
import com.raven.model.ModelPembelian;
import com.raven.model.Modelbarang;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class Pembelian extends javax.swing.JPanel {
com.raven.component.koneksi konek = new com.raven.component.koneksi();
private boolean isEditing = false;
private int selectedPembelianId = -1;
private int idb = -1;
    public Pembelian() {
        initComponents();
        initKategoriComboBox();
        tblpembelian.fixTable(jScrollPane1);
        setOpaque(false);
        initData();
        initListeners();
        
    }

    private void initData() {
        loadSuppliersToComboBox();
        initTableData();
        txtsearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });
    }
    private void performSearch() {
    String keyword = txtsearch.getText().trim();

    if (keyword.isEmpty()) {
        initTableData(); // Menampilkan semua data jika input kosong
        return;
    }

    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID", "IDS", "IDB", "Barang", "Nama Supplier", "Perusahaan", "Telp", "Tanggal", "Harga/Kg", "total", "Keterangan"}, 0
    );
    tblpembelian.setModel(model);

    String sql = "SELECT " +
                 "p.id_pembelian, " +
                 "p.id_supplier, " +
                 "p.id_barang, " +
                 "p.tanggal, " +
                 "p.total, " +
                 "p.keterangan, " +
                 "s.nama_supplier, " +
                 "s.perusahaan AS NPerusahaan, " +
                 "s.no_hp AS Notelp, " +
                 "b.hargapkg, " +
                 "b.nama_bahan " +
                 "FROM pembelian p " +
                 "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                 "JOIN barang b ON p.id_barang = b.id_barang " +
                 "WHERE LOWER(b.nama_bahan) LIKE ? OR " +
                 "LOWER(s.nama_supplier) LIKE ? OR " +
                 "LOWER(s.perusahaan) LIKE ? OR " +
                 "LOWER(s.no_hp) LIKE ? OR " +
                 "LOWER(p.keterangan) LIKE ? " +
                 "ORDER BY p.tanggal DESC";

    try (Connection conn = konek.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Masukkan keyword ke semua parameter pencarian
        for (int i = 1; i <= 5; i++) {
            ps.setString(i, "%" + keyword.toLowerCase() + "%");
        }

        ResultSet rs = ps.executeQuery();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            ModelPembelian data = new ModelPembelian(
                rs.getInt("id_pembelian"),
                rs.getInt("id_supplier"),
                rs.getInt("id_barang"),
                rs.getString("nama_bahan"),
                rs.getString("nama_supplier"),
                rs.getString("NPerusahaan"),
                rs.getString("Notelp"),
                sdf.format(rs.getDate("tanggal")),
                rs.getDouble("hargapkg"),
                rs.getDouble("total"),
                rs.getString("keterangan")
            );
            model.addRow(data.toRowTable());
        }

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
        }

        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mencari data: " + e.getMessage());
    }
}

    private void initTableData() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "IDS","IDB","Barang", "Nama Supplier", "Perusahaan", "Telp", "Tanggal","Harga/Kg", "total", "Keterangan"}, 0
        );
        tblpembelian.setModel(model);

        String sql = "SELECT " +
                     "p.id_pembelian, " +
                     "p.id_supplier, " +
                     "p.id_barang, " +
                     "p.tanggal, " +
                     "p.total, " +
                     "p.keterangan, " +
                     "s.nama_supplier, " +
                     "s.perusahaan AS NPerusahaan, " +
                     "s.no_hp AS Notelp, " + // no_hp dari DB akan dipetakan ke kontak di ModelDataSupplier
                     "s.alamat, " + // Tambahkan alamat jika ingin disimpan di ModelDataSupplier
                     "s.keterangan AS KeteranganSupplier, " + // Keterangan dari supplier
                     "b.hargapkg, "+
                     "b.nama_bahan " +
                     "FROM pembelian p " +
                     "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                     "JOIN barang b ON p.id_barang = b.id_barang " +
                     "ORDER BY p.tanggal DESC";

        try (Connection conn = konek.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                ModelPembelian data = new ModelPembelian(
                    rs.getInt("id_pembelian"),
                    rs.getInt("id_supplier"),
                    rs.getInt("id_barang"),// id_supplier (int) dari pembelian
                    rs.getString("nama_bahan"),
                    rs.getString("nama_supplier"),
                    rs.getString("NPerusahaan"),
                    rs.getString("Notelp"),
                    sdf.format(rs.getDate("tanggal")),
                    rs.getDouble("hargapkg"),
                    rs.getDouble("total"),
                    rs.getString("keterangan")
                );
                model.addRow(data.toRowTable());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data pembelian dari database: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
       }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpembelian = new com.raven.swing.table.Table();
        txtsearch = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbKategori = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtHargap = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbBarang = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();

        jMenu1.setText("jMenu1");

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Pembelian");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Pembelian");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblpembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblpembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpembelianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpembelian);

        jLabel19.setText("Cari:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
        );

        jLabel2.setText("Tanggal");

        jLabel3.setText("Total Harga");

        jLabel4.setText("Keterangan");

        jLabel7.setText(":");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel9.setText("Nama Supplier");

        jLabel10.setText(":");

        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel<>());

        jLabel11.setText("Nama Barang");

        jLabel12.setText("Kategori Barang");

        cmbKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKategoriActionPerformed(evt);
            }
        });

        jLabel13.setText(":");

        jLabel14.setText(":");

        jLabel15.setText("Harga/kg");

        txtHargap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargapActionPerformed(evt);
            }
        });

        jLabel16.setText(":");

        jLabel17.setText(":");

        jLabel18.setText(":");

        cmbBarang.setModel(new javax.swing.DefaultComboBoxModel<>());
        cmbBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBarangActionPerformed(evt);
            }
        });

        jLabel6.setText("Stock / kg");

        jLabel8.setText(":");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel6)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(39, 39, 39)
                                .addComponent(jLabel15)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHargap, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(txtStok)
                    .addComponent(txtKeterangan)
                    .addComponent(txtTotal))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel7)
                    .addComponent(txtHargap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(jLabel2))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel17)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel18)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnSave))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblpembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpembelianMouseClicked
        int selectedRow = tblpembelian.getSelectedRow();

    if (selectedRow != -1) {
        isEditing = true;
        btnSave.setText("Update");

        selectedPembelianId = (int) tblpembelian.getValueAt(selectedRow, 0); // ID Pembelian
        
        String id_supp_str_from_table = tblpembelian.getValueAt(selectedRow, 1).toString(); // ID Supplier (String)
        idb = (int) tblpembelian.getValueAt(selectedRow, 2);
        String tanggalStr = tblpembelian.getValueAt(selectedRow, 7).toString(); // Tanggal
        String totalStr = tblpembelian.getValueAt(selectedRow, 9).toString(); // totalH
        String keterangan = tblpembelian.getValueAt(selectedRow, 10).toString(); // Keterangan
        String HargaPkg = tblpembelian.getValueAt(selectedRow, 8).toString();
       
        

        // Perbaikan parsing
        double total = Double.parseDouble(totalStr);
        double hrga = Double.parseDouble(HargaPkg);
        int stok = (int) (total / hrga);  // Dibulatkan ke bawah

        // Set tanggal ke DateChooser
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalStr);
            txtTanggal.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Format tanggal di tabel tidak valid: " + tanggalStr, "Error", JOptionPane.ERROR_MESSAGE);
        }

        txtHargap.setText(HargaPkg);
        txtStok.setText(String.valueOf(stok));
        txtTotal.setText(totalStr);
        txtKeterangan.setText(keterangan);
        

        // Set comboBox supplier
        DefaultComboBoxModel<ModelDataSupplier> cmbModel = (DefaultComboBoxModel<ModelDataSupplier>) cmbSupplier.getModel();
        for (int i = 0; i < cmbModel.getSize(); i++) {
            ModelDataSupplier s = cmbModel.getElementAt(i);
            if (s.getId_supplier().equals(id_supp_str_from_table)) {
                cmbSupplier.setSelectedItem(s);
                break;
            }
        }
    }
    }//GEN-LAST:event_tblpembelianMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Date utilDate = txtTanggal.getDate();
        if (utilDate == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih tanggal!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Modelbarang selectedBarang = (Modelbarang) cmbBarang.getSelectedItem(); 
        String NamaB = selectedBarang.getNamaBahan();
        String Hargapkg = txtHargap.getText();
        String totalHargaStr = txtTotal.getText();
        String keterangan = txtKeterangan.getText();
        String kategoriB = (String) cmbKategori.getSelectedItem();
        ModelDataSupplier selectedSupplier = (ModelDataSupplier) cmbSupplier.getSelectedItem();

        if (totalHargaStr.isEmpty() || keterangan.isEmpty() ||
            selectedSupplier == null || selectedSupplier.getId_supplier().equals("-1")) { // Periksa ID Supplier String
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua data!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double total = Double.parseDouble(totalHargaStr);
            double HargaP = Double.parseDouble(Hargapkg);
            
            double Stok = total/HargaP;
            // Konversi id_supplier dari String ke int untuk database
            int idSupplier = Integer.parseInt(selectedSupplier.getId_supplier());

            if (isEditing) {
            if (selectedPembelianId <= 0) {
                JOptionPane.showMessageDialog(this, "ID pembelian tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            updatePembelian(sqlDate, total, keterangan, idSupplier);
            updateKeuangan(sqlDate, total, selectedPembelianId);
            updateBarang(sqlDate, NamaB, kategoriB, Stok, HargaP, keterangan, idSupplier);
        } else {
            int idBarang = InsertBarang(sqlDate, HargaP, Stok, keterangan, kategoriB, NamaB, idSupplier);
            if (idBarang != -1) {
                int idPembelian = insertPembelian(sqlDate, total, keterangan, idSupplier, idBarang);
                if (idPembelian != -1) {
                    InsertBeli(sqlDate, total, idPembelian);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mendapatkan ID barang!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        clearForm();
        initTableData();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Total Harga dan Harga per kg harus berupa angka.", "Error Format", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btnSaveActionPerformed

    private int insertPembelian(java.sql.Date tanggal, double total, String keterangan, int idSupplier, int idBarang) {
    String sql = "INSERT INTO pembelian (tanggal, total, keterangan, id_supplier, id_barang) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setDate(1, tanggal);
        stmt.setDouble(2, total);
        stmt.setString(3, keterangan);
        stmt.setInt(4, idSupplier);
        stmt.setInt(5, idBarang);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Pembelian berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    return rs.getInt(1); // return id_pembelian
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error saat menambah pembelian: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    return -1;
}
    private void InsertBeli(java.sql.Date tanggal, double total, int idPembelian) {
    String sql = "INSERT INTO keuangan (tanggal, tipe, jumlah, id_pembelian) VALUES (?, ?, ?, ?)";
    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, tanggal);
        stmt.setString(2, "Pengeluaran Harian");
        stmt.setDouble(3, total);
        stmt.setInt(4, idPembelian);

        stmt.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan keuangan: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    private int InsertBarang(java.sql.Date tanggal, double HargaP, double Stok, String keterangan, String kategoriB, String NamaB, int idSupplier) {
    String sql = "INSERT INTO barang (id_supplier, nama_bahan, jenis_bahan, stok, hargapkg, keterangan, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        stmt.setInt(1, idSupplier);
        stmt.setString(2, NamaB);
        stmt.setString(3, kategoriB);
        stmt.setDouble(4, Stok);
        stmt.setDouble(5, HargaP);
        stmt.setString(6, keterangan);
        stmt.setDate(7, tanggal);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idBarang = generatedKeys.getInt(1); // ← Ambil id_barang yang di-generate otomatis
                    return idBarang;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan barang.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error saat insert barang: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    return -1; // jika gagal
}
private void updateBarang(java.sql.Date tanggal,String NamaB, String kategoriB,double Stok,double HargaP, String keterangan, int idSupplier) {
    String sql = "UPDATE barang SET nama_bahan=?, jenis_bahan=?, stok=?, hargapkg=?, keterangan=?, tanggal=? WHERE id_barang=?";
    
    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, NamaB); // ✅ nama_bahan
        stmt.setString(2, kategoriB); // ✅ jenis_bahan
        stmt.setDouble(3, Stok); // ✅ stok
        stmt.setDouble(4, HargaP); // ✅ hargapkg
        stmt.setString(5, keterangan); // ✅ keterangan
        stmt.setDate(6, tanggal); // ✅ tanggal
        stmt.setInt(7, idb); // ✅ WHERE id_barang=?

        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            JOptionPane.showMessageDialog(this, "Barang berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate Barang.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error database saat update: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    

    private void updatePembelian(java.sql.Date tanggal, double total, String keterangan, int idSupplier) {
    String sql = "UPDATE pembelian SET tanggal=?, total=?, keterangan=?, id_supplier=? WHERE id_pembelian=?";
    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, tanggal);
        stmt.setDouble(2, total);
        stmt.setString(3, keterangan);
        stmt.setInt(4, idSupplier);
        stmt.setInt(5, selectedPembelianId);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            JOptionPane.showMessageDialog(this, "Pembelian berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error database saat update: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    private void updateKeuangan(java.sql.Date tanggal, double total, int idPembelian) {
    String sql = "UPDATE keuangan SET tanggal=?, jumlah=? WHERE id_pembelian=?";
    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, tanggal);
        stmt.setDouble(2, total);
        stmt.setInt(3, idPembelian);

        stmt.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengupdate keuangan: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    private void initListeners() {
        // ActionListener untuk cmbBarang
        cmbBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Modelbarang selectedBarang = (Modelbarang) cmbBarang.getSelectedItem();
                if (selectedBarang != null && !selectedBarang.getIdBarang().equals("-1")) {
                    txtHargap.setText(String.valueOf(selectedBarang.getHargapkg()));
                    calculateTotalHarga();
                } else {
                    txtHargap.setText("");
                    txtTotal.setText("");
                }
            }
        });

        // DocumentListener untuk txtStok
        txtStok.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotalHarga();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotalHarga();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotalHarga();
            }
        });
    }

    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tblpembelian.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pembelian yang ingin dihapus dari tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data pembelian ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idPembelianToDelete = (int) tblpembelian.getValueAt(selectedRow, 0);

            String sql = "DELETE FROM pembelian WHERE id_pembelian = ?";
            try (Connection conn = konek.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idPembelianToDelete);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Pembelian berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    initTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error database saat menghapus: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtHargapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargapActionPerformed

    private void cmbKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKategoriActionPerformed
        // TODO add your handling code here:
         String selectedKategori = (String) cmbKategori.getSelectedItem();
    if (selectedKategori != null) {
        loadBarangByKategori(selectedKategori);
    };

    }//GEN-LAST:event_cmbKategoriActionPerformed

    private void cmbBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBarangActionPerformed
        // TODO add your handling code here:
        // Di dalam konstruktor Pembelian() atau method initData() atau method baru
// Setelah cmbBarang diinisialisasi (biasanya oleh initComponents())

        Modelbarang selectedBarang = (Modelbarang) cmbBarang.getSelectedItem();
        if (selectedBarang != null && !selectedBarang.getIdBarang().equals("-1")) { // Pastikan bukan placeholder
            txtHargap.setText(String.valueOf(selectedBarang.getHargapkg()));
        } else {
            txtHargap.setText(""); // Kosongkan jika tidak ada barang yang dipilih atau placeholder
        }
    

    }//GEN-LAST:event_cmbBarangActionPerformed
private void loadBarangByKategori(String kategori) {
    cmbBarang.removeAllItems();
    // Tambahkan placeholder awal jika diperlukan
    cmbBarang.addItem(new Modelbarang("-1", "-- Pilih Barang --", 0.0));

    String sql = "SELECT id_barang, nama_bahan, hargapkg FROM barang WHERE jenis_bahan = ?"; // Ambil hargapkg

    try (Connection conn = konek.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, kategori);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String idBarang = rs.getString("id_barang");
            String namaBahan = rs.getString("nama_bahan");
            double hargapkg = rs.getDouble("hargapkg"); // Ambil harga per kg
            cmbBarang.addItem(new Modelbarang(idBarang, namaBahan, hargapkg)); // Buat objek Modelbarang dengan hargapkg
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat barang: " + e.getMessage());
    }
}
private void calculateTotalHarga() {
        try {
            double hargaPerKg = 0.0;
            // Coba ambil harga per kg dari txtHargap
            if (!txtHargap.getText().isEmpty()) {
                hargaPerKg = Double.parseDouble(txtHargap.getText());
            }

            double stok = 0.0;
            // Coba ambil stok dari txtStok
            if (!txtStok.getText().isEmpty()) {
                stok = Double.parseDouble(txtStok.getText());
            }

            double totalHarga = stok * hargaPerKg;
            txtTotal.setText(String.format("%.2f", totalHarga)); // Format ke 2 angka di belakang koma
        } catch (NumberFormatException ex) {
            // Jika input bukan angka, kosongkan total atau tampilkan pesan error
            txtTotal.setText("");
            // Anda bisa menambahkan JOptionPane.showMessageDialog di sini jika ingin memberitahu user
            // JOptionPane.showMessageDialog(this, "Input Stok atau Harga/Kg harus berupa angka.", "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearForm() {
        txtTanggal.setDate(null);
        txtTotal.setText("");
        txtKeterangan.setText("");
        txtStok.setText(""); // Bersihkan juga field stok
        txtHargap.setText(""); // Bersihkan juga field harga per kg
        cmbSupplier.setSelectedIndex(0);
        cmbKategori.setSelectedIndex(0); // Mungkin perlu direset juga
        cmbBarang.removeAllItems(); // Kosongkan cmbBarang
        isEditing = false;
        idb = -1;
        selectedPembelianId = -1;
        btnSave.setText("Save");
    }
    private void initKategoriComboBox() {
    cmbKategori.removeAllItems();
    cmbKategori.addItem("Ikan");
    cmbKategori.addItem("Tepung");
    cmbKategori.addItem("Bumbu");
}
   



 
    


    private void loadSuppliersToComboBox() {
        DefaultComboBoxModel<ModelDataSupplier> model = new DefaultComboBoxModel<>();
        model.addElement(new ModelDataSupplier("-1", "-- Pilih Supplier --", "", "", "", "")); // Placeholder awal
        try (Connection conn = konek.getConnection();
             // Sesuaikan query agar mengambil kolom yang sesuai dengan ModelDataSupplier Anda
             PreparedStatement stmt = conn.prepareStatement("SELECT id_supplier, nama_supplier, perusahaan, no_hp, alamat, keterangan FROM supplier");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id_supplier"); // id_supplier sebagai String
                String nama = rs.getString("nama_supplier");
                String perusahaan = rs.getString("perusahaan");
                String noHp = rs.getString("no_hp"); // kontak di ModelDataSupplier
                String alamat = rs.getString("alamat");
                String keterangan = rs.getString("keterangan");
                model.addElement(new ModelDataSupplier(id, nama, perusahaan, noHp, alamat, keterangan));
            }
            cmbSupplier.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<Modelbarang> cmbBarang;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JComboBox<ModelDataSupplier> cmbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table tblpembelian;
    private javax.swing.JTextField txtHargap;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtStok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
