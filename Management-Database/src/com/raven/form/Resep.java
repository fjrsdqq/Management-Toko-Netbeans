package com.raven.form;

import com.raven.model.ModelResep; // ganti model yang sesuai
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
import java.sql.Statement;
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

public class Resep extends javax.swing.JPanel {
    com.raven.component.koneksi konek = new com.raven.component.koneksi();

    public Resep() {
        initComponents();
        loadNamaPelanggan();
        loadTabelResep();
        tblResep.fixTable(jScrollPane1); // ganti nama tabel jika di-designer sebelumnya tblbumbu
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
            initTableData(); // Tampilkan semua data jika kosong
            return;
        }

        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nama Resep", "Nama Pelanggan","Harga", "Tanggal", "Keterangan"}, 0
        );
        tblResep.setModel(model);

        try {
            Connection conn = konek.getConnection();
            String sql = "SELECT r.id_resep, r.nama_resep, p.nama_pelanggan, r.harga, r.tanggal, r.keterangan " +
                         "FROM resep r JOIN pelanggan p ON r.id_pelanggan = p.id_pelanggan " +
                         "WHERE r.nama_resep LIKE ? OR p.nama_pelanggan LIKE ? OR r.tanggal LIKE ? OR r.keterangan LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 4; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                ModelResep data = new ModelResep(
                    rs.getInt("id_resep"),
                    rs.getString("nama_resep"),
                    rs.getString("nama_pelanggan"),
                        rs.getDouble("harga"),
                    rs.getString("tanggal"),
                    rs.getString("keterangan")
                );
                model.addRow(data.toRowTable()); // method yang mengubah model ke Object[]
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
        new Object[]{"ID", "Nama Resep", "Nama Pelanggan","Harga", "Tanggal", "Keterangan"}, 0
        );
        tblResep.setModel(model);

        try {
            Connection conn = konek.getConnection();

            String sql = "SELECT r.id_resep, r.nama_resep, p.nama_pelanggan, r.harga, r.tanggal, r.keterangan " +
                         "FROM resep r JOIN pelanggan p ON r.id_pelanggan = p.id_pelanggan";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModelResep data = new ModelResep(
                    rs.getInt("id_resep"),
                    rs.getString("nama_resep"),
                    rs.getString("nama_pelanggan"),
                    rs.getDouble("harga"),
                    rs.getString("tanggal"),
                    rs.getString("keterangan")
                );
                model.addRow(data.toRowTable()); // asumsi lo punya method toTableRow() di ModelResep
            }

        rs.close();
        ps.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal mengambil data resep dari database.");
    }
    }
    
    private void loadNamaPelanggan() {
        try (Connection conn = konek.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nama_pelanggan FROM pelanggan")) {

            cbPelanggan.removeAllItems();
            while (rs.next()) {
                cbPelanggan.addItem(rs.getString("nama_pelanggan"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load pelanggan: " + e.getMessage());
        }
    }
    
    private int getIdPelangganByNama(String nama) {
        try (Connection conn = konek.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id_pelanggan FROM pelanggan WHERE nama_pelanggan = ?")) {
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pelanggan");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil ID pelanggan: " + e.getMessage());
        }
        return -1; // gagal
    }
    
    private void simpanResep() {
    String namaResep = txtNamaResep.getText();
    String namaPelanggan = cbPelanggan.getSelectedItem() != null ? cbPelanggan.getSelectedItem().toString() : null;
    Date tanggalDate = txtTanggal.getDate();
    String deskripsi = txtKeterangan.getText();
    double hargat = 0.0;

    if (namaResep.isEmpty() || namaPelanggan == null || tanggalDate == null) {
        JOptionPane.showMessageDialog(this, "Lengkapi semua data terlebih dahulu.");
        return;
    }

    java.sql.Date tanggal = new java.sql.Date(tanggalDate.getTime());

    try (Connection conn = konek.getConnection()) {
        // Ambil id_pelanggan dari nama
        String sqlGetId = "SELECT id_pelanggan FROM pelanggan WHERE nama_pelanggan = ?";
        PreparedStatement psGetId = conn.prepareStatement(sqlGetId);
        psGetId.setString(1, namaPelanggan);
        ResultSet rs = psGetId.executeQuery();

        int idPelanggan = -1;
        if (rs.next()) {
            idPelanggan = rs.getInt("id_pelanggan");
        } else {
            JOptionPane.showMessageDialog(this, "Pelanggan tidak ditemukan.");
            return;
        }

        // Insert ke tabel resep
        String sqlInsert = "INSERT INTO resep (nama_resep, id_pelanggan, harga, tanggal, keterangan) VALUES (?, ?, ?, ?,?)";
        PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
        psInsert.setString(1, namaResep);
        psInsert.setInt(2, idPelanggan);
        psInsert.setDate(4, tanggal);
        psInsert.setDouble(3, hargat);
        psInsert.setString(5, deskripsi);

        psInsert.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data resep berhasil disimpan!");

        loadTabelResep(); // buat reload JTable
        clearForm(); // bersihkan form input

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal simpan resep: " + e.getMessage());
    }
    }
    
    private int ambilIdPelangganDariNama(String nama) {
        try (Connection conn = konek.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id_pelanggan FROM pelanggan WHERE nama = ?")) {
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pelanggan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // gagal
    }

    
    private void loadTabelResep() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Resep");
    model.addColumn("Nama Resep");
    model.addColumn("Nama Pelanggan");
    model.addColumn("Harga");
    model.addColumn("Tanggal");
    model.addColumn("Keterangan");

    try (Connection conn = konek.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(
             "SELECT r.id_resep, r.nama_resep, p.nama_pelanggan, r.harga, r.tanggal, r.keterangan " +
             "FROM resep r JOIN pelanggan p ON r.id_pelanggan = p.id_pelanggan")) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_resep"),
                rs.getString("nama_resep"),
                rs.getString("nama_pelanggan"),
                rs.getString("harga"),
                rs.getString("tanggal"),
                rs.getString("keterangan")
            });
        }
        tblResep.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal load tabel resep: " + e.getMessage());
    }
    }
    
    private void clearForm() {
    txtNamaResep.setText("");
    cbPelanggan.setSelectedIndex(0);
    txtTanggal.setDate(null);
    txtKeterangan.setText("");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResep = new com.raven.swing.table.Table();
        t_cari = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNamaResep = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        cbPelanggan = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Resep");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Data Resep");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        tblResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResepMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblResep);

        t_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_cariActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel2.setText("Cari");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(t_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("Nama Resep");

        jLabel7.setText("Tanggal");

        jLabel8.setText("Nama Pelanggan");

        jLabel9.setText("Keterangan");

        jLabel10.setText(":");

        jLabel11.setText(":");

        jLabel12.setText(":");

        jLabel13.setText(":");

        txtNamaResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaResepActionPerformed(evt);
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

        cbPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPelangganActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(65, 65, 65)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNamaResep, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtKeterangan)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnsave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnedit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndelete))
                            .addComponent(cbPelanggan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(txtNamaResep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(cbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsave)
                    .addComponent(btnedit)
                    .addComponent(btndelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaResepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaResepActionPerformed

    private void txtKeteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeteranganActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        simpanResep();
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
int selectedRow = tblResep.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data resep yang ingin diedit.");
        return;
    }

    // Ambil ID Resep dari tabel
    String idResep = tblResep.getValueAt(selectedRow, 0).toString();

    // Ambil data input
    String namaResep = txtNamaResep.getText().trim();
    String namaPelanggan = cbPelanggan.getSelectedItem().toString();
    Date tanggalDate = txtTanggal.getDate(); // JDateChooser
    String deskripsi = txtKeterangan.getText().trim();

    if (namaResep.isEmpty() || tanggalDate == null || deskripsi.isEmpty() || namaPelanggan == null) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
        return;
    }

    // Format tanggal ke SQL
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal = sdf.format(tanggalDate);

    // Ambil id_pelanggan dari nama pelanggan
    int idPelanggan = ambilIdPelangganDariNama(namaPelanggan); // fungsi buatan lo

   try (Connection conn = konek.getConnection()) {
        // Ambil ID pelanggan berdasarkan nama dari ComboBox
        String getIdSql = "SELECT id_pelanggan FROM pelanggan WHERE nama_pelanggan = ?";
        PreparedStatement psGet = conn.prepareStatement(getIdSql);
        psGet.setString(1, namaPelanggan);
        ResultSet rs = psGet.executeQuery();

        if (rs.next()) {
            idPelanggan = rs.getInt("id_pelanggan");

            String sql = "UPDATE resep SET nama_resep = ?, id_pelanggan = ?, tanggal = ?, keterangan = ? WHERE id_resep = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, namaResep);
            ps.setInt(2, idPelanggan);
            ps.setString(3, tanggal);
            ps.setString(4, deskripsi);
            ps.setString(5, idResep);

            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Data resep berhasil diperbarui!");
                clearForm();         // Kosongkan form input
                initTableData();     // Refresh tabel
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada data yang diperbarui.");
            }

            ps.close();
        } else {
            JOptionPane.showMessageDialog(this, "ID Pelanggan tidak ditemukan.");
        }

        rs.close();
        psGet.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btneditActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
    int selectedRow = tblResep.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data resep yang ingin dihapus.");
        return;
    }

    // Ambil ID resep dari kolom pertama
    String idResep = tblResep.getValueAt(selectedRow, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data resep ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try (Connection conn = konek.getConnection()) {
        String sql = "DELETE FROM resep WHERE id_resep = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idResep);

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data resep berhasil dihapus.");
        } else {
            JOptionPane.showMessageDialog(this, "Data resep tidak ditemukan atau gagal dihapus.");
        }

        ps.close();

        initTableData(); // refresh tabel
        clearForm();     // kosongkan form input
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data resep.");
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void tblResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResepMouseClicked
        int i = tblResep.getSelectedRow();
        if (i == -1) {
            return;
        }

        // Ambil nilai dari baris yang diklik
        String idResep = tblResep.getValueAt(i, 0).toString();
        String namaResep = tblResep.getValueAt(i, 1).toString();
        String namaPelanggan = tblResep.getValueAt(i, 2).toString();
        String tanggalStr = tblResep.getValueAt(i, 4).toString();
        String deskripsi = tblResep.getValueAt(i, 5).toString();

        // Set nilai ke form input
        txtNamaResep.setText(namaResep);
        cbPelanggan.setSelectedItem(namaPelanggan);
        txtKeterangan.setText(deskripsi);

        try {
            Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalStr);
            txtTanggal.setDate(tanggal);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Format tanggal tidak sesuai");
        }
    }//GEN-LAST:event_tblResepMouseClicked


    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
       try (Connection conn = konek.getConnection()) {
        String sqlGetResep = "SELECT id_resep, harga FROM resep";
        PreparedStatement psResep = conn.prepareStatement(sqlGetResep);
        ResultSet rsResep = psResep.executeQuery();

        int updatedCount = 0;

        while (rsResep.next()) {
            int idResep = rsResep.getInt("id_resep");
            double hargaLama = rsResep.getDouble("harga");

            // Hitung total harga dari detail_resep
            String sqlSum = "SELECT SUM(hargapkg) as total FROM detail_resep WHERE id_resep = ?";
            PreparedStatement psSum = conn.prepareStatement(sqlSum);
            psSum.setInt(1, idResep);
            ResultSet rsSum = psSum.executeQuery();

            if (rsSum.next()) {
                double hargaBaru = rsSum.getDouble("total");

                if (hargaBaru != hargaLama) {
                    // Update harga baru
                    String sqlUpdate = "UPDATE resep SET harga = ? WHERE id_resep = ?";
                    PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setDouble(1, hargaBaru);
                    psUpdate.setInt(2, idResep);
                    psUpdate.executeUpdate();
                    updatedCount++;
                }
            }

            rsSum.close();
            psSum.close();
        }

        rsResep.close();
        psResep.close();

        if (updatedCount > 0) {
            JOptionPane.showMessageDialog(this, updatedCount + " resep berhasil diperbarui.");
        } else {
            JOptionPane.showMessageDialog(this, "Semua harga sudah terbaru.");
        }

        initTableData(); // refresh ulang tampilan tabel jika ada
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memperbarui harga.");
    }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cbPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPelangganActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnsave;
    private javax.swing.JComboBox<String> cbPelanggan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField t_cari;
    private com.raven.swing.table.Table tblResep;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNamaResep;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
