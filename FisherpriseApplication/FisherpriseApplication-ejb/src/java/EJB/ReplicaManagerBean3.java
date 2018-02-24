/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import GossipingFaultDetection.GossipCheckThread3;
import GossipingFaultDetection.GossipSendThread3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Singleton (name = "replica3")
public class ReplicaManagerBean3 implements ReplicaManagerBeanLocal {
    public String name = "replica3";
    public final static int NUMBER_OF_REPLICAS = 5;    
    private final static String PORT = "3309";
    private final static String DB = "db";
    private final static String PASSWORD = "bastacomplicazioni";
    
    //GOSSIPING PARAMETERS    
    @EJB ( beanName = "replica2")
    public ReplicaManagerBeanLocal rmA;
    @EJB ( beanName = "replica4")
    public ReplicaManagerBeanLocal rmB;
    
    public static String[] LiveList = new String[NUMBER_OF_REPLICAS];
    public static ArrayList<ReplicaManagerBeanLocal> SubsetOfNodes;
    public static ArrayList<Integer> SubsetOfNodesNumbers;
    public static ArrayList<String> AckedNodes;
    public static long Period = 1000;
    public static int k = 2;
    private Thread t1, t2;
    public static boolean working = true;
    
    
    @PostConstruct
    void init() {
        for (int i = 0; i<LiveList.length; i++)
            LiveList[i] = "OK";
        SubsetOfNodes = new ArrayList<>();
        SubsetOfNodes.add(rmA); 
        SubsetOfNodes.add(rmB);
        SubsetOfNodesNumbers = new ArrayList<>();
        SubsetOfNodesNumbers.add(2);
        SubsetOfNodesNumbers.add(4);
        AckedNodes = new ArrayList<>();
        t1 = new Thread(new GossipSendThread3(name));
        t1.start();
        t2 = new Thread(new GossipCheckThread3(name));
        t2.start();
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    @Lock(LockType.WRITE)
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
            //System.out.println("Scritto su DB3");
            working = true;
        }
        catch (SQLException e)
        {
            //Logger.getLogger(ReplicaManagerBean3.class.getName()).log(Level.SEVERE, null, e);
            working = false;
        }
    }

    @Override
    @Lock(LockType.WRITE)
    public ArrayList<LogEntry> readFromDB(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Lock(LockType.WRITE)
    public void heartBeat(String rmName, String[] LiveList) {
        if (!AckedNodes.contains(rmName)) AckedNodes.add(rmName);
        for (int i = 0; i<this.LiveList.length; i++) {
                if (!SubsetOfNodesNumbers.contains(i+1)) 
                    this.LiveList[i] = LiveList[i];
        }
    }
}
