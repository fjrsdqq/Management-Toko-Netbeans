package com.raven.form;

import com.raven.model.ModelIkan; // ganti model yang sesuai
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

public class Ikan extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();

    public Ikan() {
        initComponents();

        tblikan.fixTable(jScrollPane1); // ganti nama tabel jika di-designer sebelumnya tblbumbu
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

        DefaultTableModel model = new DefaultTableModel(
             new Object[]{"ID","IDS", "Nama Ikan", "Harga/kg", "Stok(kg)","Tanggal", "Keterangan"}, 0
        );
        tblikan.setModel(model);

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT * FROM barang WHERE jenis_bahan='Ikan' AND " +
                         "(id_barang LIKE ? OR nama_bahan LIKE ? OR keterangan LIKE ? OR tanggal LIKE ? OR stok LIKE ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 5; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                ModelIkan data = new ModelIkan(
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
            new Object[]{"ID","IDS", "Nama Ikan", "Harga/kg", "Stok(kg)","Tanggal", "Keterangan"}, 0
        );
        tblikan.setModel(model);

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT * FROM barang WHERE jenis_bahan='Ikan'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModelIkan data = new ModelIkan(
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
            JOptionPane.showMessageDialog(null, "Gagal mengambil data ikan dari database.");
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblikan = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Ikan");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Ikan");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblikan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama Ikan", "Tanggal", "Harga/Kg", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblikanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblikan);
        if (tblikan.getColumnModel().getColumnCount() > 0) {
            tblikan.getColumnModel().getColumn(0).setPreferredWidth(150);
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 678, Short.MAX_VALUE)
                        .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("Nama Ikan");

        jLabel7.setText("Stok");

        jLabel8.setText("Tanggal");

        jLabel9.setText("Keterangan");

        jLabel10.setText(":");

        jLabel11.setText(":");

        jLabel12.setText(":");

        jLabel13.setText(":");

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

        txtKeterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeteranganActionPerformed(evt);
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
                    .addComponent(jLabel1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnsave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnedit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndelete))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(txtStok)
                                .addComponent(txtNama)
                                .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
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
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel11))
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsave)
                    .addComponent(btnedit)
                    .addComponent(btndelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStokActionPerformed

    private void txtKeteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeteranganActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
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
    ps.setString(2, "Ikan"); // ✅ ubah dari 'Bumbu' ke 'Ikan'
    ps.setInt(3, jumlah);
    ps.setString(4, keterangan);
    ps.setString(5, tanggal);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Data ikan berhasil disimpan.");
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
}
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
int selectedRow = tblikan.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit terlebih dahulu.");
    return;
}

// Ambil ID dari baris tabel yang dipilih
String id = tblikan.getValueAt(selectedRow, 0).toString();

// Ambil nilai dari form input
String namaIkan = txtNama.getText().trim();
Date tanggalDate = txtTanggal.getDate(); // txtTanggal adalah JDateChooser
String stokKgText = txtStok.getText().trim();
String keterangan = txtKeterangan.getText().trim();

// Validasi input
if (namaIkan.isEmpty() || tanggalDate == null || stokKgText.isEmpty() || keterangan.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
    return;
}

// Format tanggal
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String tanggal = sdf.format(tanggalDate);

try (Connection conn = konek.getConnection()) {
    String sql = "UPDATE barang SET nama_bahan = ?, tanggal = ?, stok = ?, keterangan = ? WHERE id_barang = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, namaIkan);
    ps.setString(2, tanggal);
    ps.setInt(3, Integer.parseInt(stokKgText));
    ps.setString(4, keterangan);
    ps.setString(5, id);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Data ikan berhasil diperbarui!");
        resetForm();         // Kosongkan form input
        initTableData();     // Refresh tabel
    } else {
        JOptionPane.showMessageDialog(this, "Tidak ada data yang diperbarui.");
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
    e.printStackTrace();
} catch (NumberFormatException nfe) {
    JOptionPane.showMessageDialog(this, "Stok (kg) harus berupa angka.");
}
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
int selectedRow = tblikan.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
    return;
}

// Ambil ID dari kolom pertama (id_ikan)
String id = tblikan.getValueAt(selectedRow, 0).toString();

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
        JOptionPane.showMessageDialog(this, "Data ikan berhasil dihapus.");
    } else {
        JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau gagal dihapus.");
    }

    ps.close();
    conn.close();

    initTableData(); // refresh tabel
    resetForm();     // kosongkan form
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Gagal menghapus data ikan.");
}
        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void tblikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblikanMouseClicked
// Ambil data dari tabel ikan
int i = tblikan.getSelectedRow();  // tblbumbu bisa diganti jadi tblikan jika nama tabel di UI Anda juga diubah

String id = tblikan.getValueAt(i, 0).toString(); // Jika dibutuhkan
String nama = tblikan.getValueAt(i, 2).toString();
String tanggal = tblikan.getValueAt(i, 5).toString();
String stokText = tblikan.getValueAt(i, 4).toString();
String keterangan = tblikan.getValueAt(i, 6).toString();

int stok = 0;
try {
    // Bersihkan data stok dari karakter non-angka
    stokText = stokText.replaceAll("[^\\d]", ""); // hanya angka
    stok = Integer.parseInt(stokText);
} catch (Exception e) {
    e.printStackTrace();
    stok = 0;
}

// Set nilai ke form input untuk data ikan
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
    }//GEN-LAST:event_tblikanMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
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
    private com.raven.swing.table.Table tblikan;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
