package com.raven.form;

import com.raven.model.ModelBumbu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JOptionPane;

public class Bumbu extends javax.swing.JPanel {
com.raven.component.koneksi konek = new com.raven.component.koneksi();


    public Bumbu() {
        initComponents();

        tblbumbu.fixTable(jScrollPane1);
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
        new Object[]{"ID","IDS", "Nama Bumbu", "Harga/kg", "Stok(kg)","Tanggal", "Keterangan"},0
    );
    tblbumbu.setModel(model);

    try {
        Connection conn = konek.getConnection();
        String sql = "SELECT * FROM barang WHERE jenis_bahan='Bumbu' AND " +
                     "(id_barang LIKE ? OR nama_bahan LIKE ? OR keterangan LIKE ? OR tanggal LIKE ? OR stok LIKE ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 1; i <= 5; i++) {
            ps.setString(i, "%" + keyword + "%");
        }

        ResultSet rs = ps.executeQuery();
        int rowCount = 0;

        while (rs.next()) {
            rowCount++;
            ModelBumbu data = new ModelBumbu(
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
         new Object[]{"ID","IDS", "Nama Bumbu", "Harga/kg", "Stok(kg)","Tanggal", "Keterangan"}, 0
    );
    tblbumbu.setModel(model);

    try {
        Connection conn = konek.getConnection();
        String sql = "SELECT * FROM barang WHERE jenis_bahan='Bumbu'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ModelBumbu data = new ModelBumbu(
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
        JOptionPane.showMessageDialog(null, "Gagal mengambil data bumbu dari database.");
    }
}



       
    
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbumbu = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Bumbu");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Bumbu");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblbumbu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbumbuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblbumbu);
        if (tblbumbu.getColumnModel().getColumnCount() > 0) {
            tblbumbu.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        t_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_cariActionPerformed(evt);
            }
        });

        jLabel4.setText("Cari:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("Nama Bumbu");

        jLabel7.setText(":");

        jLabel8.setText("Tanggal");

        jLabel9.setText(":");

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

        jLabel2.setText("Stok");

        jLabel10.setText(":");

        jLabel3.setText("Keterangan");

        jLabel11.setText(":");

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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnsave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnedit)
                                .addGap(12, 12, 12)
                                .addComponent(btndelete))
                            .addComponent(txtStok)
                            .addComponent(txtNama)
                            .addComponent(txtKeterangan)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel3)
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
    ps.setString(2, "Bumbu"); // Ini kunci utama: set 'Bumbu'
    ps.setInt(3, jumlah);
    ps.setString(4, keterangan);
    ps.setString(5, tanggal);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Data bumbu berhasil disimpan.");
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
int selectedRow = tblbumbu.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit terlebih dahulu.");
    return;
}

// Ambil ID dari baris tabel yang dipilih
String id = tblbumbu.getValueAt(selectedRow, 0).toString();

// Ambil nilai dari form input
String namaBumbu = txtNama.getText().trim();
Date tanggalDate = txtTanggal.getDate(); // txtTanggal adalah JDateChooser
String stokText = txtStok.getText().trim();
String keterangan = txtKeterangan.getText().trim();


// Validasi input
if (namaBumbu.isEmpty() || tanggalDate == null || stokText.isEmpty() || keterangan.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
    return;
}

// Format tanggal
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String tanggal = sdf.format(tanggalDate);

try (Connection conn = konek.getConnection()) {
    String sql = "UPDATE barang SET nama_bahan = ?, tanggal = ?, stok = ?, keterangan = ? WHERE id_barang = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, namaBumbu);
    ps.setString(2, tanggal);
    ps.setInt(3, Integer.parseInt(stokText));
    ps.setString(4, keterangan);
    ps.setString(5, id);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(this, "Data bumbu berhasil diperbarui!");
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
}
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
int selectedRow = tblbumbu.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
    return;
}

// Ambil ID dari kolom pertama (id_barang)
String id = tblbumbu.getValueAt(selectedRow, 0).toString();

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
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
    } else {
        JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau gagal dihapus.");
    }

    ps.close();
    conn.close();

    initTableData(); // refresh tabel
    resetForm();     // kosongkan form
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
}

        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void tblbumbuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbumbuMouseClicked
// Ambil data dari tabel
int selectedRow = tblbumbu.getSelectedRow();

if (selectedRow != -1) {
    // Ambil data dari tabel berdasarkan index kolom
    String id = tblbumbu.getValueAt(selectedRow, 0).toString(); // Jika dibutuhkan
    String nama = tblbumbu.getValueAt(selectedRow, 2).toString();
    String tanggal = tblbumbu.getValueAt(selectedRow, 5).toString();
    String stokText = tblbumbu.getValueAt(selectedRow, 4).toString();
    String keterangan = tblbumbu.getValueAt(selectedRow, 6).toString();

    // Parsing stok dari teks ke angka
    int stok = 0;
    try {
        stokText = stokText.replaceAll("[^\\d]", ""); // hanya ambil angka
        stok = Integer.parseInt(stokText);
    } catch (Exception e) {
        e.printStackTrace();
        stok = 0;
    }

    // Tampilkan data ke input field
    txtNama.setText(nama);
    try {
        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        txtTanggal.setDate(date);
    } catch (Exception e) {
        e.printStackTrace();
    }
    txtStok.setText(String.valueOf(stok));
    txtKeterangan.setText(keterangan);
}

        // TODO add your handling code here:
    }//GEN-LAST:event_tblbumbuMouseClicked

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tblbumbu;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
