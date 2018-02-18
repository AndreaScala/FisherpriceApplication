/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Singleton
public class SendToDBManager implements SendToDBManagerLocal {

    @EJB
    private ReplicaManagerBeanLocal replicaManagerBean;

    
    @Override
    public void add(LogEntry le) {
        replicaManagerBean.writeOnDB(le);
    }
    
}
