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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Singleton (name = "replica1")
public class ReplicaManagerBean1 implements ReplicaManagerBeanLocal {
    
    private final static String PORT = "3307";
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
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:"+ PORT + "/" + DB + "?autoReconnect=true&useSSL=false","root",PASSWORD);          

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
            Logger.getLogger(ReplicaManagerBean1.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public ArrayList<LogEntry> readFromDB(int par, String parID, String parEv) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        }
        
        ArrayList<LogEntry> lel = new ArrayList<LogEntry>();
        
        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:"+ PORT + "/" + DB + "?autoReconnect=true&useSSL=false","root",PASSWORD);
            
            String getQuery = "";
            
            switch(par){
                case 1:
                    getQuery = "SELECT * FROM LogEntries ORDER BY timeStamp;";
                    break;
                case 2:
                    getQuery = "SELECT * FROM LogEntries WHERE messageString = '" + parEv + "' ORDER BY timeStamp;";
                    break;
                case 3:
                    getQuery = "SELECT * FROM LogEntries WHERE MachineID = '" + parID + "' ORDER BY timeStamp;";
                    break;
                case 4:
                    getQuery = "SELECT * FROM LogEntries WHERE messageString = '" + parEv + "' AND MachineID = '" + parID + "' ORDER BY timeStamp;";
                    break;
                case 5:
                    /* Under Construction*/
                    break;
                    
            }

            // create the java statement
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(getQuery);

            // iterate through the java resultset
            while (rs.next())
            {
                LogEntry le = new LogEntry(rs.getString("timeStamp") + "00",rs.getString("MachineID"),rs.getString("messageString"));
                System.out.println("Sto per aggiungere " + le);
                lel.add(le);
            }
            st.close();
            con.close();
        }
        catch (SQLException e)
        {
          System.err.println(e.getMessage());
        }
        return lel;
    }
}
