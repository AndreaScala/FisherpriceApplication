/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilario
 */
public class SvuotaAllDatabases {
    public static int FIRSTPORT = 3307;
    public static int LASTPORT = 3311;
    public static String DB = "db";
    public static String PASSWORD = "bastacomplicazioni";
    
    public static void main(String[] args) {
        for (int port = FIRSTPORT; port<=LASTPORT; port++)
            doQuery("delete from LogEntries", port);
    }
    
    public static void doQuery (String query, int port) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        try{
            //System.out.println("Sto scrivendo sul DB");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:"+ port + "/" + DB + "?autoReconnect=true&useSSL=false","root",PASSWORD);          

            // create the java statement
            Statement st = con.createStatement();

            // execute the query
            int rs = st.executeUpdate(query);

            st.close();
            con.close();
            //System.out.println("Ho scritto sul DB");
        }
        catch (SQLException e)
        {
            Logger.getLogger(SvuotaAllDatabases.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
