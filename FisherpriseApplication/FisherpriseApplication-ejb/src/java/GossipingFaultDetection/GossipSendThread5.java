/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GossipingFaultDetection;
import static EJB.ReplicaManagerBean5.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilario
 */
public class GossipSendThread5 implements Runnable{
    public String name;

    public GossipSendThread5(String name) {
        this.name = name;
    }
    
    
    
    @Override
    public void run() {
        while (true) {
            if (working) {

                SubsetOfNodes.forEach((rm) -> {
                    try {
                        rm.heartBeat(name, LiveList);
                    } catch(Exception ex) {
                        Logger.getLogger(GossipSendThread1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //System.out.println(name + ": ack to " + rm.getName());
                });
               
            }
            try {
                Thread.sleep(Period);
            } catch (InterruptedException ex) {
                Logger.getLogger(GossipSendThread5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
