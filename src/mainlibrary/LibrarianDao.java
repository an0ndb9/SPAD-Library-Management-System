package mainlibrary;

import java.sql.*;
import java.security.MessageDigest;

public class LibrarianDao {

    public static int save(String name, String password, String email, String address, String city, String contact) {
        int status = 0;
        
        try {

            Connection con = DB.getConnection();
            String hashedInputPassword = sha256Hash(password);
            PreparedStatement ps = con.prepareStatement("insert into librarian(name,password,email,address,city,contact) values(?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, hashedInputPassword);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setString(5, city);
            ps.setString(6, contact);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int delete(int id) {
        int status = 0;
        try {
            Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from Librarian where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean validate(String name, String password) {
        boolean status = false;
        try {
            Connection con = DB.getConnection();
            String hashedInputPassword = sha256Hash(password);
            PreparedStatement ps=con.prepareStatement("select * from Librarian where UserName=? and Password=?");
            ps.setString(1,name);
            ps.setString(2,hashedInputPassword);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;s
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
