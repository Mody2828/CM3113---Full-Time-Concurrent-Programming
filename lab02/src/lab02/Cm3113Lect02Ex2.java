package lab02;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Use low-level threading i.e. create as many threads as we can 
 * hope will run and test for termination using Thread.join()
 */
public class Cm3113Lect02Ex2 {

  public static void main(String[] args) {

    int NTHREADS = 100;
    long COUNTUNTIL = 400000000L;

    LocalTime startTime = LocalTime.now();
    System.out.println("Main thread starts at " + startTime) ;

    // store thread references so that we can check if they are done
    List<Thread> threads = new ArrayList<Thread>();
    
    for (int i = 0; i < NTHREADS; i++) {
      Runnable task = new MyRunnable(COUNTUNTIL, i);
      Thread worker = new Thread(task);
      worker.setName(String.valueOf(i));    //set the name of the thread
      worker.start();                       // Start the thread
      threads.add(worker);                  // Remember thread for later usage 
    }

    // main method will simply wait at the join() method for other threads to complete
    for (Thread thread : threads) {
      try {
        thread.join(); // main waits for the thread to terminate
      } catch(InterruptedException ie){}
    }

    System.out.println("All threads finished");
    
    LocalTime endTime = LocalTime.now();
    System.out.println("Main thread ends at " + endTime + " after " 
            + (Duration.between(startTime, endTime).toMillis()) + " ms")  ;     

  }
    
}
