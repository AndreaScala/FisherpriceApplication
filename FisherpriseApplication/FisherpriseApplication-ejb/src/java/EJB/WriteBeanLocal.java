/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Local; 
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Local
public interface WriteBeanLocal {
    public String write(LogEntry le);
    public String write(String x);
}
