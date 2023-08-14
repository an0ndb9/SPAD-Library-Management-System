
/*
Create database.properties file and store the following credentials:

user=root
password=dummyPa@ssw0rd!

*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;

/**
 *
 * @author bikash
 */
public class DB {

    public static String connection = "jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false";
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("src\\mainlibrary\\database.properties");
            props.load(in);
            in.close();
            
            /*  Original Code
                
                props.put("user", user);
                //change the password to the password  ↓↓↓↓↓↓↓↓↓↓↓   you enteredwhen setting up mysql
                props.put("password", "dummyPa@ssw0rd!");
            
            */            
            // Modified Code
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            props.put("useUnicode", "true");
            props.put("useServerPrepStmts", "false"); 
            props.put("characterEncoding", "UTF-8"); 

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(connection, props);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

}
