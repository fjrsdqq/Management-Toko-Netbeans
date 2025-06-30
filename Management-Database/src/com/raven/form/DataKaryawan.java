package com.raven.form;

import com.raven.model.ModelKaryawan;
import com.raven.dialog.Message;
import com.raven.main.Main;
import com.raven.model.ModelCard;
import com.raven.model.ModelStudent;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import com.raven.swing.noticeboard.ModelNoticeBoard;
import com.raven.swing.table.EventAction;
import java.awt.Color;
import java.sql.Connection;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import com.raven.component.koneksi;
import com.raven.model.ModelIkan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DataKaryawan extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();

    public DataKaryawan() {
        initComponents();
        table1.fixTable(jScrollPane1);
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
    
    private void kosong(){
        txtnama.setText("");
        txtalamat.setText("");
        txttelp.setText("");
        cbjabatan.setSelectedItem(0);
        txtgaji.setText("");
    }
    
    private void performSearch() {
    String keyword = t_cari.getText().trim();

        if (keyword.isEmpty()) {
            initTableData(); // Tampilkan semua data jika pencarian kosong
            return;
        }

        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nama Karyawan", "Jabatan", "Gaji", "No Telpon", "Alamat"}, 0
        );
        table1.setModel(model);

        try {
            Connection conn = konek.getConnection();

            // Gunakan LIKE pada beberapa kolom yang relevan
            String sql = "SELECT * FROM karyawan WHERE " +
                         "CAST(id_karyawan AS CHAR) LIKE ? OR " +
                         "nama_karyawan LIKE ? OR " +
                         "jabatan LIKE ? OR " +
                         "no_hp LIKE ? OR " +
                         "alamat LIKE ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            // Isi parameter untuk pencarian LIKE
            for (int i = 1; i <= 5; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                ModelKaryawan data = new ModelKaryawan(
                    rs.getInt("id_karyawan"),
                    rs.getString("nama_karyawan"),
                    rs.getString("jabatan"),
                    rs.getBigDecimal("gaji"),  // Gunakan BigDecimal
                    rs.getString("no_hp"),
                    rs.getString("alamat")
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
        DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID", "Nama Karyawan", "Jabatan", "Gaji", "No Telpon", "Alamat"}, 0
            );
            table1.setModel(model);

            try {
                Connection conn = konek.getConnection();
                String sql = "SELECT * FROM karyawan";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    ModelKaryawan data = new ModelKaryawan(
                        rs.getInt("id_karyawan"),
                        rs.getString("nama_karyawan"),
                        rs.getString("jabatan"),
                        rs.getBigDecimal("gaji"), // Ubah dari getInt ke getBigDecimal
                        rs.getString("no_hp"),
                        rs.getString("alamat")
                    );
                    model.addRow(data.toRowTable());
                }

                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal mengambil data karyawan dari database: " + e.getMessage());
            }
    }
    
    private boolean showMessage(String message) {
        Message obj = new Message(Main.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        txtalamat = new javax.swing.JTextField();
        txttelp = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        cbjabatan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtgaji = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Karyawan");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Karyawan");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Alamat", "No.Telp", "Jabatan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
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
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        jLabel13.setText("Cari:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setText("Nama Karyawan");

        jLabel3.setText("Alamat Karyawan");

        jLabel4.setText("No Telp");

        jLabel6.setText("Jabatan");

        jLabel8.setText(":");

        jLabel9.setText(":");

        jLabel10.setText(":");

        jLabel11.setText(":");

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

        cbjabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Operator Penggilingan Molen", "Operator Penggilingan Kasar",
            "Operator Pemotongan Ikan", "Admin" }));

jLabel7.setText("Gaji");

jLabel12.setText(":");

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
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbjabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnsave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnedit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btndelete))
                                    .addComponent(txtgaji, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 651, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jLabel8)
                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15, 15, 15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(jLabel9)
                .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(jLabel10)
                .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20, 20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6)
                .addComponent(jLabel11)
                .addComponent(cbjabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(16, 16, 16)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(jLabel12)
                .addComponent(txtgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnsave)
                .addComponent(btnedit)
                .addComponent(btndelete))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
    String nama = txtnama.getText().trim();
    String alamat = txtalamat.getText().trim();
    String telpon = txttelp.getText().trim();
    String jabatan = cbjabatan.getSelectedItem().toString();
    String gaji = txtgaji.getText().trim();

    if (nama.isEmpty() || alamat.isEmpty() || telpon.isEmpty() || gaji.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Harap lengkapi semua data.");
        return;
    }

    String sql = "INSERT INTO karyawan (nama_karyawan, alamat, no_hp, jabatan, gaji) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = konek.getConnection()) {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nama);
        ps.setString(2, alamat);
        ps.setString(3, telpon);
        ps.setString(4, jabatan);
        ps.setInt(5, Integer.parseInt(gaji)); // pastikan gaji adalah angka

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data karyawan berhasil disimpan.");
            kosong();
            initTableData(); // refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan: " + e.getMessage());
    }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        int selectedRow = table1.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris yang akan diedit terlebih dahulu.");
                return;
            }

            // Ambil ID dari kolom pertama tabel (kolom ke-0)
            int idKaryawan = (int) table1.getValueAt(selectedRow, 0);

            // Ambil data dari input form
            String nama = txtnama.getText().trim();
            String jabatan = cbjabatan.getSelectedItem().toString();
            String gajiStr = txtgaji.getText().trim();
            String nohp = txttelp.getText().trim();
            String alamat = txtalamat.getText().trim();

            // Validasi sederhana
            if (nama.isEmpty() || jabatan.isEmpty() || gajiStr.isEmpty() || nohp.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Jika gaji bertipe DECIMAL(15,2), gunakan BigDecimal
                BigDecimal gaji = new BigDecimal(gajiStr);

                Connection conn = konek.getConnection();
                String sql = "UPDATE karyawan SET nama_karyawan=?, jabatan=?, gaji=?, no_hp=?, alamat=? WHERE id_karyawan=?";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, nama);
                ps.setString(2, jabatan);
                ps.setBigDecimal(3, gaji); // tipe data gaji decimal
                ps.setString(4, nohp);
                ps.setString(5, alamat);
                ps.setInt(6, idKaryawan); // parameter ke-6 dari WHERE

                int updated = ps.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
                    initTableData(); // refresh table
                    kosong();     // bersihkan input
                } else {
                    JOptionPane.showMessageDialog(this, "Data gagal diperbarui.");
                }

                ps.close();
                conn.close();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Format gaji tidak valid.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengupdate data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btneditActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        // TODO add your handling code here:
        int row = table1.getSelectedRow();
        if (row != -1) {
            // Ambil data dari model table
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            // Ambil setiap kolom dari baris yang diklik
            String nama = model.getValueAt(row, 1).toString();
            String jabatan = model.getValueAt(row, 2).toString();
            String gaji = model.getValueAt(row, 3).toString();
            String noHp = model.getValueAt(row, 4).toString();
            String alamat = model.getValueAt(row, 5).toString();

            // Tampilkan ke field input form
            txtnama.setText(nama);
            cbjabatan.setSelectedItem(jabatan);
            txtgaji.setText(gaji);
            txttelp.setText(noHp);
            txtalamat.setText(alamat);
        }
    }//GEN-LAST:event_table1MouseClicked

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = table1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu.");
            return;
        }

        // Ambil ID karyawan dari kolom pertama tabel (kolom 0)
        int idKaryawan = (int) table1.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus data ini?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = konek.getConnection();
                String sql = "DELETE FROM karyawan WHERE id_karyawan = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idKaryawan);

                int deleted = ps.executeUpdate();

                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                    initTableData(); // refresh tabel
                    kosong();     // bersihkan input form jika perlu
                } else {
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus.");
                }

                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private javax.swing.JComboBox<String> cbjabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private com.raven.swing.table.Table table1;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtgaji;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txttelp;
    // End of variables declaration//GEN-END:variables
}
