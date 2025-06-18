package com.raven.form;

import com.raven.model.ModelDetailResep; // ganti model yang sesuai
import com.raven.dialog.Message;
import com.raven.main.Main;
import com.raven.model.ModelCard;
import com.raven.model.ModelDataPelanggan;
import com.raven.model.ModelStudent;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import com.raven.swing.table.EventAction;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.DecimalFormat;
import java.awt.event.*;

public class DetailResep extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();
    private DefaultTableModel tableModel;
    private List<ModelDetailResep> listDetailResep = new ArrayList<>();
    
    public DetailResep() {
        initComponents();
        loadNamaResep();
        loadKategori();
        tblDResep.fixTable(jScrollPane1); // ganti nama tabel jika di-designer sebelumnya tblbumbu
        setOpaque(false);
        initData();
    }

    private void initData() {
        initTableData();
        t_cari.getDocument().addDocumentListener(new DocumentListener() {
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
        String keyword = t_cari.getText().trim();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID Detail", "Nama Resep", "Nama Bahan", "Jumlah", "Tanggal"}, 0);
        tblDResep.setModel(model);

        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT d.id_detail, r.nama_resep, b.nama_bahan, d.jumlah, d.tanggal " +
                         "FROM detail_resep d " +
                         "JOIN resep r ON d.id_resep = r.id_resep " +
                         "JOIN barang b ON d.id_barang = b.id_barang " +
                         "WHERE r.nama_resep LIKE ? OR b.nama_bahan LIKE ? OR d.tanggal LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_detail"),
                    rs.getString("nama_resep"),
                    rs.getString("nama_bahan"),
                    rs.getInt("jumlah"),
                    rs.getString("tanggal")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        cbNama.setSelectedIndex(0);
        cbKategori.setSelectedIndex(0);
        cbItem.setSelectedIndex(0);
        txtJumlah.setText("");
    }

    private void initTableData() {
        tableModel = new DefaultTableModel(new Object[]{
            "ID Detail Resep", "ID Resep", "ID Barang", "Nama Resep", "Kategori", "Nama Item", "Jumlah",
        "Tanggal"}, 0);
        tblDResep.setModel(tableModel);
        
        try {
        Connection conn = konek.getConnection();

        String sql = "SELECT dr.id_detail, dr.id_resep, dr.id_barang, dr.jumlah, dr.tanggal, " +
                     "r.nama_resep, b.nama_bahan, b.jenis_bahan " +
                     "FROM detail_resep dr " +
                     "JOIN resep r ON dr.id_resep = r.id_resep " +
                     "JOIN barang b ON dr.id_barang = b.id_barang";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ModelDetailResep detail = new ModelDetailResep(
                rs.getInt("id_detail"),
                rs.getInt("id_resep"),
                rs.getInt("id_barang"),
                rs.getString("nama_resep"),
                rs.getString("jenis_bahan"),
                rs.getString("nama_bahan"),
                rs.getInt("jumlah"),
                rs.getString("tanggal")
            );

            tableModel.insertRow(0, detail.toRowTable());
        }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data detail resep dari database.");
        }
    }
    
    private void loadNamaResep() {
        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT id_resep, nama_resep FROM resep";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cbNama.addItem(rs.getString("nama_resep"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadKategori() {
        cbKategori.addItem("Ikan");
        cbKategori.addItem("Tepung");
        cbKategori.addItem("Bumbu");
    }
    
    private void loadItemByKategori(String kategori) {
        cbItem.removeAllItems();
        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT nama_bahan FROM barang WHERE jenis_bahan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kategori);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cbItem.addItem(rs.getString("nama_bahan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int getIdResepByName(String nama) {
        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT id_resep FROM resep WHERE nama_resep = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_resep");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getIdBarangByName(String nama) {
        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT id_barang FROM barang WHERE nama_bahan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_barang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getNamaResepById(int idResep) {
        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT nama_resep FROM resep WHERE id_resep = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idResep);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nama_resep");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Tidak Ditemukan";
    }

    private String getNamaBarangById(int idBarang) {
        try (Connection conn = konek.getConnection()) {
            String sql = "SELECT nama_bahan FROM barang WHERE id_barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nama_bahan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Tidak Ditemukan";
    }
    
    private void updateTable() {
        DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID Detail", "Nama Resep", "Nama Bahan", "Jumlah", "Tanggal"}, 0
        );

        for (ModelDetailResep detail : listDetailResep) {
            model.addRow(new Object[]{
                detail.getIdDResep(),
                detail.getNamaResep(),
                detail.getNamaItem(),
                detail.getJumlah(),
                detail.getTanggal()
            });
        }
        tblDResep.setModel(model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDResep = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        cbKategori = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbItem = new javax.swing.JComboBox<>();
        cbNama = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Detail Resep");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Detail Resep");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblDResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama Resep", "Tanggal", "Deskripsi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDResepMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDResep);
        if (tblDResep.getColumnModel().getColumnCount() > 0) {
            tblDResep.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        t_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_cariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(678, 678, 678)
                        .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Nama Resep");

        jLabel10.setText(":");

        btnSimpan.setText("Simpan Resep");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel14.setText("Barang");

        jLabel18.setText(":");

        jLabel21.setText("Jumlah Per Porsi (Kg)");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        btnTambah.setText("+");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        cbKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKategoriActionPerformed(evt);
            }
        });

        jLabel15.setText("Tanggal");

        jLabel19.setText(":");

        jLabel16.setText("Kategori");

        jLabel20.setText(":");

        cbItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbItemActionPerformed(evt);
            }
        });

        cbNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNamaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(btnSimpan)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbNama, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbKategori, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambah)))))
                .addContainerGap(846, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(cbNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel19))
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try (Connection conn = konek.getConnection()) {
            String sql = "INSERT INTO detail_resep (id_resep, id_barang, jumlah, tanggal) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (ModelDetailResep detail : listDetailResep) {
                ps.setInt(1, detail.getIdResep());
                ps.setInt(2, detail.getIdBarang());
                ps.setInt(3, detail.getJumlah());
                ps.setString(4, detail.getTanggal());
                ps.addBatch();
            }

            ps.executeBatch();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            listDetailResep.clear();
            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void tblDResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDResepMouseClicked
    int selectedRow = tblDResep.rowAtPoint(evt.getPoint());
    // Cek apakah baris valid dipilih
    if (selectedRow == -1) {
    return; // Tidak ada baris dipilih, keluar dari method
    }

    // Ambil model dari tabel
    DefaultTableModel model = (DefaultTableModel) tblDResep.getModel();

    // Ambil data dari tabel sesuai baris yang diklik
    String namaBarang = model.getValueAt(selectedRow, 0).toString();
    String jumlah = model.getValueAt(selectedRow, 1).toString();

    // Masukkan ke inputan form (misalnya comboboxBarang dan txtJumlah)
    cbKategori.setSelectedItem(namaBarang); // Pastikan isi combobox sesuai
    txtJumlah.setText(jumlah);
    }//GEN-LAST:event_tblDResepMouseClicked

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        String namaResep = cbNama.getSelectedItem().toString();
        String kategori = cbKategori.getSelectedItem().toString();
        String namaItem = cbItem.getSelectedItem().toString();
        String jumlahText = txtJumlah.getText().trim();

        if (jumlahText.isEmpty() || !jumlahText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Jumlah harus diisi dengan angka!");
            return;
        }

        int jumlah = Integer.parseInt(jumlahText);

        Date tanggalDate = txtTanggal.getDate();
        if (tanggalDate == null) {
            JOptionPane.showMessageDialog(this, "Tanggal harus dipilih.");
            return;
        }

        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(tanggalDate);

        // Dapatkan ID resep dan ID barang berdasarkan nama
        int idResep = getIdResepByName(namaResep);
        int idBarang = getIdBarangByName(namaItem);

        if (idResep == -1 || idBarang == -1) {
            JOptionPane.showMessageDialog(this, "ID resep atau barang tidak ditemukan.");
            return;
        }

        ModelDetailResep detail = new ModelDetailResep(0, idResep, idBarang, namaResep, kategori, namaItem, jumlah, tanggal);
        listDetailResep.add(detail);
        updateTable();
        resetForm();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void cbItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbItemActionPerformed
        cbKategori.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String kategori = (String) cbKategori.getSelectedItem();
                loadItemByKategori(kategori);
            }
        }
        });
    }//GEN-LAST:event_cbItemActionPerformed

    private void cbKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKategoriActionPerformed
        String selectedKategori = cbKategori.getSelectedItem().toString();
        loadItemByKategori(selectedKategori);
    }//GEN-LAST:event_cbKategoriActionPerformed

    private void cbNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNamaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbItem;
    private javax.swing.JComboBox<String> cbKategori;
    private javax.swing.JComboBox<String> cbNama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tblDResep;
    private javax.swing.JTextField txtJumlah;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
