/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilario
 */
public class GenericTest {
    
    private final static String DB = "db1";
    private final static String PASSWORD = "bastacomplicazioni";

    public static void main (String[] args) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/" + DB,"root",PASSWORD);
            System.out.println("OK DB!");
            
            
            String query = "SELECT * FROM LogEntries";

            // create the java statement
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                
            }
            st.close();
        }
        catch (SQLException e)
        {
          System.err.println(e.getMessage());
        }
    }
}
