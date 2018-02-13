/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import utilities.LogEntry;

/**
 *
 * @author ilario
 */
@Stateless
public class WriteBean implements WriteBeanLocal {

    @Override
    public String write(LogEntry le) {
        System.out.println("Sono dentro write!!!");
        System.out.println(le.toString());
        return le.toString();
    }
    
    @Override
    public String write(String x) {
        System.out.println(x);
        return x;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
