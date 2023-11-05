package lab01;

/* Thread Creation using a specialised Thread with overridden run method 
 * The Thread class is declared in java.lang, so no need to import it */
/* There is no significance to class name MyThread, use any name you like */

public class MainAppPattern2 { 
  public static void main(String[] args) { 
    // create a MyThread object i.e. an object which can be run as a thread 
    MyThread t0 = new MyThread("t0") ; 
    // now start the concurrent execution of thread t0 
    t0.start() ; 
    // started a new thread. What is it running concurrently with? 
  }

  static class MyThread extends Thread {
 
    public MyThread(String name) { 
      super(name) ; // Thread super-class constructor to initialise thread name
    } 

    /* override inherited Thread's empty method run() 
     * the method name run() is important here; whenever a thread starts 
     * it expects to call a method with the signature public void run() */
    @Override public void run() { 
    /* put code to be executed when this thread starts in the run() method. 
     * This example just prints the thread name and a count. */ 
      String tname = Thread.currentThread().getName() ;
      int count = 0;
      for (;;) {
        System.out.println("Thread name: " + tname + " count: " + count++) ;
        try{Thread.sleep(10);}
        catch(InterruptedException e){/* ignore */}
      }  
    }
  }
} 
