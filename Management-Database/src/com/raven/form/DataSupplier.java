package com.raven.form;

import com.raven.dialog.Message;
import com.raven.main.Main;
import com.raven.model.ModelDataSupplier;
import com.raven.model.ModelStudent;
import com.raven.swing.table.EventAction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataSupplier extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();

    public DataSupplier() {
        initComponents();
        table1.fixTable(jScrollPane1);
        setOpaque(false);
        initData();
    }

    private void initData() {
        initTableData();
    }

    private void initTableData() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
    model.setRowCount(0);

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = konek.getConnection();
        String sql = "SELECT id_supplier, nama_supplier, perusahaan, no_hp, alamat, keterangan FROM supplier ORDER BY id_supplier DESC";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id_supplier");
            String nama = rs.getString("nama_supplier");
            String kontak = rs.getString("no_hp");
            String alamats = rs.getString("alamat");
            String perus = rs.getString("perusahaan");
            String keterangan = rs.getString("keterangan");
            

            ModelDataSupplier barang = new ModelDataSupplier(id, nama,perus,kontak, alamats, keterangan);
            model.addRow(barang.toRowTable()); // Tanpa eventAction
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database.");
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new com.raven.swing.table.Table();
        btnsearch = new javax.swing.JButton();
        txtsearch = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtalamat = new javax.swing.JTextField();
        txtnotel = new javax.swing.JTextField();
        txtperusahaan = new javax.swing.JTextField();
        txtketerangan = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Dashboard / Home");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Supplier");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id","Nama Supplier", "Alamat", "No telepon", "Perusahaan", "keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        btnsearch.setText("Seaarch");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1096, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsearch))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnsearch)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("Nama Supplier");

        jLabel7.setText("Alamat");

        jLabel8.setText("No Telepon");

        jLabel9.setText("Keterangan");

        jLabel10.setText("Perusahaan");

        txtnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaActionPerformed(evt);
            }
        });

        jLabel11.setText(":");

        jLabel12.setText(":");

        jLabel13.setText(":");

        jLabel14.setText(":");

        jLabel15.setText(":");

        txtalamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtalamatActionPerformed(evt);
            }
        });

        txtnotel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnotelActionPerformed(evt);
            }
        });

        txtperusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtperusahaanActionPerformed(evt);
            }
        });

        txtketerangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtketeranganActionPerformed(evt);
            }
        });

        btnsave.setText("Save");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        btndelete.setText("Delete");
        btndelete.setToolTipText("");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtperusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtnotel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnsave)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnedit)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btndelete))
                                            .addComponent(txtketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 363, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(txtnotel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15)
                    .addComponent(txtperusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14)
                    .addComponent(txtketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsave)
                    .addComponent(btnedit)
                    .addComponent(btndelete))
                .addGap(9, 9, 9)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaActionPerformed

    private void txtalamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtalamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtalamatActionPerformed

    private void txtnotelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnotelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotelActionPerformed

    private void txtperusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtperusahaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtperusahaanActionPerformed

    private void txtketeranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtketeranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtketeranganActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
    int selectedRow = table1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
        return;
    }

    // Ambil ID dari baris yang dipilih
    String id_supplier = table1.getValueAt(selectedRow, 0).toString();

    // Konfirmasi sebelum menghapus
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Apakah kamu yakin ingin menghapus data dengan ID " + id_supplier + "?", 
        "Konfirmasi Hapus", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = konek.getConnection(); // konek = class koneksi milikmu
            String sql = "DELETE FROM supplier WHERE id_supplier = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id_supplier);

            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                ShowData();     // Refresh tabel
                resetForm();    // Bersihkan form input
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal dihapus.");
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    } 
    }//GEN-LAST:event_btndeleteActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        int selectedRow = table1.getSelectedRow();

    if (selectedRow != -1) {
        // Ambil data dari tabel berdasarkan index kolom
        String id = table1.getValueAt(selectedRow, 0).toString();
        String nama = table1.getValueAt(selectedRow, 1).toString();
        String alamat = table1.getValueAt(selectedRow, 4).toString();
        String nohp = table1.getValueAt(selectedRow, 3).toString();
        String perusahaan = table1.getValueAt(selectedRow, 2).toString();
        String keterangan = table1.getValueAt(selectedRow, 5).toString();

        // Tampilkan data ke input field
        txtnama.setText(nama);
        txtalamat.setText(alamat);
        txtnotel.setText(nohp);
        txtperusahaan.setText(perusahaan);
        txtketerangan.setText(keterangan);
    }
    }//GEN-LAST:event_table1MouseClicked

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
    String nama = txtnama.getText().trim();
    String alamat = txtalamat.getText().trim();
    String kontak = txtnotel.getText().trim();
    String perusahaan = txtperusahaan.getText().trim();
    String keterangan = txtketerangan.getText().trim();

    // Validasi input tidak boleh kosong
    if (nama.isEmpty() || alamat.isEmpty() || kontak.isEmpty() || perusahaan.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field (kecuali keterangan) harus diisi.");
        return;
    }

    try {
        Connection conn = konek.getConnection(); // Ganti dengan method koneksi milikmu
        String sql = "INSERT INTO supplier (nama_supplier, alamat, no_hp, perusahaan, keterangan) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nama);
        ps.setString(2, alamat);
        ps.setString(3, kontak);
        ps.setString(4, perusahaan);
        ps.setString(5, keterangan);

        int result = ps.executeUpdate();

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            ShowData();     // Refresh tabel
            resetForm();    // Kosongkan field input
        } else {
            JOptionPane.showMessageDialog(this, "Data gagal disimpan.");
        }

        ps.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        int selectedRow = table1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit terlebih dahulu.");
        return;
    }

    // Ambil ID dari baris yang dipilih
    String id_supplier = table1.getValueAt(selectedRow, 0).toString();

    // Ambil data dari form input
    String nama = txtnama.getText().trim();
    String alamat = txtalamat.getText().trim();
    String no_hp = txtnotel.getText().trim();
    String perusahaan = txtperusahaan.getText().trim();
    String keterangan = txtketerangan.getText().trim();

    // Validasi input kosong
    if (nama.isEmpty() || alamat.isEmpty() || no_hp.isEmpty() || perusahaan.isEmpty() || keterangan.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
        return;
    }

    // Eksekusi update
    try {
        Connection conn = konek.getConnection();
        String sql = "UPDATE supplier SET nama_supplier=?, alamat=?, no_hp=?, perusahaan=?, keterangan=? WHERE id_supplier=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nama);
        ps.setString(2, alamat);
        ps.setString(3, no_hp);
        ps.setString(4, perusahaan);
        ps.setString(5, keterangan);
        ps.setString(6, id_supplier);

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            ShowData();     // Refresh tabel
            resetForm();    // Kosongkan form input (buatkan fungsi resetForm jika belum)
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data.");
        }

        ps.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btneditActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        String keyword = txtsearch.getText().trim(); // Ambil kata kunci dari field pencarian

    DefaultTableModel model = (DefaultTableModel) table1.getModel();
    model.setRowCount(0); // Kosongkan tabel sebelum menampilkan hasil pencarian

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = konek.getConnection();

        // Query multi-kolom: LIKE untuk semua kolom
        String sql = "SELECT id_supplier, nama_supplier, alamat, no_hp, perusahaan, keterangan FROM supplier " +
                     "WHERE id_supplier LIKE ? OR nama_supplier LIKE ? OR alamat LIKE ? OR no_hp LIKE ? OR perusahaan LIKE ? OR keterangan LIKE ?";

        ps = conn.prepareStatement(sql);
        for (int i = 1; i <= 6; i++) {
            ps.setString(i, "%" + keyword + "%");
        }

        rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id_supplier");
            String nama = rs.getString("nama_supplier");
            String alamat = rs.getString("alamat");
            String nohp = rs.getString("no_hp");
            String perusahaan = rs.getString("perusahaan");
            String keterangan = rs.getString("keterangan");

            Object[] row = { id, nama, alamat, nohp, perusahaan, keterangan };
            model.addRow(row);
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat pencarian: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnsearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table table1;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtketerangan;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnotel;
    private javax.swing.JTextField txtperusahaan;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables

    private void resetForm() {
    txtnama.setText("");
    txtalamat.setText("");
    txtnotel.setText("");
    txtperusahaan.setText("");
    txtketerangan.setText("");
}

    private void ShowData() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Menghapus semua baris yang ada di tabel

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = konek.getConnection(); // Sesuaikan ini dengan koneksi database milikmu
            String sql = "SELECT id_supplier, nama_supplier, alamat, no_hp, perusahaan, keterangan FROM supplier";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_supplier");
                String nama = rs.getString("nama_supplier");
                String alamat = rs.getString("alamat");
                String nohp = rs.getString("no_hp");
                String perusahaan = rs.getString("perusahaan");
                String keterangan = rs.getString("keterangan");

                // Tambahkan baris ke tabel
                Object[] row = { id, nama, alamat, nohp, perusahaan, keterangan };
                model.addRow(row);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
