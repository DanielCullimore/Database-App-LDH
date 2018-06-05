/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.coffie.pkg723;

/**
 * @author Daniel Mensche
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;


public class MultiAccessRestriction {
    private static File file;
    private static FileChannel channel;
    private static FileLock lock;
    private String statusProgram;
    
   public MultiAccessRestriction(){
       
   }
   
   public String restrictionControl(){
    try {
            file = new File("RingOnRequest.lock");
            
            if (file.exists()) { // Check if the lock exist
                   file.delete(); // if exist try to delete it
                }   
            
            // Try to get the lock
            channel = new RandomAccessFile(file, "rw").getChannel();
            lock = channel.tryLock();
           
            if(lock == null)
               {
               try{// File is lock by other application
               channel.close();
               throw new RuntimeException(); // Only 1 instance can run."
                
               } catch(Exception RuntimeException){
                    statusProgram = "Deny Instance";
                }
                }
                
            // Add shutdown hook to release lock when application shutdown
            if(lock != null){
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
 
            //Your application tasks here..
            System.out.println("Running");
             statusProgram = "Running";
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
        }
        catch(IOException e)
        {
          try {
            throw new RuntimeException(e); //"Could not start process."
              } catch(Exception RuntimeException){
                    statusProgram = "Access Deny";       
              }
        }
    
    return statusProgram;
}
    public static void unlockFile() {
        // release and delete file lock
        try {
            if(lock != null) {
                lock.release();
                channel.close();
                file.delete();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
 
    static class ShutdownHook extends Thread {
 
        public void run() {
            unlockFile();
        }
    }
   
}
