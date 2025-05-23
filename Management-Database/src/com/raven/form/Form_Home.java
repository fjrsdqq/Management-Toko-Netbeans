package com.raven.form;

import com.raven.model.ModelForm_Home;
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
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Form_Home extends javax.swing.JPanel {
com.raven.component.koneksi konek = new com.raven.component.koneksi();
    public Form_Home() {
        initComponents();
        
        tblkeuangan.fixTable(jScrollPane1);
        setOpaque(false);
        initData();
    }

    private void initData() {
        initCardData();
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
        new Object[]{"ID", "Tanggal", "Tipe", "Pemasukan Harian", "Pengluaran Harian", "Keterangan"}, 0
    );
    tblkeuangan.setModel(model);

    try {
        Connection conn = konek.getConnection();
        String sql = "SELECT * FROM keuangan WHERE " +
                     "id_keuangan LIKE ? OR " +
                     "tanggal LIKE ? OR " +
                     "tipe LIKE ? OR " +
                     "keterangan LIKE ? OR " +
                     "CAST(jumlah AS CHAR) LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 1; i <= 5; i++) {
            ps.setString(i, "%" + keyword + "%");
        }

        ResultSet rs = ps.executeQuery();
        int rowCount = 0;

        while (rs.next()) {
            rowCount++;
            String id = rs.getString("id_keuangan");
            String tanggal = rs.getString("tanggal");
            String tipe = rs.getString("tipe");
            double jumlah = rs.getDouble("jumlah");
            String keterangan = rs.getString("keterangan");

            ModelForm_Home data = new ModelForm_Home(id, tanggal, tipe, keterangan, jumlah);
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
    jComboBox1.setSelectedIndex(0);
    jDateChooser1.setDate(null);
    jTextField1.setText("");
    jTextField3.setText("");
}

    private void initTableData() {
       DefaultTableModel model = new DefaultTableModel(
    new Object[]{"ID", "Tanggal", "Tipe", "Pemasukan Harian", "Pengeluaran Harian", "Keterangan"}, 0
);
tblkeuangan.setModel(model);
// Menghapus semua baris sebelum load ulang


Connection conn = null;
PreparedStatement ps = null;
ResultSet rs = null;
    
try {   
    conn = konek.getConnection();
    String sql = "SELECT id_keuangan, tanggal, tipe, keterangan, jumlah FROM keuangan";
    ps = conn.prepareStatement(sql);
    rs = ps.executeQuery();

    while (rs.next()) {
        String id = rs.getString("id_keuangan");
        String tanggal = rs.getString("tanggal");
        String tipe = rs.getString("tipe");
        double jumlah = rs.getDouble("jumlah");
        String keterangan = rs.getString("keterangan");
      

        // Ganti dengan nama class model keuangan kamu
      ModelForm_Home data = new ModelForm_Home(id, tanggal, tipe, keterangan, jumlah);
        model.addRow(data.toRowTable());
    }

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Gagal mengambil data keuangan dari database.");
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
        card1.setData(new ModelCard("PEMASUKAN HARIAN", 5100, 20, icon1));
        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.MONETIZATION_ON, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card2.setData(new ModelCard("PENGELUARAN HARIAN", 2000, 60, icon2));
        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SHOPPING_BASKET, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card3.setData(new ModelCard("REKAP BULANAN", 3000, 80, icon3));
        Icon icon4 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BUSINESS_CENTER, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card4.setData(new ModelCard("REKAP TAHUNAN", 550, 95, icon4));
    }

    private boolean showMessage(String message) {
        Message obj = new Message(Main.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new com.raven.component.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new com.raven.component.Card();
        card3 = new com.raven.component.Card();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblkeuangan = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        card4 = new com.raven.component.Card();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        card1.setColorGradient(new java.awt.Color(211, 28, 215));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Dashboard / Home");

        card2.setBackground(new java.awt.Color(10, 30, 214));
        card2.setColorGradient(new java.awt.Color(72, 111, 252));

        card3.setBackground(new java.awt.Color(194, 85, 1));
        card3.setColorGradient(new java.awt.Color(255, 212, 99));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("DATA TRANSAKSI PEMASUKAN HARIAN & PENGLUARAN HARIAN");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblkeuangan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID","Tanggal","Tipe","PEMASUKAN HARIAN", "PENGELUARAN HARIAN", "KETERANGAN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblkeuangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblkeuanganMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblkeuangan);

        t_cari.setToolTipText("");
        t_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_cariMouseClicked(evt);
            }
        });
        t_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_cariActionPerformed(evt);
            }
        });

        btnsearch.setText("Search");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(247, 247, 247)
                .addComponent(t_cari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsearch)
                .addGap(1004, 1004, 1004))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1042, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1013, 1013, 1013))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setText("Transaksi");

        jLabel3.setText(":");

        jLabel7.setText("Tanggal");

        jLabel8.setText(":");

        card4.setBackground(new java.awt.Color(60, 195, 0));
        card4.setColorGradient(new java.awt.Color(208, 255, 90));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Keterangan");

        jLabel10.setText(":");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pemasukan Harian", "Pengeluaran Harian" }));

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

        btndelete.setText("Hapus");
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
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnedit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnsave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btndelete))
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(603, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnedit)
                    .addComponent(btnsave)
                    .addComponent(btndelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
// Ambil data dari input field
String jenisTransaksi = jComboBox1.getSelectedItem().toString();
String inputJumlah = jTextField1.getText();
String keterangan = jTextField3.getText().trim();

if (jDateChooser1.getDate() == null) {
    JOptionPane.showMessageDialog(this, "Silakan pilih tanggal terlebih dahulu.");
    return;
}

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String tanggal = sdf.format(jDateChooser1.getDate());

// Validasi field
if (jenisTransaksi.isEmpty() || tanggal.isEmpty() || inputJumlah.isEmpty() || keterangan.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
    return;
}

double jumlah;
try {
    jumlah = Double.parseDouble(inputJumlah);
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
    return;
}

try (Connection conn = konek.getConnection()) {
    String sql = "INSERT INTO keuangan (tanggal, tipe, keterangan, jumlah) VALUES (?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, tanggal);
    ps.setString(2, jenisTransaksi); // "Pemasukan Harian" atau "luaran Harian"
    ps.setString(3, keterangan);
    ps.setDouble(4, jumlah);

    int result = ps.executeUpdate();
    if (result > 0) {
        JOptionPane.showMessageDialog(null, "Data transaksi berhasil disimpan!");
        jComboBox1.setSelectedIndex(0);
        jTextField1.setText("");
        jDateChooser1.setDate(null);
        jTextField3.setText("");

        initTableData(); // Refresh tabel
    } else {
        JOptionPane.showMessageDialog(null, "Gagal menyimpan data transaksi.");
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
}

                   
    
    

        // TODO add your handling code here:
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
int selectedRow = tblkeuangan.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
    return;
}

// Ambil ID dari kolom pertama
String id = tblkeuangan.getValueAt(selectedRow, 0).toString();

int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
if (confirm != JOptionPane.YES_OPTION) {
    return;
}

try {
    Connection conn = konek.getConnection();
    String sql = "DELETE FROM keuangan WHERE id_keuangan = ?"; // GANTI di sini
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, id);
    ps.executeUpdate();
    JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");

    ps.close();
    conn.close();

    initTableData(); // refresh tabel
    resetForm();     // kosongkan form setelah penghapusan
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
}
 // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
// TODO add your handling code here:

    int selectedRow = tblkeuangan.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit terlebih dahulu.");
        return;
        
    }

    // Ambil ID dari baris tabel yang dipilih
    String id = tblkeuangan.getValueAt(selectedRow, 0).toString();

    // Ambil nilai dari form (samakan dengan tombol Save)
    String jenisTransaksi = jComboBox1.getSelectedItem().toString();
    String jumlahText = jTextField1.getText().trim();
    Date tanggalDate = jDateChooser1.getDate();
    String keterangan = jTextField3.getText().trim();

    // Validasi input
    if (jenisTransaksi.isEmpty() || jumlahText.isEmpty() || tanggalDate == null || keterangan.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
        return;
    }

    // Format tanggal ke string
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal = sdf.format(tanggalDate);

    try (Connection conn = konek.getConnection()) {
        String sql = "UPDATE keuangan SET `tipe`=?, `jumlah`=?, `tanggal`=?, `keterangan`=? WHERE `id_keuangan`=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, jenisTransaksi);
        ps.setDouble(2, Double.parseDouble(jumlahText));
        ps.setString(3, tanggal);
        ps.setString(4, keterangan);
        ps.setString(5, id);

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            resetForm();           // Kosongkan form
            initTableData();       // Refresh tabel
        } else {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang diperbarui.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        e.printStackTrace();
    }
   // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
btnsearch.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        String keyword = t_cari.getText().trim();
if (keyword.isEmpty()) {
    initTableData(); // Tampilkan semua data kalau pencarian kosong
    return;
}   
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Tanggal", "Tipe", "Pemasukan Harian", "Pengluaran Harian", "Keterangan"}, 0
        );
        tblkeuangan.setModel(model);

        try {
            Connection conn = konek.getConnection();
            String sql ="SELECT * FROM keuangan WHERE " +
                 "id_keuangan LIKE ? OR " +
                 "tanggal LIKE ? OR " +
                 "tipe LIKE ? OR " +
                 "keterangan LIKE ? OR " +
                 "CAST(jumlah AS CHAR) LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
             for (int i = 1; i <= 5; i++) {
        ps.setString(i, "%" + keyword + "%");
    }
           
            ResultSet rs = ps.executeQuery();
int rowCount = 0;

while (rs.next()) {
    rowCount++;  // Tambah hitungan hasil
    String id = rs.getString("id_keuangan");
    String tanggal = rs.getString("tanggal");
    String tipe = rs.getString("tipe");
    double jumlah = rs.getDouble("jumlah");
    String keterangan = rs.getString("keterangan");

    ModelForm_Home data = new ModelForm_Home(id, tanggal, tipe, keterangan, jumlah);
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
});

        // TODO add your handling code here:
    }//GEN-LAST:event_btnsearchActionPerformed

    private void t_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_cariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariMouseClicked

    private void tblkeuanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkeuanganMouseClicked
// Ambil data dari tabel
int i = tblkeuangan.getSelectedRow();

String id = tblkeuangan.getValueAt(i, 0).toString();
String tanggal = tblkeuangan.getValueAt(i, 1).toString();
String tipe = tblkeuangan.getValueAt(i, 2).toString();
String keterangan = tblkeuangan.getValueAt(i, 5).toString();

double jumlah = 0;
if (tipe.equals("Pemasukan Harian")) {
    jumlah = Double.parseDouble(tblkeuangan.getValueAt(i, 3).toString());
} else if (tipe.equals("Pengluaran Harian")) {
    jumlah = Double.parseDouble(tblkeuangan.getValueAt(i, 4).toString());
}

// Set nilai ke form input
try {
    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
    jDateChooser1.setDate(date);
} catch (Exception e) {
    e.printStackTrace();
}

jComboBox1.setSelectedItem(tipe);
jTextField1.setText(String.valueOf(jumlah));
jTextField3.setText(keterangan);


try {
    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
    jDateChooser1.setDate(date);
} catch (Exception e) {
    e.printStackTrace();
}



        // TODO ad your handling code here:
    }//GEN-LAST:event_tblkeuanganMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsearch;
    private com.raven.component.Card card1;
    private com.raven.component.Card card2;
    private com.raven.component.Card card3;
    private com.raven.component.Card card4;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tblkeuangan;
    // End of variables declaration//GEN-END:variables
}
