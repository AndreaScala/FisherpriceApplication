/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import javax.ejb.Local;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Local
public interface DataManagerBeanLocal {
    void add (LogEntry le);
    ArrayList<LogEntry> retrieve (int par, String parID, String parEv);
    
}
