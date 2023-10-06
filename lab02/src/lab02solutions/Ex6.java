package lab02solutions;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Ex6 {
  // assuming that we are about to run on a quad code platform
  // a thread pool of size 4 should allow full concurrent progress for main and three sums
  private static final int NTHREADS = 3;
    
  public static final int SIZE = 20000000 ; 
  public static final double RANGE = 1000.0 ; 
  
  public static void main(String[] args) 
  {
    double sum1, sum2, sum3 ; // to collect results
    
    Data data1 = new Data(SIZE, RANGE) ;
    Data data2 = new Data(SIZE, RANGE) ;
    Data data3 = new Data(SIZE, RANGE) ;
    
    LocalTime startTime = LocalTime.now();
    System.out.println("Main thread starts at " + startTime) ;
    
    // use executor service to manage threads
    ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

    Callable<Double> task1 = new SumCallable(data1);
    Future<Double> future1 = executor.submit(task1);
    Callable<Double> task2 = new SumCallable(data2);
    Future<Double> future2 = executor.submit(task2);
    Callable<Double> task3 = new SumCallable(data3);
    Future<Double> future3 = executor.submit(task3);
    
    // now retrieve the result
    try {
      sum1 = future1.get();
      sum2 = future2.get();
      sum3 = future3.get();
      System.out.println("sum1 = " + sum1);
      System.out.println("sum2 = " + sum2);
      System.out.println("sum3 = " + sum3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    LocalTime endTime = LocalTime.now();
    System.out.println("Main thread ends calculation at " + endTime +
            " after " + Duration.between(startTime, endTime).toMillis() + " milliseconds")  ;
    
    executor.shutdown();
    // Wait until all threads are finished
    try {
        if (!executor.awaitTermination(60, TimeUnit.SECONDS)){
            // we have waited long enough
            // just shutdown all threads now
            executor.shutdownNow();
            System.out.println("I am not going to wait any longer");
        }
    } catch(InterruptedException ie){
        executor.shutdownNow();
        System.out.println("I've been interrupted!!");
    }
  }
}

