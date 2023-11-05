package lab01;
// File: CM3113 Lab1 sample solution for Ex5.java 

//import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;

public class Ex5{
  
  public static final long MAX = 1000000L ;
   public static final int NUMBEROFTASKS = 4;
   private static int tasksLeft; 
   private static LocalTime mainstart;

    public static void main(String[] args) {

        mainstart = LocalTime.now();  
        System.out.println("Main thread started at " + mainstart);
        tasksLeft = NUMBEROFTASKS;
        
        for(int i = 0; i < NUMBEROFTASKS; i++){ 
          Thread t = new Thread(new Task(i));
          t.start();
        }
    
        LocalTime finish = LocalTime.now();
        System.out.println("Main thread ended at " + finish 
                + " after running for " +  Duration.between(mainstart,finish).toMillis() + " ms")  ; 

    }
  

    static class Task implements Runnable{

        private int id;
        
        public Task(int id){
          this.id = id;
        }
    
        @Override public void run(){
          LocalTime start = LocalTime.now();
          System.out.println("Task " + id + " started at " + start); 
          long sum = 0 ;
          for (long  i = 0 ; i < MAX ; i++){
            sum ++  ;
            if (id == 0) Thread.yield(); // ADD THIS NEW LINE OF CODE for Ex7
          } // this creates a time-consuming loop
             
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
  

