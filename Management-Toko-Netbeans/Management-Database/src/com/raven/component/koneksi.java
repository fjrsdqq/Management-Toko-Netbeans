/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ferdi
 */
    public class koneksi {
    public Connection conn;
    public koneksi(){}

    public Connection openkoneksi() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connURL = "jdbc:mysql://localhost:3306/DB_Penggilingan?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(connURL, user, password);
            return conn;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Tidak ada koneksi yang terbuka atau salah konfigurasi database.");
            return null;
        }
    }
    
    public void closekoneksi() throws SQLException{
        try{
            if(conn != null){
                System.out.print("Tutup Koneksi\n");
            }
        }catch(Exception ex){
        }
    } 

    public Connection getConnection() {
        try {
        if (conn == null || conn.isClosed()) {
            openkoneksi();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal membuka koneksi: " + e.getMessage());
    }
    return conn;
    }
    
}
