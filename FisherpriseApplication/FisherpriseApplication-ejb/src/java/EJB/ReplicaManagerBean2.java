/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Singleton (name = "replica2")
public class ReplicaManagerBean2 implements ReplicaManagerBeanLocal {
    
    private final static String PORT = "3308";
    private final static String DB = "db";
    private final static String PASSWORD = "bastacomplicazioni";

    @Override
    public void writeOnDB(LogEntry le) {
        String myQuery = "INSERT INTO LogEntries VALUES ('" + 
            le.getTimeStamp() + "', '" + 
            le.getMachineID() + "', '" +
            le.getMessageString() + "');";
        
        doQuery(myQuery);
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
            //System.out.println("Sto scrivendo sul DB");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:" + PORT + "/" + DB + "?autoReconnect=true&useSSL=false","root",PASSWORD);          

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
            Logger.getLogger(ReplicaManagerBean2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public ArrayList<LogEntry> readFromDB(int par, String parID, String parEv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
