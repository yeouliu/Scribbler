/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.scribbler.model;

/**
 *
 * @author Yeou Liu
 */
import com.yeou.scribbler.database.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDAO {

    //*********************************USER LOGIN************************************
    public static boolean validate(String uname, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean status = false;
        String USER_LOGIN = "SELECT * FROM WBUSER WHERE USERNAME=? AND PASSWORD=?";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(USER_LOGIN);
            pstmt.setString(1, uname);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                //Login Successful, entered inputs were valid!!!
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
        } finally {
            DBConnect.close(conn);
        }
        return status;
    }

//    *************************************register Users to the application**********************************************
    public static boolean register(String uname, String pass) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean status = false;
        String INSERT_DATA = "INSERT INTO WBUSER(USERNAME, PASSWORD) VALUES(?,?)";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(INSERT_DATA);

            pstmt.setString(1, uname);
            pstmt.setString(2, pass);

            if (pstmt.executeUpdate() == 1) {
                //Registration Successful, inputs have been added to the database!!!
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Registration error -->" + ex.getMessage());
            return false;
        } finally {
            DBConnect.close(conn);
        }
        return status;
    }

    //****************************Check if the user trying to join a whiteboard session is available in the DB or not********************************
    public static boolean checkUname(String name, String pwd) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String FIND_USER = "SELECT * FROM WBUSER WHERE USERNAME = ? AND PASSWORD = ?";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(FIND_USER);
            pstmt.setString(1, name);
            pstmt.setString(2, pwd);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                //Login Successful, entered inputs were valid!!!
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
        } finally {
            DBConnect.close(conn);
        }
        return status;
    }

//    ***********************************Add whiteboard data to the database***************************************
    public static void addWbData(String wbid, String title) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean status = false;
        String INSERT_DATA = "INSERT INTO WBBOARDS(WBID, WBTITLE) VALUES(?,?)";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(INSERT_DATA);

            pstmt.setString(1, wbid);
            pstmt.setString(2, title);

            if (pstmt.executeUpdate() == 1) {
                //Registration Successful, inputs have been added to the database!!!
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());

        } finally {
            DBConnect.close(conn);
        }
//        return status;
    }

    //    ***********************************Add whiteboard and creator data to the database***************************************
    public static void saveUserBoardData(String wbid, String uname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean status = false;
        String INSERT_DATA = "INSERT INTO WBUSERBOARDS(USERNAME, WBID,SAVE) VALUES(?,?,?)";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(INSERT_DATA);

            pstmt.setString(1, uname);
            pstmt.setString(2, wbid);
            pstmt.setInt(3, 0); //value =0 when board is not sved, otherwise set value to 1 pr non zero

            if (pstmt.executeUpdate() == 1) {
                //Registration Successful, inputs have been added to the database!!!
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());

        } finally {
            DBConnect.close(conn);
        }
//        return status;
    }

//    Find the owner of the whiteboard so that a user is not added twice to the session
    public static String findOwner(String wbid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String username = "null";
        String SEARCH_OWNER = "SELECT USERNAME FROM WBUSERBOARDS WHERE WBID = ?";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(SEARCH_OWNER);
            pstmt.setString(1, wbid);
            
            ResultSet rs = pstmt.executeQuery();

             if (rs.next()) {
                //Login Successful, entered inputs were valid!!!
                username = rs.getString(username);
            }
        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());

        } finally {
            DBConnect.close(conn);
        }
        return username;
    }
    
    public static void saveWhiteboard(String uname){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String username = "null";
        String SAVE_BOARD = "UPDATE WBUSERBOARDS " +
                   "SET SAVE = 1 WHERE USERNAME = ?";
        try {
            conn = DBConnect.getConnection();
            pstmt = conn.prepareStatement(SAVE_BOARD);
            pstmt.setString(1, uname);
            
            ResultSet rs = pstmt.executeQuery();

             if (rs.next()) {
                //Login Successful, entered inputs were valid!!!
                //username = rs.getString(username);
                 System.out.println("Board Saved");
            }
        } catch (SQLException ex) {
            System.out.println("Error -->" + ex.getMessage());

        } finally {
            DBConnect.close(conn);
        }
    }
    
}
