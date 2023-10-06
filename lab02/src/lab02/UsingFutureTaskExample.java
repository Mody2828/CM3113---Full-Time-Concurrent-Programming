package lab02;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UsingFutureTaskExample {

  public static void main(String[] args) {
    LocalTime startTime = LocalTime.now();
    System.out.println("Main thread starts at " + startTime) ;

    Callable<String> task = new MyCallable(10000000L, 1);
    FutureTask<String> future = new FutureTask<String>(task);
    new Thread(future).start();

    String sum = "";
      try {
          sum = future.get();
      } catch (InterruptedException ex) {} 
        catch (ExecutionException ex) { }
    System.out.println(sum);



    LocalTime endTime = LocalTime.now();
    System.out.println("Main thread ends at " + endTime + " after "
            + (Duration.between(startTime, endTime).toMillis()) + " ms")  ;

  }
}
