/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.component;

/**
 *
 * @author muhamadyusuf
 */
public class SesiAdmin {
    private static String u_id;
    private static String u_username;
    private static String u_nama;
    private static String u_role;
     
    public static String getU_id() {
        return u_id;
    }
 
    public static void setU_id(String u_id) {
        SesiAdmin.u_id = u_id;
    }
 
    public static String getU_username() {
        return u_username;
    }
 
    public static void setU_username(String u_username) {
        SesiAdmin.u_username = u_username;
    }
 
    public static String getU_nama() {
        return u_nama;
    }
 
    public static void setU_nama(String u_nama) {
        SesiAdmin.u_nama = u_nama;
    }
    
    public static String getU_role() {
        return u_role;
    }
 
    public static void setU_role(String u_role) {
        SesiAdmin.u_role = u_role;
    }
}
