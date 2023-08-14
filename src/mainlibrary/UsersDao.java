/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.MessageDigest;

/**
 *
 * @author bikash
 */
public class UsersDao {

    public static boolean validate(String name, String password) {
        boolean status = false;
        try {
            Connection con = DB.getConnection();

            // Hash the provided password using SHA-256
            String hashedInputPassword = sha256Hash(password);

            String select = "SELECT * FROM Users WHERE UserName = ? AND UserPass = ?";
            PreparedStatement ps = con.prepareStatement(select);
            ps.setString(1, name);
            ps.setString(2, hashedInputPassword);

            ResultSet rs = ps.executeQuery();
            status = rs.next();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean CheckIfAlready(String UserName) {
        boolean status = false;
        try {
            Connection con = DB.getConnection();
            String select = "select * from Users where UserName= '" + UserName +"'";
            Statement selectStatement = con.createStatement();
            ResultSet rs = selectStatement.executeQuery(select);
            status = rs.next();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;

    }

   public static int AddUser(String User, String UserPass, String UserEmail, String Date) {
        int status = 0;
        try {
            Connection con = DB.getConnection();
            
            // Hash the password using SHA-256 before saving
            String hashedPassword = sha256Hash(UserPass);

            PreparedStatement ps = con.prepareStatement("INSERT INTO Users(UserPass, RegDate, UserName, Email) VALUES(?, ?, ?, ?)");
            ps.setString(1, hashedPassword);
            ps.setString(2, Date);
            ps.setString(3, User);
            ps.setString(4, UserEmail);
            
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
    private static String sha256Hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}

