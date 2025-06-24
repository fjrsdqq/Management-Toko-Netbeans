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
import javax.swing.SwingUtilities;

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
        
        cbKategori.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedKategori = cbKategori.getSelectedItem().toString();
            loadItemByKategori(selectedKategori);
        }
    });
        cbItem.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        hitungTotalHarga();
    }
});

txtJumlah.getDocument().addDocumentListener(new DocumentListener() {
    public void insertUpdate(DocumentEvent e) { hitungTotalHarga(); }
    public void removeUpdate(DocumentEvent e) { hitungTotalHarga(); }
    public void changedUpdate(DocumentEvent e) { hitungTotalHarga(); }
});
        
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
    ActionListener[] listeners = cbKategori.getActionListeners();
    for (ActionListener l : listeners) {
        cbKategori.removeActionListener(l);
    }

    cbNama.setSelectedIndex(0);
    cbKategori.setSelectedIndex(0);
    cbItem.removeAllItems();
    txtJumlah.setText("");
    txtTotal.setText("");

    for (ActionListener l : listeners) {
        cbKategori.addActionListener(l);
    }
}

    private void initTableData() {
        tableModel = new DefaultTableModel(new Object[]{
             "ID Resep",  "Nama Resep", "Nama Item", "Jumlah","Harga",
        "Tanggal"}, 0);
        tblDResep.setModel(tableModel);
        
        try {
        Connection conn = konek.getConnection();

        String sql = "SELECT dr.id_detail, dr.id_resep, dr.id_barang, dr.jumlah, dr.hargapkg, dr.tanggal, " +
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
                rs.getDouble("hargapkg"),
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
        cbKategori.addItem("Bahan");
        cbKategori.addItem("Ikan");
        cbKategori.addItem("Tepung");
        cbKategori.addItem("Bumbu");
    }
    
    private void loadItemByKategori(String kategori) {
    cbItem.removeAllItems();
    if (kategori == null) return;

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
        new Object[]{"ID Detail", "Nama Resep", "Nama Bahan", "Harga","Jumlah", "Tanggal"}, 0
        );

        for (ModelDetailResep detail : listDetailResep) {
            model.addRow(new Object[]{
                detail.getIdDResep(),
                detail.getNamaResep(),
                detail.getNamaItem(),
                detail.gethargat(),
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
        jLabel21 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
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
        jLabel2 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

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

        jLabel21.setText("Jumlah Per Porsi (Kg)");

        jLabel18.setText(":");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        btnTambah.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnTambah.setText("Tambah Detail");
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

        jLabel2.setText("Total Harga");

        jLabel22.setText(":");

        jLabel23.setText(":");

        jLabel3.setText("Jumlah :");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(34, 34, 34))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(63, 63, 63)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(cbNama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTotal))))
                .addGap(846, 846, 846))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel19))
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try (Connection conn = konek.getConnection()) {
            String sql = "INSERT INTO detail_resep (id_resep, id_barang, jumlah, tanggal, hargapkg) VALUES (?, ?, ?, ?, ?)";
PreparedStatement ps = conn.prepareStatement(sql);

for (ModelDetailResep detail : listDetailResep) {
    ps.setInt(1, detail.getIdResep());
    ps.setInt(2, detail.getIdBarang());
    ps.setInt(3, detail.getJumlah());
    ps.setString(4, detail.getTanggal());
    ps.setDouble(5, detail.gethargat()); // pastikan method ini mengembalikan harga total
    ps.addBatch();
}

            ps.executeBatch();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            listDetailResep.clear();
            initTableData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed
private void setKategoriTanpaEvent(String kategori) {
    ActionListener[] listeners = cbKategori.getActionListeners();
    for (ActionListener l : listeners) {
        cbKategori.removeActionListener(l);
    }

    cbKategori.setSelectedItem(kategori);
    loadItemByKategori(kategori);

    for (ActionListener l : listeners) {
        cbKategori.addActionListener(l);
    }
}

    private void tblDResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDResepMouseClicked
    int selectedRow = tblDResep.rowAtPoint(evt.getPoint());
    if (selectedRow == -1) return;

    DefaultTableModel model = (DefaultTableModel) tblDResep.getModel();

    String kategori = model.getValueAt(selectedRow, 2).toString();  // langsung ambil dari tabel
    String namaItem = model.getValueAt(selectedRow, 3).toString();
    String jumlah = model.getValueAt(selectedRow, 4).toString();
    String total = model.getValueAt(selectedRow, 5).toString();

    setKategoriTanpaEvent(kategori);
    SwingUtilities.invokeLater(() -> cbItem.setSelectedItem(namaItem));

    txtJumlah.setText(jumlah);
    txtTotal.setText(total);
    }//GEN-LAST:event_tblDResepMouseClicked

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        String namaResep = cbNama.getSelectedItem().toString();
        String kategori = cbKategori.getSelectedItem().toString();
        String namaItem = cbItem.getSelectedItem().toString();
        String jumlahText = txtJumlah.getText().trim();
        String total = txtTotal.getText().trim();

        if (jumlahText.isEmpty() || !jumlahText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Jumlah harus diisi dengan angka!");
            return;
        }

        int jumlah = Integer.parseInt(jumlahText);
        double HargaP = Double.parseDouble(total);
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

        ModelDetailResep detail = new ModelDetailResep(0, idResep, idBarang, namaResep, kategori, namaItem, jumlah,HargaP, tanggal);
        listDetailResep.add(detail);
        updateTable();
        resetForm();
    }//GEN-LAST:event_btnTambahActionPerformed
private String getKategoriByNamaItem(String namaItem) {
    try (Connection conn = konek.getConnection()) {
        String sql = "SELECT jenis_bahan FROM barang WHERE nama_bahan = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, namaItem);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("jenis_bahan");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "";
}

    private void cbItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbItemActionPerformed
        
    }//GEN-LAST:event_cbItemActionPerformed

    private void cbKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKategoriActionPerformed
        
    }//GEN-LAST:event_cbKategoriActionPerformed
private void hitungTotalHarga() {
    String nama = (String) cbItem.getSelectedItem();
    String jumlahText = txtJumlah.getText().trim();

    if (nama == null || jumlahText.isEmpty() || !jumlahText.matches("\\d+")) {
        txtTotal.setText("0");
        return;
    }

    int jumlah = Integer.parseInt(jumlahText);

    try (Connection conn = konek.getConnection()) {
        String sql = "SELECT hargapkg FROM barang WHERE nama_bahan = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nama);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double harga = rs.getDouble("hargapkg");
            double total = harga * jumlah;
            txtTotal.setText(String.valueOf(total));
        } else {
            txtTotal.setText("0");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        txtTotal.setText("0");
    }
}

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tblDResep;
    private javax.swing.JTextField txtJumlah;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
