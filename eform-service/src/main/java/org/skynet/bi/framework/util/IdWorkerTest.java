package org.skynet.bi.framework.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit; 

public class IdWorkerTest { 
    public static void main(String []args){ 
        IdWorkerTest test = new IdWorkerTest(); 
        test.test2(); 
    } 

    public void test2(){ 
        final IDWorker w = new IDWorker(1,2); 
        final CyclicBarrier cdl = new CyclicBarrier(100); 

        for(int i = 0; i < 100; i++){ 
            new Thread(new Runnable() { 
                @Override 
                public void run() { 
                try { 
                    cdl.await(); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } catch (BrokenBarrierException e) { 
                    e.printStackTrace(); 
                } 
                System.out.println(w.nextId());} 
             }).start(); 
        } 
        try { 
            TimeUnit.SECONDS.sleep(5); 
        } catch (InterruptedException e) { 
           e.printStackTrace(); 
        } 

    } 
}