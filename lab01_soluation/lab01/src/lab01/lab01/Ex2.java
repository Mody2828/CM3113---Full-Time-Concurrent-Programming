package lab01;
// File: CM3113 Lab1 sample solution for Ex2.java 

public class Ex2 { 
  public static void main(String[] args) {
      
    // create three instances of the MyThread class
    MyThread t1 = new MyThread ("t1") ; 
    MyThread t2 = new MyThread ("t2") ; 
    MyThread t3 = new MyThread ("t3") ;
    
    /* uncomment for Ex 2 parts (d) and (e) */ 
    // t1.setDaemon(true);
    // t2.setDaemon(true);
    // t3.setDaemon(true);
    
    // start the three threads
    t1.start() ; 
    t2.start() ; 
    t3.start() ; 

      // "run" the three threads - uncomment for Task 2(f)
    // t1.run() ; 
    // t2.run() ; 
    // t3.run() ; 
           
    /* uncomment for Ex 2 parts (d), (e) and (f) */ 
   try {
         Thread.sleep(1000L);
     } catch (InterruptedException ex) {
     }
System.out.println("sleep over");
      /* uncomment for Ex 2 part (e) */ 
   t1.interrupt() ; 
   t2.interrupt() ;
   t3.interrupt() ;
  } 

  /* Thread Creation using a specialised Thread with overridden run method 
   * The Thread class is declared in java.lang, so no need to import it */
  /* There is no significance to class name MyThread, use any name you like */
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
      // while (true) {
      while (!interrupted()) {
        System.out.println("Thread name: " + tname + " count: " + count++ ) ;
        // try{Thread.sleep(1);}
        // catch(InterruptedException e){/* ignore */}
      }  
    }
}


} 

