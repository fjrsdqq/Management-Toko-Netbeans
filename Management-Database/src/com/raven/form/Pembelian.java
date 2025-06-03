package com.raven.form;

import com.raven.dialog.Message;
import com.raven.main.Main;
import java.util.Date;
import com.raven.model.ModelDataSupplier;
import com.raven.model.ModelPembelian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Pembelian extends javax.swing.JPanel {
com.raven.component.koneksi konek = new com.raven.component.koneksi();
private boolean isEditing = false;
private int selectedPembelianId = -1;
    public Pembelian() {
        initComponents();
        tblpembelian.fixTable(jScrollPane1);
        setOpaque(false);
        initData();
    }

    private void initData() {
        loadSuppliersToComboBox();
        initTableData();
    }

    private void initTableData() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Id_supp", "Nama Supplier", "Perusahaan", "Telp", "Tanggal", "total", "Keterangan"}, 0
        );
        tblpembelian.setModel(model);

        String sql = "SELECT " +
                     "p.id_pembelian, " +
                     "p.id_supplier, " +
                     "p.tanggal, " +
                     "p.total, " +
                     "p.keterangan, " +
                     "s.nama_supplier, " +
                     "s.perusahaan AS NPerusahaan, " +
                     "s.no_hp AS Notelp, " + // no_hp dari DB akan dipetakan ke kontak di ModelDataSupplier
                     "s.alamat, " + // Tambahkan alamat jika ingin disimpan di ModelDataSupplier
                     "s.keterangan AS KeteranganSupplier " + // Keterangan dari supplier
                     "FROM pembelian p " +
                     "JOIN supplier s ON p.id_supplier = s.id_supplier " +
                     "ORDER BY p.tanggal DESC";

        try (Connection conn = konek.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                ModelPembelian data = new ModelPembelian(
                    rs.getInt("id_pembelian"),
                    rs.getInt("id_supplier"), // id_supplier (int) dari pembelian
                    rs.getString("nama_supplier"),
                    rs.getString("NPerusahaan"),
                    rs.getString("Notelp"),
                    sdf.format(rs.getDate("tanggal")),
                    rs.getDouble("total"),
                    rs.getString("keterangan")
                );
                model.addRow(data.toRowTable());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data pembelian dari database: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
       }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpembelian = new com.raven.swing.table.Table();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Pembelian");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Pembelian");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblpembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpembelianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpembelian);
        if (tblpembelian.getColumnModel().getColumnCount() > 0) {
            tblpembelian.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 687, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
        );

        jLabel2.setText("Tanggal");

        jLabel3.setText("Total Harga");

        jLabel4.setText("Keterangan");

        jLabel6.setText(":");

        jLabel7.setText(":");

        jLabel8.setText(":");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel9.setText("Nama Supplier");

        jLabel10.setText(":");

        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel<>());

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
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnSave)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnDelete))
                                            .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblpembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpembelianMouseClicked
        int selectedRow = tblpembelian.getSelectedRow();

        if (selectedRow != -1) {
            isEditing = true;
            btnSave.setText("Update");

            selectedPembelianId = (int) tblpembelian.getValueAt(selectedRow, 0); // ID Pembelian

            String id_supp_str_from_table = tblpembelian.getValueAt(selectedRow, 1).toString(); // ID Supplier (String)
            String tanggalStr = tblpembelian.getValueAt(selectedRow, 5).toString(); // Tanggal
            String totalStr = tblpembelian.getValueAt(selectedRow, 6).toString(); // totalH
            String keterangan = tblpembelian.getValueAt(selectedRow, 7).toString(); // Keterangan

            // Set Tanggal ke JDateChooser
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalStr);
                txtTanggal.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Format tanggal di tabel tidak valid: " + tanggalStr, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Set Total dan Keterangan
            txtTotal.setText(totalStr);
            txtKeterangan.setText(keterangan);

            // Set cmbSupplier dan pemicu displaySelectedSupplierDetails()
            DefaultComboBoxModel<ModelDataSupplier> cmbModel = (DefaultComboBoxModel<ModelDataSupplier>) cmbSupplier.getModel();
            for (int i = 0; i < cmbModel.getSize(); i++) {
                ModelDataSupplier s = cmbModel.getElementAt(i);
                // Perbandingan id_supplier sebagai String
                if (s.getId_supplier().equals(id_supp_str_from_table)) {
                    cmbSupplier.setSelectedItem(s);
                    break;
                }
            }
            // txtPerus dan txtTelp akan otomatis terisi oleh displaySelectedSupplierDetails()
        }
    }//GEN-LAST:event_tblpembelianMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Date utilDate = txtTanggal.getDate();
        if (utilDate == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih tanggal!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String totalHargaStr = txtTotal.getText();
        String keterangan = txtKeterangan.getText();
        ModelDataSupplier selectedSupplier = (ModelDataSupplier) cmbSupplier.getSelectedItem();

        if (totalHargaStr.isEmpty() || keterangan.isEmpty() ||
            selectedSupplier == null || selectedSupplier.getId_supplier().equals("-1")) { // Periksa ID Supplier String
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua data!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double total = Double.parseDouble(totalHargaStr);
            // Konversi id_supplier dari String ke int untuk database
            int idSupplier = Integer.parseInt(selectedSupplier.getId_supplier());

            if (isEditing) {
                updatePembelian(sqlDate, total, keterangan, idSupplier);
            } else {
                insertPembelian(sqlDate, total, keterangan, idSupplier);
            }
            clearForm();
            initTableData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Total Harga harus angka.", "Error Format", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void insertPembelian(java.sql.Date tanggal, double total, String keterangan, int idSupplier) {
        String sql = "INSERT INTO pembelian (tanggal, total, keterangan, id_supplier) VALUES (?, ?, ?, ?)";
        try (Connection conn = konek.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, tanggal);
            stmt.setDouble(2, total);
            stmt.setString(3, keterangan);
            stmt.setInt(4, idSupplier);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Pembelian berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error database saat menambah: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updatePembelian(java.sql.Date tanggal, double total, String keterangan, int idSupplier) {
        String sql = "UPDATE pembelian SET tanggal=?, total=?, keterangan=?, id_supplier=? WHERE id_pembelian=?";
        try (Connection conn = konek.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, tanggal);
            stmt.setDouble(2, total);
            stmt.setString(3, keterangan);
            stmt.setInt(4, idSupplier);
            stmt.setInt(5, selectedPembelianId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Pembelian berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error database saat update: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tblpembelian.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pembelian yang ingin dihapus dari tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data pembelian ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idPembelianToDelete = (int) tblpembelian.getValueAt(selectedRow, 0);

            String sql = "DELETE FROM pembelian WHERE id_pembelian = ?";
            try (Connection conn = konek.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idPembelianToDelete);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Pembelian berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    initTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error database saat menghapus: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void clearForm() {
        txtTanggal.setDate(null); // Mengosongkan JDateChooser
        txtTotal.setText("");
        txtKeterangan.setText("");
        cmbSupplier.setSelectedIndex(0); // Kembali ke pilihan "-- Pilih Supplier --"
        isEditing = false;
        selectedPembelianId = -1;
        btnSave.setText("Save");
    }

    private void loadSuppliersToComboBox() {
        DefaultComboBoxModel<ModelDataSupplier> model = new DefaultComboBoxModel<>();
        model.addElement(new ModelDataSupplier("-1", "-- Pilih Supplier --", "", "", "", "")); // Placeholder awal
        try (Connection conn = konek.getConnection();
             // Sesuaikan query agar mengambil kolom yang sesuai dengan ModelDataSupplier Anda
             PreparedStatement stmt = conn.prepareStatement("SELECT id_supplier, nama_supplier, perusahaan, no_hp, alamat, keterangan FROM supplier");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id_supplier"); // id_supplier sebagai String
                String nama = rs.getString("nama_supplier");
                String perusahaan = rs.getString("perusahaan");
                String noHp = rs.getString("no_hp"); // kontak di ModelDataSupplier
                String alamat = rs.getString("alamat");
                String keterangan = rs.getString("keterangan");
                model.addElement(new ModelDataSupplier(id, nama, perusahaan, noHp, alamat, keterangan));
            }
            cmbSupplier.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<ModelDataSupplier> cmbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTextField jTextField3;
    private com.raven.swing.table.Table tblpembelian;
    private javax.swing.JTextField txtKeterangan;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
