package com.raven.form;

import com.raven.dialog.Message;
import com.raven.main.Main;
import com.raven.model.ModelCard;
import com.raven.model.ModelDataPelanggan;
import com.raven.model.ModelStudent;
import com.raven.model.ModelDataPenjualan;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import com.raven.swing.noticeboard.ModelNoticeBoard;
import com.raven.swing.table.EventAction;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class Penjualan extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();
    public Penjualan() {
        initComponents();
        loadNamaPelanggan();
        loadResep();
        loadhargaresep();
        table1.fixTable(jScrollPane1);
        setOpaque(false);
        initData();
    }

    private void initData() {
        initTableData();
        tampilkanSemuaData();
        
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
        
        // Listener untuk jumlah
        txtjumlah.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadhargaresep();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadhargaresep();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadhargaresep();
            }
        });
    }
    
    private void performSearch() {
        String keyword = txtsearch.getText().trim();
        
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Bersihkan tabel

        if (keyword.isEmpty()) {
        tampilkanSemuaData(); // Kalau kosong, tampilkan semua
        return;
        }

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT * FROM penjualan WHERE "
                       + "id_penjualan LIKE ? OR "
                       + "id_pelanggan LIKE ? OR "
                       + "tanggal LIKE ? OR "
                       + "total LIKE ? OR "
                       + "keterangan LIKE ? OR "
                       + "nama_pelanggan LIKE ? OR "
                       + "nama_resep LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 7; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                ModelDataPenjualan data = new ModelDataPenjualan(
                    rs.getInt("id_penjualan"),
                    rs.getInt("id_pelanggan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_resep"),
                    rs.getDouble("total"),
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

    private void initTableData() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "ID Pelanggan", "Nama", "Resep", "Total Harga","Tanggal", "Keterangan"}, 0
        );
        table1.setModel(model);
    }
    
    private void tampilkanSemuaData() {
    DefaultTableModel model = (DefaultTableModel) table1.getModel();
    model.setRowCount(0); // Bersihkan isi tabel

    try {
        Connection conn = konek.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM penjualan ORDER BY id_penjualan DESC");
        
        while (rs.next()) {
            ModelDataPenjualan data = new ModelDataPenjualan(
                rs.getInt("id_penjualan"),
                rs.getInt("id_pelanggan"),
                rs.getString("nama_pelanggan"),
                rs.getString("nama_resep"),
                rs.getDouble("total"),
                rs.getString("tanggal"),
                rs.getString("keterangan")
            );
            model.addRow(data.toRowTable());
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menampilkan semua data.");
    }
}
    
    private void loadNamaPelanggan() {
        try (Connection conn = konek.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nama_pelanggan FROM pelanggan")) {

            cbxpelanggan.removeAllItems();
            while (rs.next()) {
                cbxpelanggan.addItem(rs.getString("nama_pelanggan"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load pelanggan: " + e.getMessage());
        }
    }
    private boolean isLoadingResep = false;     
    private void loadResep() {
        isLoadingResep = true; // agar listener tahu sedang mengisi data
        try (Connection conn = konek.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nama_resep FROM resep")) {

            cbxresep.removeAllItems();
            while (rs.next()) {
                cbxresep.addItem(rs.getString("nama_resep"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load resep: " + e.getMessage());
        } finally {
        isLoadingResep = false;
        }
    }
    
    private void loadhargaresep() {
    try (Connection conn = konek.getConnection()) {
        String query = "SELECT harga FROM resep WHERE nama_resep = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        String resepTerpilih = cbxresep.getSelectedItem().toString();
        pst.setString(1, resepTerpilih);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            double hargaSatuan = rs.getDouble("harga");

            // Ambil jumlah dari input
            String jumlahStr = txtjumlah.getText().trim();
            int jumlah = 0;
            if (!jumlahStr.isEmpty()) {
                jumlah = Integer.parseInt(jumlahStr);
            }

            double totalHarga = hargaSatuan * jumlah;

            // Tampilkan total harga
            txtharga.setText(String.valueOf(totalHarga));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal load harga: " + e.getMessage());
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
        txtsearch = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtharga = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        cbxresep = new javax.swing.JComboBox<>();
        txtketerangan = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxpelanggan = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Penjualan");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Penjualan");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jLabel6.setText("Tanggal");

        jLabel7.setText("Total Harga");

        jLabel8.setText("Keterangan");

        jLabel9.setText("Resep");

        jLabel13.setText(":");

        jLabel14.setText(":");

        jLabel15.setText(":");

        jLabel16.setText(":");

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });

        cbxresep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxresepActionPerformed(evt);
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
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        jLabel17.setText(":");

        jLabel10.setText("Pelanggan");

        cbxpelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Jumlah");

        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });

        jLabel3.setText(":");

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
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(105, 105, 105))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(75, 75, 75)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel10))
                                                .addGap(55, 55, 55)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel3))))
                                        .addGap(50, 50, 50))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxresep, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnsave)
                                .addGap(18, 18, 18)
                                .addComponent(btnedit)
                                .addGap(18, 18, 18)
                                .addComponent(btndelete)))
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel13))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxresep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel14)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsave)
                    .addComponent(btnedit)
                    .addComponent(btndelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here: 
    }//GEN-LAST:event_txthargaActionPerformed

    private void txtketeranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtketeranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtketeranganActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        Connection conn = null;
    try {
        conn = konek.getConnection();
        conn.setAutoCommit(false); // penting agar rollback bisa jalan

        // 1. Ambil data dari form
        String namaResep = cbxresep.getSelectedItem().toString();
        String namaPelanggan = cbxpelanggan.getSelectedItem().toString();
        String hargaStr = txtharga.getText();
        String keterangan = txtketerangan.getText();
        java.util.Date utilDate = jDateChooser1.getDate();
  
        if (utilDate == null || hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal dan Harga tidak boleh kosong.");
            return;
        }

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        double harga = Double.parseDouble(hargaStr);
        
        int idPelanggan = 0;
        String sqlCariIdPelanggan = "SELECT id_pelanggan FROM pelanggan WHERE nama_pelanggan = ?";
        try (PreparedStatement pst = conn.prepareStatement(sqlCariIdPelanggan)) {
            pst.setString(1, namaPelanggan);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idPelanggan = rs.getInt("id_pelanggan");
            } else {
                JOptionPane.showMessageDialog(this, "pelanggan tidak ditemukan!");
                return;
            }
        }

        // 2. Cari id_resep dari nama_resep
        String idResep = "";
        String sqlCariId = "SELECT id_resep FROM resep WHERE nama_resep = ?";
        try (PreparedStatement pst = conn.prepareStatement(sqlCariId)) {
            pst.setString(1, namaResep);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idResep = rs.getString("id_resep");
            } else {
                JOptionPane.showMessageDialog(this, "Resep tidak ditemukan!");
                return;
            }
        }

        // 3. Simpan ke tabel penjualan dan ambil id_penjualan
        int idPenjualan = -1;
        String sqlInsert = "INSERT INTO penjualan (id_pelanggan, nama_resep, nama_pelanggan, total, tanggal, keterangan) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, idPelanggan);
            pst.setString(2, namaResep);
            pst.setString(3, namaPelanggan);
            pst.setDouble(4,harga);
            pst.setDate(5, sqlDate);
            pst.setString(6, keterangan);
            pst.executeUpdate();

            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                idPenjualan = generatedKeys.getInt(1);
            }
        }

        // 4. Update stok barang berdasarkan detail_resep
        String sqlDetail = "SELECT id_barang, jumlah FROM detail_resep WHERE id_resep = ?";
        try (PreparedStatement pst = conn.prepareStatement(sqlDetail)) {
            pst.setString(1, idResep);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String idBarang = rs.getString("id_barang");
                int jumlahPakai = rs.getInt("jumlah");

                String updateStok = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";
                try (PreparedStatement pstUpdate = conn.prepareStatement(updateStok)) {
                    pstUpdate.setInt(1, jumlahPakai);
                    pstUpdate.setString(2, idBarang);
                    pstUpdate.executeUpdate();
                }
            }
        }

        // 5. Tambahkan ke tabel keuangan dan kaitkan dengan id_penjualan
        String sqlKeuangan = "INSERT INTO keuangan (tanggal, tipe, jumlah, id_penjualan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sqlKeuangan)) {
            pst.setDate(1, sqlDate);
            pst.setString(2, "Pemasukan Harian");
            pst.setDouble(3, harga);
            //pst.setString(4, "Penjualan: " + namaResep);
            pst.setInt(4, idPenjualan);
            pst.executeUpdate();
        }

        conn.commit();
        JOptionPane.showMessageDialog(this, "Penjualan berhasil disimpan, stok dikurangi, dan data masuk ke keuangan.");

        // Tambahkan ke UI tabel jika perlu
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addRow(new Object[]{
            model.getRowCount() + 1,
            idPelanggan,
            cbxpelanggan.getSelectedItem().toString(),
            namaResep,
            harga,
            new SimpleDateFormat("yyyy-MM-dd").format(sqlDate),
            keterangan
        });

        clearForm();
    } catch (Exception e) {
        /**try {
            if (conn != null) conn.rollback(); // rollback di koneksi yang sama
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Rollback error: " + ex.getMessage());
        }**/
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        e.printStackTrace();
    } /**finally {
        try {
            if (conn != null) conn.setAutoCommit(true); // kembalikan ke default
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }**/                  
    }//GEN-LAST:event_btnsaveActionPerformed
    private void clearForm() {
        cbxresep.setSelectedIndex(0);
        //txtharga.setText("");
        cbxpelanggan.setSelectedIndex(0);
        txtketerangan.setText("");
        jDateChooser1.setDate(null);
    }
    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
    int selectedRow = table1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit di tabel terlebih dahulu.");
        return;
    }

    try {
        // Ambil nilai dari form
        String namaPelanggan = cbxpelanggan.getSelectedItem().toString();
        String namaResep = cbxresep.getSelectedItem().toString();
        String hargaStr = txtharga.getText().trim();
        String keterangan = txtketerangan.getText().trim();
        java.util.Date utilDate = jDateChooser1.getDate();

        if (utilDate == null || hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal dan Harga tidak boleh kosong.");
            return;
        }

        double harga = Double.parseDouble(hargaStr);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        // Ambil id_penjualan dari baris yang dipilih di tabel
        int idPenjualan = (int) table1.getValueAt(selectedRow, 0);

        // Update data di database
        String sql = "UPDATE penjualan SET nama_pelanggan=?, nama_resep=?, total=?, tanggal=?, keterangan=? WHERE id_penjualan=?";
        try (Connection conn = konek.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, namaPelanggan);
            pst.setString(2, namaResep);
            pst.setDouble(3, harga);
            pst.setDate(4, sqlDate);
            pst.setString(5, keterangan);
            pst.setInt(6, idPenjualan);

            int updated = pst.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate.");
                tampilkanSemuaData(); // Refresh table
                clearForm(); // Kosongkan form jika ada fungsi ini
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data.");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengedit data.");
    }
    }//GEN-LAST:event_btneditActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        int selectedRow = table1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus.");
            return;
        }

        // Ambil ID Penjualan dari kolom pertama tabel
        int idPenjualan = (int) table1.getValueAt(selectedRow, 0);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus data ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try (Connection conn = konek.getConnection()) {
                // Hapus juga dari tabel keuangan jika ada relasi
                String sqlDeleteKeuangan = "DELETE FROM keuangan WHERE id_penjualan = ?";
                try (PreparedStatement pst1 = conn.prepareStatement(sqlDeleteKeuangan)) {
                    pst1.setInt(1, idPenjualan);
                    pst1.executeUpdate();
                }

                // Hapus dari tabel penjualan
                String sqlDeletePenjualan = "DELETE FROM penjualan WHERE id_penjualan = ?";
                try (PreparedStatement pst2 = conn.prepareStatement(sqlDeletePenjualan)) {
                    pst2.setInt(1, idPenjualan);
                    int rowsAffected = pst2.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                        tampilkanSemuaData(); // Refresh tabel setelah penghapusan
                    } else {
                        JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data.");
            }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void cbxresepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxresepActionPerformed
        if (!isLoadingResep) {
        loadhargaresep();
    }
    }//GEN-LAST:event_cbxresepActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        int selectedRow = table1.getSelectedRow();

    if (selectedRow != -1) {
        try {
            // Ambil data dari tabel
            int idPenjualan = (int) table1.getValueAt(selectedRow, 0);
            int idPelanggan = (int) table1.getValueAt(selectedRow, 1);
            String namaPelanggan = (String) table1.getValueAt(selectedRow, 2);
            String namaResep = (String) table1.getValueAt(selectedRow, 3);
            double harga = (double) table1.getValueAt(selectedRow, 4);
            String tanggalStr = (String) table1.getValueAt(selectedRow, 5);
            String keterangan = (String) table1.getValueAt(selectedRow, 6);

            // Set nilai pada combo box dan field
            cbxpelanggan.setSelectedItem(namaPelanggan);
            cbxresep.setSelectedItem(namaResep);
            txtharga.setText(String.valueOf(harga));
            txtketerangan.setText(keterangan);

            // Format tanggal ke dalam java.util.Date
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date tanggal = sdf.parse(tanggalStr);
            jDateChooser1.setDate(tanggal);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menampilkan data dari tabel.");
        }
    }
    }//GEN-LAST:event_table1MouseClicked

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private javax.swing.JComboBox<String> cbxpelanggan;
    private javax.swing.JComboBox<String> cbxresep;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table table1;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtketerangan;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
