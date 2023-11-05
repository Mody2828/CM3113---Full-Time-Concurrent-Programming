package lab01;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock extends Thread {
  private long interval ; 
  private DateTimeFormatter time_format; 

  public Clock(long time) { 
    interval = time ; 
    time_format = DateTimeFormatter.ofPattern("HH:mm:ss:SSSS");
    this.setPriority(Thread.MAX_PRIORITY) ; 
    this.setDaemon(true) ;
  } 

  @Override
  public void run() {
    while(true){ 
      System.out.println("Clock says : " + LocalTime.now().format(time_format)) ;
      try { 
        Thread.sleep(interval) ; 
      } catch (InterruptedException ex) {/* ignore exception */} 
    } 
  } 
}

