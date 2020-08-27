/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.scribbler.database;

/**
 *
 * @author Yeou Liu
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    private static final String DBURL = "jdbc:derby://localhost:1527/sample";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
    
    private static Connection conn = null;
    
    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            return conn;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->"
                    + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            conn.close();
        } catch (Exception ex) {
        }
    }
}
