package com.raven.form;

import com.raven.dialog.Message;
import com.raven.main.Main;
import com.raven.model.ModelCard;
import com.raven.model.ModelDataPelanggan;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import java.awt.Color;
import java.sql.*;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class DataPelanggan extends javax.swing.JPanel {
com.raven.component.koneksi konek = new com.raven.component.koneksi();

    public DataPelanggan() {
        initComponents();
        table1.fixTable(jScrollPane1);
        setOpaque(false);
        initData();
    }

    private void initData() {
        initCardData();
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
            new Object[]{"ID", "Nama", "Perusahaan", "No Telepon", "Alamat"}, 0
        );
        table1.setModel(model);

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT * FROM pelanggan WHERE "
                       + "nama_pelanggan LIKE ? OR "
                       + "perusahaan LIKE ? OR "
                       + "no_hp LIKE ? OR "
                       + "alamat LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 4; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                ModelDataPelanggan data = new ModelDataPelanggan(
                    rs.getString("id_pelanggan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getString("perusahaan")
                );
                model.addRow(data.toRowTable());
            }

            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mencari data.");
        }
    }
    
    private void initTableData() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
    model.setRowCount(0);

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = konek.getConnection();
        String sql = "SELECT id_pelanggan,nama_pelanggan, alamat, no_hp, perusahaan FROM pelanggan ORDER BY id_pelanggan DESC";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id_pelanggan");
            String nama = rs.getString("nama_pelanggan");
            String kontak = rs.getString("no_hp");
            String alamats = rs.getString("alamat");
            String perus = rs.getString("perusahaan");
            
            
            

            ModelDataPelanggan barang = new ModelDataPelanggan(id, nama,perus,kontak, alamats);
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

    private void initCardData() {
        Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card1.setData(new ModelCard("TOP INCOME", 5100, 20, icon1));
        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.MONETIZATION_ON, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card2.setData(new ModelCard("TOP PELANGGAN", 2000, 60, icon2));
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new com.raven.component.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new com.raven.component.Card();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new com.raven.swing.table.Table();
        txtsearch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        txtnotel = new javax.swing.JTextField();
        txtperusahaan = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btndelet = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        txtalamat = new javax.swing.JTextField();

        card1.setColorGradient(new java.awt.Color(211, 28, 215));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Pelanggan");

        card2.setBackground(new java.awt.Color(10, 30, 214));
        card2.setColorGradient(new java.awt.Color(72, 111, 252));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Pelanggan");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID","Nama", "Perusahaan","No Telepon", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        txtsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1645, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setText("NAMA PELANGGAN");

        jLabel6.setText("NO TELP");

        jLabel7.setText("ALAMAT PELANGGAN");

        jLabel8.setText("PERUSAHAAN ");

        jLabel9.setText(":");

        jLabel10.setText(":");

        jLabel11.setText(":");

        jLabel12.setText(":");

        txtnotel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnotelActionPerformed(evt);
            }
        });

        btnsave.setText("Save");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btndelet.setText("Delete");
        btndelet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletActionPerformed(evt);
            }
        });

        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8))
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtnotel, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                            .addComponent(txtnama)
                                            .addComponent(txtperusahaan)
                                            .addComponent(txtalamat)))
                                    .addComponent(jLabel3))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnedit)
                                    .addComponent(btndelet)
                                    .addComponent(btnsave))
                                .addGap(78, 78, 78)
                                .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsave))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btnedit))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10)
                                    .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btndelet)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel6)
                                        .addComponent(txtnotel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtperusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)))
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtnotelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnotelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotelActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        String nama = txtnama.getText();
        String alamats = txtalamat.getText();
        String notel = txtnotel.getText();
        String perusahaan = txtperusahaan.getText();
        
        if (nama.isEmpty() || alamats.isEmpty() || notel.isEmpty() || perusahaan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
            return;
        }
        try (Connection conn = konek.getConnection()) {
        String sql = "INSERT INTO pelanggan SET nama_pelanggan=?, Perusahaan=?, no_hp=?, alamat=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nama);
        ps.setString(2, perusahaan);
        ps.setString(3, notel); // Idealnya, password di-hash sebelum disimpan ke database
        ps.setString(4, alamats);

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
            txtnama.setText(""); // Mengosongkan text field nama
            txtalamat.setText(""); 
            txtnotel.setText(""); 
            txtperusahaan.setText("");
             initTableData();// refresh tabel
             
        } else {
            JOptionPane.showMessageDialog(null, "Gagal Menambahkan data. Pastikan Password staff/Petugas benar.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
    }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletActionPerformed
        // TODO add your handling code here:
        int selectedRow = table1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
        return;
    }

    String id = table1.getValueAt(selectedRow, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus barang ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        Connection conn = konek.getConnection();
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Pelanggan berhasil dihapus.");
        ps.close();
        conn.close();
        ShowData();
        resetForm();
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menghapus Pelanggan.");
    }
    }//GEN-LAST:event_btndeletActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        
        int selectedRow = table1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit terlebih dahulu.");
        return;
    }

    // Ambil ID dari tabel
    String id = table1.getValueAt(selectedRow, 0).toString();

    // Ambil nilai dari form
    String nama = txtnama.getText().trim();
    String alamat = txtalamat.getText().trim();
    String notel = txtnotel.getText().trim();
    String perusahaan = txtperusahaan.getText().trim();

    // Validasi input kosong
    if (nama.isEmpty() || alamat.isEmpty() || notel.isEmpty() || perusahaan.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
        return;
    }

    try (Connection conn = konek.getConnection()) {
        // Sintaks UPDATE yang benar
        String sql = "UPDATE pelanggan SET nama_pelanggan=?, perusahaan=?, no_hp=?, alamat=? WHERE id_pelanggan=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nama);
        ps.setString(2, perusahaan);
        ps.setString(3, notel);
        ps.setString(4, alamat);
        ps.setInt(5, Integer.parseInt(id));

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
            ShowData();
            resetForm();           // Kosongkan form
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang diperbarui.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
        e.printStackTrace();  // Untuk debugging
    }
}

// Fungsi bantu untuk mereset form
private void resetForm() {
    txtnama.setText("");
    txtalamat.setText("");
    txtnotel.setText("");
    txtperusahaan.setText("");

    }//GEN-LAST:event_btneditActionPerformed
public void ShowData() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Menghapus semua baris yang ada di tabel

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = konek.getConnection(); // Sesuaikan ini dengan koneksi database milikmu
            String sql = "SELECT * FROM pelanggan ORDER BY id_pelanggan DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_pelanggan");
                String nama = rs.getString("nama_pelanggan");
                String alamat = rs.getString("alamat");
                String nohp = rs.getString("no_hp");
                String perusahaan = rs.getString("perusahaan");

                // Tambahkan baris ke tabel
                Object[] row = { id, nama, perusahaan, nohp, alamat};
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
    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchActionPerformed
    
    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        int selectedRow = table1.getSelectedRow();
        if (selectedRow >= 0) {
            txtnama.setText(table1.getValueAt(selectedRow, 1).toString());
            txtperusahaan.setText(table1.getValueAt(selectedRow, 2).toString());
            txtnotel.setText(table1.getValueAt(selectedRow, 3).toString());
            txtalamat.setText(table1.getValueAt(selectedRow, 4).toString());
        }
    }//GEN-LAST:event_table1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelet;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private com.raven.component.Card card1;
    private com.raven.component.Card card2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table table1;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnotel;
    private javax.swing.JTextField txtperusahaan;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
