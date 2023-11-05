package lab01;
// File: CM3113 Lab1 sample solution for Ex1.java  

public class Ex1 { 
  public static void main(String[] args) {
      
    // create a Runnable object based on MyRunnable class
    MyRunnable r = new MyRunnable(); 
    
    // pass the Runnable object into three distinct threads
    Thread t1 = new Thread (r, "t1") ; 
    Thread t2 = new Thread (r, "t2") ; 
    Thread t3 = new Thread (r, "t3") ;
    
    // start the three threads
    t1.start() ; 
    t2.start() ; 
    t3.start() ;
    
    // this code will run forever! Use ctrl+c to stop the process

  }
  
  static class MyRunnable implements Runnable { 
    
    // supply the public void run() method required by Runnable interface
    @Override public void run() { 
     /* put code to be executed when a thread starts in the run() method. 
      * This example prints the thread name and a count */ 
      String tname = Thread.currentThread().getName() ;
      int count = 0;
      for (;;) { // empty for loop creates an infinite loop, or use while(true)
        System.out.println("This thread is " + tname + " " + count++) ;
        try{Thread.sleep(10);}
        catch(InterruptedException e){/* ignore */}
      } 
    } 
  }

} 

