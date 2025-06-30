package com.raven.form;

import com.raven.model.ModelTepung;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Tepung extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();

    public Tepung() {
        initComponents();

        tbltepung.fixTable(jScrollPane1);
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

        if (keyword.isEmpty()) {
            initTableData(); // Menampilkan semua data jika input kosong
            return;
        }

        // ✅ Kolom header disesuaikan
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","IDS", "Nama Tepung", "Harga/kg", "Stok(kg)","Tanggal", "Keterangan"}, 0
        );
        tbltepung.setModel(model);
        

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT * FROM barang WHERE jenis_bahan='Tepung' AND " +
                         "(id_barang LIKE ? OR nama_bahan LIKE ? OR keterangan LIKE ? OR tanggal LIKE ? OR stok LIKE ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 5; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                ModelTepung data = new ModelTepung(
                     rs.getInt("id_barang"),
                    rs.getInt("id_supplier"),
                    rs.getString("nama_bahan"),
                    rs.getDouble("hargapkg"),    
                    rs.getInt("stok"),
                    rs.getString("tanggal"),
                    rs.getString("keterangan")
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

    private void resetForm() {
        txtNama.setText("");
        txtTanggal.setDate(null);
        txtStok.setText("");
        txtKeterangan.setText("");
    }

    private void initTableData() {
        DefaultTableModel model = new DefaultTableModel(
             new Object[]{"ID","IDS", "Nama Tepung", "Harga/kg", "Stok(kg)","Tanggal", "Keterangan"}, 0
        );
        tbltepung.setModel(model);

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT * FROM barang WHERE jenis_bahan='Tepung'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModelTepung data = new ModelTepung(
                     rs.getInt("id_barang"),
                    rs.getInt("id_supplier"),
                    rs.getString("nama_bahan"),
                    rs.getDouble("hargapkg"),    
                    rs.getInt("stok"),
                    rs.getString("tanggal"),
                    rs.getString("keterangan")
                );
                model.insertRow(0, data.toRowTable());
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data tepung dari database.");
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltepung = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        btnnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Tepung");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Tepung");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tbltepung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tbltepung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltepungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbltepung);

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
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Nama Tepung");

        jLabel7.setText("Tanggal");

        jLabel8.setText("Stok");

        jLabel9.setText("Keterangan");

        jLabel10.setText(":");

        jLabel11.setText(":");

        jLabel12.setText(":");

        jLabel13.setText(":");

        txtKeterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeteranganActionPerformed(evt);
            }
        });

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStokActionPerformed(evt);
            }
        });

        btnnsave.setText("Save");
        btnnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnsaveActionPerformed(evt);
            }
        });

        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        btndelete.setText("Delete");
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
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtStok, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnnsave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnedit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndelete))
                            .addComponent(txtNama)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 611, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)))
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnnsave)
                    .addComponent(btnedit)
                    .addComponent(btndelete))
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtKeteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeteranganActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStokActionPerformed

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void btnnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnsaveActionPerformed
String nama = txtNama.getText().trim();
String stokText = txtStok.getText().trim();
String keterangan = txtKeterangan.getText().trim();

if (txtTanggal.getDate() == null) {
    JOptionPane.showMessageDialog(this, "Silakan pilih tanggal terlebih dahulu.");
    return;
}

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String tanggal = sdf.format(txtTanggal.getDate());

if (nama.isEmpty() || tanggal.isEmpty() || stokText.isEmpty() || keterangan.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
    return;
}

int jumlah;
try {
    jumlah = Integer.parseInt(stokText);
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
    return;
}

try (Connection conn = konek.getConnection()) {
    String sql = "INSERT INTO barang (nama_bahan, jenis_bahan, stok, keterangan, tanggal) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, nama);
    ps.setString(2, "Tepung"); // ✅ Ganti ke 'Tepung'
    ps.setInt(3, jumlah);
    ps.setString(4, keterangan);
    ps.setString(5, tanggal);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Data tepung berhasil disimpan.");
        txtNama.setText("");
        txtStok.setText("");
        txtKeterangan.setText("");
        txtTanggal.setDate(null);
        initTableData(); // refresh tabel
    } else {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan: " + e.getMessage());
}        // TODO add your handling code here:
    }//GEN-LAST:event_btnnsaveActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed

int selectedRow = tbltepung.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit terlebih dahulu.");
    return;
}

// Ambil ID dari baris tabel yang dipilih
String id = tbltepung.getValueAt(selectedRow, 0).toString();

// Ambil nilai dari form input
String namaTepung = txtNama.getText().trim();  // ✅ Nama Tepung
Date tanggalDate = txtTanggal.getDate();
String stokText = txtStok.getText().trim();
String keterangan = txtKeterangan.getText().trim();

// Validasi input
if (namaTepung.isEmpty() || tanggalDate == null || stokText.isEmpty() || keterangan.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
    return;
}

// Format tanggal
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String tanggal = sdf.format(tanggalDate);

try (Connection conn = konek.getConnection()) {
    String sql = "UPDATE barang SET nama_bahan = ?, tanggal = ?, stok = ?, keterangan = ? WHERE id_barang = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, namaTepung); // ✅
    ps.setString(2, tanggal);
    ps.setInt(3, Integer.parseInt(stokText));
    ps.setString(4, keterangan);
    ps.setString(5, id);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Data tepung berhasil diperbarui!");
        resetForm();         // Kosongkan form input
        initTableData();     // Refresh tabel
    } else {
        JOptionPane.showMessageDialog(this, "Tidak ada data yang diperbarui.");
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
    e.printStackTrace();
} catch (NumberFormatException nfe) {
    JOptionPane.showMessageDialog(this, "Stok harus berupa angka.");
}        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
int selectedRow = tbltepung.getSelectedRow(); // ✅ ganti dari tblbumbu
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
    return;
}

// Ambil ID dari kolom pertama (id_barang)
String id = tbltepung.getValueAt(selectedRow, 0).toString();

int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
if (confirm != JOptionPane.YES_OPTION) {
    return;
}

try {
    Connection conn = konek.getConnection();
    String sql = "DELETE FROM barang WHERE id_barang = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, id);

    int affectedRows = ps.executeUpdate();
    if (affectedRows > 0) {
        JOptionPane.showMessageDialog(this, "Data tepung berhasil dihapus."); // ✅ ganti pesan
    } else {
        JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau gagal dihapus.");
    }

    ps.close();
    conn.close();

    initTableData(); // refresh tabel
    resetForm();     // kosongkan form
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Gagal menghapus data tepung."); // ✅ ganti pesan
}
        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void tbltepungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltepungMouseClicked
int i = tbltepung.getSelectedRow(); // ✅ ganti dari tblbumbu

String id = tbltepung.getValueAt(i, 0).toString(); // Jika dibutuhkan
String nama = tbltepung.getValueAt(i, 2).toString();
String tanggal = tbltepung.getValueAt(i, 5).toString();
String stokText = tbltepung.getValueAt(i, 4).toString();
String keterangan = tbltepung.getValueAt(i, 6).toString();

int stok = 0;
try {
    // Bersihkan data stok dari karakter yang tidak perlu
    stokText = stokText.replaceAll("[^\\d]", ""); // hanya angka
    stok = Integer.parseInt(stokText);
} catch (Exception e) {
    e.printStackTrace();
    stok = 0;
}

// Set nilai ke form input
txtNama.setText(nama);

try {
    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
    txtTanggal.setDate(date);
} catch (Exception e) {
    e.printStackTrace();
}

txtStok.setText(String.valueOf(stok));
txtKeterangan.setText(keterangan);
        // TODO add your handling code here:
    }//GEN-LAST:event_tbltepungMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnnsave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tbltepung;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
