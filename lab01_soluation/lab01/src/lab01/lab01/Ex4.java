package lab01;
// File: CM3113 Lab1 sample solution for Ex4.java 

import java.time.Duration;
import java.time.LocalTime;

public class Ex4 {
   public static final long MAX = 1000000000L ;
   public static final int NUMBEROFTASKS = 4;
   private static int tasksLeft; 
   private static LocalTime mainstart;

    public static void main(String[] args) throws InterruptedException {
            // Display results of class-level methods from thread class
    System.out.println("results of class-level methods from thread class");
    System.out.println("Minimum priority of a thread is " + Thread.MIN_PRIORITY);
    System.out.println("Maximum priority of a thread is " + Thread.MAX_PRIORITY);
    System.out.println("Normal priority of a thread is " + Thread.NORM_PRIORITY);

    System.out.println("This machine has "
    + Runtime.getRuntime().availableProcessors() + " processors available");

        mainstart = LocalTime.now();  
        System.out.println("Main thread started at " + mainstart);

        // Display results of object-level method from thread class
        System.out.println("Priority of main thread is " + Thread.currentThread().getPriority()) ;
        tasksLeft = NUMBEROFTASKS;
        
        Thread[] tasks = new Thread[NUMBEROFTASKS];
        for(int i = 0; i < NUMBEROFTASKS; i++){ 
          tasks[i] = new Thread(new Task(i));
        }

        tasks[0].setPriority(Thread.MIN_PRIORITY);
        tasks[1].setPriority(Thread.MAX_PRIORITY);


        for(Thread task: tasks){ 
          task.start(); 
        }

           
    System.out.println("results of object-level getpriority method from thread class");

    
        LocalTime finish = LocalTime.now();
        System.out.println("Main thread ended at " + finish 
                + " after running for " +  Duration.between(mainstart,finish).toMillis() + " ms")  ; 

    }

    /* What should happen, and why?
        The task with lower priority will generally take longer, 
        the task with higher priority will take less time, 
        the other threads will be somewhere in between.

        However if you run with NUMBEROFTHREADS less than your number of processors
        you might not see a difference. Try increasing NUMTHREADS. 
        The higher NUMBEROFTHREADS is the more noticeable the effect will be.
    */
  

    static class Task implements Runnable{

        private int id;
        
        public Task(int id){
          this.id = id;
        }
    
        @Override public void run(){
          LocalTime start = LocalTime.now();
          System.out.println("Task " + id + " started at " + start); 
          long sum = 0 ;
          for (long  i = 0 ; i < MAX ; i++) // this creates a time-consuming loop
             sum ++  ;
          LocalTime finish = LocalTime.now();
          System.out.println("Task "  + id + " ended at " + finish + " with sum = " + sum
                + " after running for " +  Duration.between(start, finish).toMillis() + "ms" )  ;
          tasksLeft--;
          if(tasksLeft <= 0 ){
            System.out.println("Last Task ended at " + finish
                + ". Total run time for " + NUMBEROFTASKS +  " tasks was "  
                +  Duration.between(mainstart, finish).toMillis() + "ms")  ;
          }
     
      }
    }
}


