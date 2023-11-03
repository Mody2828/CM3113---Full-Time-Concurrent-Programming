package lab06;

import java.time.Duration;
import java.time.LocalTime;

public class Work implements Runnable {    
    @Override public void run() {
        LocalTime startTime = LocalTime.now() ;
        System.out.println("Starting task " + startTime) ;
        for (long  i = 1 ; i <= 40 ; i++) {
          System.out.print("x") ;
          if(i%10 == 0) System.out.print("\n") ;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {}
        }

        System.out.println("Ending task at " 
		+ Duration.between(startTime,LocalTime.now()).toMillis() + " ms")  ;
    }
}

