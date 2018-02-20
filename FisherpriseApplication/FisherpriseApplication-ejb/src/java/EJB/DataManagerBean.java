/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Singleton 
public class DataManagerBean implements DataManagerBeanLocal {

    @EJB ( beanName = "replica1")
    private ReplicaManagerBeanLocal replicaManagerBean1;
    @EJB ( beanName = "replica2")
    private ReplicaManagerBeanLocal replicaManagerBean2;
    @EJB ( beanName = "replica3")
    private ReplicaManagerBeanLocal replicaManagerBean3;
    @EJB ( beanName = "replica4")
    private ReplicaManagerBeanLocal replicaManagerBean4;
    @EJB ( beanName = "replica5")
    private ReplicaManagerBeanLocal replicaManagerBean5;

    
    @Override
    public void add(LogEntry le) {
        replicaManagerBean1.writeOnDB(le);
        System.out.println("Scritto su DB1");
        replicaManagerBean2.writeOnDB(le);
        System.out.println("Scritto su DB2");
        replicaManagerBean3.writeOnDB(le);
        System.out.println("Scritto su DB3");
        replicaManagerBean4.writeOnDB(le);
        System.out.println("Scritto su DB4");
        replicaManagerBean5.writeOnDB(le);
        System.out.println("Scritto su DB5");
    }
    
    @Override
    public ArrayList<LogEntry> retrieve (String query){
        return replicaManagerBean1.readFromDB(query);
    }
}
