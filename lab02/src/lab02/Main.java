package lab02;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Main {

   static Thread t1;
   static Thread t2;   
   public static void main(String[] args) throws InterruptedException { 
      t1 = new Task("T1", 100);
      t2 = new Task("T2", 200);
      displayStates("A");
      t1.start();
      displayStates("B");
      t2.start();
      for(int i = 0 ; i <= 100 ;i++){
         work(1);
         displayStates("C" + i + "ms");
      }      
      t1.join();
      displayStates("D");
      t2.join();
      displayStates("E");
   }
   
   private static void displayStates(String message){
        System.out.println("at " + message 
          + "  T1: " + t1.getState() + "  T2: " + t2.getState());
   }

   public static void work(long ms){
     LocalTime now = LocalTime.now();
     LocalTime finishTime = now.plus(ms, ChronoUnit.MILLIS);
     while(LocalTime.now().isBefore(finishTime)){
      /* waits until finish time is reached */
    };
   }

   public static synchronized void sharedWork(long ms){
     work(ms);
   }
 
   public static void randomPause(int t){
     try { Thread.sleep((int)(t*Math.random()));
     } catch (InterruptedException ex) { /* ignore */ }
   }

  static class Task extends Thread{  
    final int NLOOPS;
   
    public Task(String n, int loops){
        this.setName(n);
        this.NLOOPS = loops;
    }
   
    @Override public void run(){
      for(int i = 0; i <= NLOOPS ; i++){
          randomPause(2);
          work(1);
          // sharedWork(1);
      }
    }
  }
   



}

  

