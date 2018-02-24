/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import static TEST.ShareMemTest.timer;

/**
 *
 * @author ilario
 */
public class ShareMemTestThread implements Runnable{

    @Override
    public void run() {
        while(true) timer++;
    }
    
    
    
    
}
