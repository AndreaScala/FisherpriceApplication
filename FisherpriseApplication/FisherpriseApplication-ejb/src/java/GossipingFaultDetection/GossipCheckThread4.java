/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GossipingFaultDetection;
import static EJB.ReplicaManagerBean4.*;
import EJB.ReplicaManagerBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ilario
 */
public class GossipCheckThread4 implements Runnable{
    private int[] counters = new int[SubsetOfNodes.size()];
    public String name;

    public GossipCheckThread4(String name) {
        this.name = name;
    }
    
    
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Period);
            } catch (InterruptedException ex) {
                Logger.getLogger(GossipCheckThread4.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(working) {
    
                for (ReplicaManagerBeanLocal rm : SubsetOfNodes) {
                    if (!AckedNodes.contains(rm.getName())) {
                        String index = rm.getName().substring("replica".length());
                        int i = Integer.parseInt(index) - 1;
                        if (LiveList[i] != "FAULTY") {
                            System.out.println(name + ": "+ "SUSPECT" + " "+ rm.getName() + " " + counters[SubsetOfNodes.indexOf(rm)]);
                            counters[SubsetOfNodes.indexOf(rm)]++;
                        }
                        if (counters[SubsetOfNodes.indexOf(rm)] > k) {
                            LiveList[i] = "FAULTY";
                        }
                        
                    }
                    else {
                        String index = rm.getName().substring("replica".length());
                        int i = Integer.parseInt(index) - 1;
                        LiveList[i] = "OK";
                        if (counters[SubsetOfNodes.indexOf(rm)] > 0 && counters[SubsetOfNodes.indexOf(rm)] <=k)
                            Period+=100;
                        counters[SubsetOfNodes.indexOf(rm)] = 0;
                        //System.out.println(name + ": acked from " + rm.getName());
                    }
                }
                AckedNodes.clear();
                String LLMessage = name + " :{";
                for (int i = 0; i< LiveList.length; i++) {
                    LLMessage+=LiveList[i] + " ";
                }
                LLMessage+="}";
                System.out.println(LLMessage);
                try {
                    Thread.sleep(Period);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GossipCheckThread4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }    
}
