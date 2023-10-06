package lab02;

import java.time.LocalTime;
import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
  private final long countUntil;
  private final int id;

  public MyCallable(long countUntil, int id) {
     this.countUntil = countUntil;
     this.id = id;
  }

  @Override
  public String call() throws Exception {
    long sum = 0;
    for (long i = 0; i < countUntil; i++) {
      sum += i;  //just a bit of arithmetic to give the loop something to do
    }
    return "task " + id + " completed at " + LocalTime.now() + " on thread " + Thread.currentThread().getName() + " with sum " + sum;
  }
}
