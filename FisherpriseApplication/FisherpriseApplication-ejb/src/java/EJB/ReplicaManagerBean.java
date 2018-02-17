/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Stateless
public class ReplicaManagerBean implements ReplicaManagerBeanLocal {
    
    private final static String DB = "db1";
    private final static String PASSWORD = "bastacomplicazioni";

    @Override
    public void writeOnDB(LogEntry le) {
        //QUI SI DEVE GESTIRE LA COSA DELLE QUERY AL DATABASE
        //LA QUERY CHE DOVREBBE FARE QUESTO TIPO E'
        /*"INSERT INTO LogEntries VALUES ('" + 
            le.timestamp + "', '" + 
            le.MachineID + "', '" +
            le.messageString + "');"
        */
        //MA DEVONO ESSERE FATTE UNA ALLA VOLTA SENNO' IL COSO IMPAZZISCE
    }

    public void doQuery (String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/" + DB,"root",PASSWORD);          

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
            Logger.getLogger(ReplicaManagerBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
