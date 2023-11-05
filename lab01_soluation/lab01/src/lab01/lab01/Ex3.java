package lab01;
// File: CM3113 Lab1 sample solution for Ex3.java 

import java.time.Duration;
import java.time.LocalTime;

public class Ex3 {

   public static final long MAX = 1000000000L ;
   public static final int NUMBEROFTASKS = 4;
   private static int tasksLeft; 
   private static LocalTime mainstart;

    public static void main(String[] args) throws InterruptedException {
       
        mainstart = LocalTime.now();  
        System.out.println("Main thread started at " + mainstart);
        tasksLeft = NUMBEROFTASKS;
        
        Thread[] tasks = new Thread[NUMBEROFTASKS];
        for(int i = 0; i < NUMBEROFTASKS; i++){ 
          //task(i) ; // replacing the call of task() method with creation of Task thread 
          tasks[i] = new Thread(new Task(i));
          // tasks[i].setDaemon(true);
          /* making the threads daemon threads means they will shut-down  
         * as soon as the main thread completes, */

        }

        for(Thread task: tasks){ 
          task.start(); 
         //task.run();
          /* using run() rather than start() simply calls the run() method on the current thread
        
          * using start() submits the task to a thread, gives it its own stack space, 
          * and then starts run() on that new Thread */

          /* using run() would be bad! run() will simply run the task here on the main thread */
        }
    
        LocalTime finish = LocalTime.now();
        System.out.println("Main thread ended at " + finish 
                + " after running for " +  Duration.between(mainstart,finish).toMillis() + " ms")  ; 

        /* in my solutions Task implents Runnable rather than extends Thread */
    }
    
    /* What should happen, and why?
       The main thread will now finish very quickly, as its only job was to start the other threads.
       The other threads run side-by-side perhaps a little slower than the 
       original task did in ThreeTasks.java, because there is extra processing
       involved in setting up the Thread objects that run the tasks.
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
