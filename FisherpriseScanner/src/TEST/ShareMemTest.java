/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

/**
 *
 * @author ilario
 */
public class ShareMemTest {
    public static float timer=0;
    
    public static void main (String[] args) {
        Thread t1 = new Thread (new ShareMemTestThread ());
        t1.start();
        int i = 0;
        while(i != 10000000) i++;
        System.out.println(timer);
    }
    
}
