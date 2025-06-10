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
import java.text.DecimalFormat;



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
        new Object[]{"ID", "Tanggal", "Tipe", "Pemasukan Harian", "Pengeluaran Harian", "Keterangan"}, 0
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
        model.insertRow(0, data.toRowTable());

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
        card4 = new com.raven.component.Card();

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(233, 233, 233)
                .addComponent(t_cari)
                .addGap(1096, 1096, 1096))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        card4.setBackground(new java.awt.Color(60, 195, 0));
        card4.setColorGradient(new java.awt.Color(208, 255, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(37, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void t_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_cariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariMouseClicked

    private void tblkeuanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkeuanganMouseClicked
// Ambil data dari tabel
int i = tblkeuangan.getSelectedRow();

// Ambil data dari tabel
String id = tblkeuangan.getValueAt(i, 0).toString();
String tanggal = tblkeuangan.getValueAt(i, 1).toString();
String tipe = tblkeuangan.getValueAt(i, 2).toString();
String keterangan = tblkeuangan.getValueAt(i, 5).toString();

double jumlah = 0;

try {
    String jumlahText = "";
    if (tipe.equals("Pemasukan Harian")) {
        jumlahText = tblkeuangan.getValueAt(i, 3).toString();
    } else if (tipe.equals("Pengeluaran Harian")) {
        jumlahText = tblkeuangan.getValueAt(i, 4).toString();
    }

    // Bersihkan teks dari simbol dan pemisah ribuan
    jumlahText = jumlahText.replace("Rp", "").replace(".", "").replace(",", "").replace(" ", "");
    jumlah = Double.parseDouble(jumlahText);
} catch (Exception e) {
    e.printStackTrace();
    jumlah = 0;
}

    }//GEN-LAST:event_tblkeuanganMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.Card card1;
    private com.raven.component.Card card2;
    private com.raven.component.Card card3;
    private com.raven.component.Card card4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tblkeuangan;
    // End of variables declaration//GEN-END:variables
}
