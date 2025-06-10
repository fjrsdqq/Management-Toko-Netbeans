package com.raven.main;

import com.raven.component.Header;
import com.raven.component.Menu;
import com.raven.event.EventMenuSelected;
import com.raven.event.EventShowPopupMenu;
import com.raven.form.Pembelian;
import com.raven.form.Bumbu;
import com.raven.form.DataKaryawan;
import com.raven.form.Form_Home;
import com.raven.form.Ikan;
import com.raven.form.MainForm;
import com.raven.form.Tepung;
import com.raven.form.DataPelanggan;
import com.raven.form.DataSupplier;
import com.raven.form.Penjualan;
import com.raven.form.Keuangan;
import com.raven.swing.MenuItem;
import com.raven.swing.PopupMenu;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Main extends javax.swing.JFrame {
com.raven.component.koneksi konek = new com.raven.component.koneksi();
    private MigLayout layout;
    private Menu menu;
    private Header header;
    private MainForm main;
    private Animator animator;

    public Main() {
        initComponents();
        init();
    }

    private void init() {
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        bg.setLayout(layout);
        menu = new Menu();
        header = new Header();
        main = new MainForm();
        menu.addEvent((int menuIndex, int subMenuIndex) -> {
            System.out.println("Menu Index : " + menuIndex + " SubMenu Index " + subMenuIndex);
            switch (menuIndex) {
                case 0: // Dashboard
                    main.showForm(new Form_Home());
                    break;
                case 1: // Data Barang
                    switch (subMenuIndex) {
                        case 0:
                            main.showForm(new Bumbu());
                            break;
                        case 1:
                            main.showForm(new Ikan());
                            break;
                        case 2:
                            main.showForm(new Tepung());
                            break;
                    }
                    break;
                case 2: // Data Supplier
                    main.showForm(new DataSupplier());
                    break;
                case 3: // Data Karyawan
                    main.showForm(new DataKaryawan());  // Ganti dengan nama JForm lain yang sesuai
                    break;
                case 4: // DataPelanggan
                    main.showForm(new DataPelanggan());
                    break;
                case 5: // Transaksi
                    switch (subMenuIndex) {
                        case 0:
                            main.showForm(new Penjualan());
                            break;
                        case 1:
                            main.showForm(new Pembelian());
                            break;
                        case 2:
                            main.showForm(new Keuangan());
                            break;
                    }
                    break;
                case 6: // Laporan
                    switch (subMenuIndex) {
                        case 0:
                            lapBarang();
                            break;
                        case 1:
                            lapSupplier();
                            break;
                        case 2:
                            lapPelanggan();
                            break;
                        case 3:
                            lapKaryawan();
                            break;
                        case 4:
                            lapTransaksi();
                            break;
                    }
                    break;
                case 7: // Log Out
                    logout();
                    break;
                default:
                    System.out.println("Menu tidak dikenal");
                    break;
            }
        });
        menu.addEventShowPopup((Component com) -> {
            MenuItem item = (MenuItem) com;
            PopupMenu popup = new PopupMenu(Main.this, item.getIndex(), item.getEventSelected(), item.getMenu().getSubMenu());
            int x1 = Main.this.getX() + 52;
            int y1 = Main.this.getY() + com.getY() + 86;
            popup.setLocation(x1, y1);
            popup.setVisible(true);
        });
        menu.initMenuItem();
        bg.add(menu, "w 230!, spany 2");    // Span Y 2cell
        bg.add(header, "h 50!, wrap");
        bg.add(main, "w 100%, h 100%");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!, spany2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addMenuEvent((ActionEvent ae) -> {
            if (!animator.isRunning()) {
                animator.start();
            }
            menu.setEnableMenu(false);
            if (menu.isShowMenu()) {
                menu.hideallMenu();
            }
        });
        //  Init google icon font
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        //  Start with this form
        main.showForm(new Form_Home());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(245, 245, 245));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1354, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bg)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    private void lapBarang(){
        try {
        // Pastikan koneksi jalan
        Connection conn = konek.getConnection();  // Sesuaikan dengan class koneksi kamu

        // Lokasi file laporan .jasper
        String path = "src/Report/Lapbarang.jasper";  // Sesuaikan dengan lokasi sebenarnya

        // Jika butuh parameter, tambahkan ke sini
        HashMap<String, Object> parameter = new HashMap<>();
        // Misalnya parameter.put("id_barang", "B001");

        // Isi laporan
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);

        // Tampilkan laporan di viewer Jasper
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setTitle("Laporan Data Barang");
        viewer.setVisible(true);

        // Jika mau langsung export PDF (opsional)
         JasperExportManager.exportReportToPdfFile(print, "LaporanDataBarang.pdf");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + ex.getMessage());
        ex.printStackTrace();
    }
        
    }
    private void lapSupplier(){
        try {
        // Pastikan koneksi jalan
        Connection conn = konek.getConnection();  // Sesuaikan dengan class koneksi kamu

        // Lokasi file laporan .jasper
        String path = "src/Report/LapSupplier.jasper";  // Sesuaikan dengan lokasi sebenarnya

        // Jika butuh parameter, tambahkan ke sini
        HashMap<String, Object> parameter = new HashMap<>();
        // Misalnya parameter.put("id_barang", "B001");

        // Isi laporan
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);

        // Tampilkan laporan di viewer Jasper
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setTitle("Laporan Data Supplier");
        viewer.setVisible(true);

        // Jika mau langsung export PDF (opsional)
         JasperExportManager.exportReportToPdfFile(print, "LaporanDataSupplier.pdf");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + ex.getMessage());
        ex.printStackTrace();
    }
    }
    private void lapPelanggan(){
        try {
        // Pastikan koneksi jalan
        Connection conn = konek.getConnection();  // Sesuaikan dengan class koneksi kamu

        // Lokasi file laporan .jasper
        String path = "src/Report/LapPelanggan.jasper";  // Sesuaikan dengan lokasi sebenarnya

        // Jika butuh parameter, tambahkan ke sini
        HashMap<String, Object> parameter = new HashMap<>();
        // Misalnya parameter.put("id_barang", "B001");

        // Isi laporan
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);

        // Tampilkan laporan di viewer Jasper
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setTitle("Laporan Data Pelanggan");
        viewer.setVisible(true);

        // Jika mau langsung export PDF (opsional)
         JasperExportManager.exportReportToPdfFile(print, "LaporanDataPelanggan.pdf");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + ex.getMessage());
        ex.printStackTrace();
    }
    }
    private void lapKaryawan(){
        
    }
    private void lapTransaksi(){
        
    }
    private void logout() {
    int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        this.dispose();
        // new Login().setVisible(true); // aktifkan jika ada form login
    }
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
