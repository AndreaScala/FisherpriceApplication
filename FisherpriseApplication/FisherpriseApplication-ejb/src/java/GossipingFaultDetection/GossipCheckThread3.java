/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GossipingFaultDetection;
import static EJB.ReplicaManagerBean3.*;
import EJB.ReplicaManagerBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ilario
 */
public class GossipCheckThread3 implements Runnable{
    private int[] counters = new int[SubsetOfNodes.size()];
    public String name;
    public long period;

    public GossipCheckThread3(String name) {
        this.name = name;
        this.period = Period;
    }
    
    
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(period);
            } catch (InterruptedException ex) {
                Logger.getLogger(GossipCheckThread3.class.getName()).log(Level.SEVERE, null, ex);
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
                        if (counters[SubsetOfNodes.indexOf(rm)] > 0)
                            period+=period/10;
                        counters[SubsetOfNodes.indexOf(rm)] = 0;
                        //System.out.println(name + ": acked from " + rm.getName());
                    }
                }
                AckedNodes.clear();
                String LLMessage = name + " :{";
                for (int i = 0; i< LiveList.length; i++) {
                    LLMessage+=LiveList[i] + " ";
                }
                LLMessage+="}" + " period = "+period;
                System.out.println(LLMessage);
            }
        }
    }    
}
