package lab02solutions;

//import java.time.Duration;
//import java.time.LocalTime;

public class Ex5 {
  public static final int SIZE = 20000000 ; 
  public static final double RANGE = 1000.0 ; 
  
  public static void main(String[] args) {
       
    Data data1 = new Data(SIZE, RANGE) ;
    Data data2 = new Data(SIZE, RANGE) ;
    Data data3 = new Data(SIZE, RANGE) ;
    
    long startTime = System.currentTimeMillis() ; 
    SumThread t1 = new SumThread(data1) ;
    SumThread t2 = new SumThread(data2) ; 
    SumThread t3 = new SumThread(data3) ; 
    
    t1.start();
    t2.start();
    t3.start();
    
    try {
        // join will block the calling (main) thread while it
        // waits for the targetted thread to complete its run
        t1.join();
        t2.join();
        t3.join();
    } catch(InterruptedException ie) { /* should not happen */} 
    
    double sum1 = t1.getSum();
    double sum2 = t2.getSum();
    double sum3 = t3.getSum();
  
    long endTime = System.currentTimeMillis() ;
    
    System.out.println("Calculation for three sets of " + SIZE + " values took " 
            + (endTime - startTime) + " ms")  ;        
    System.out.println("Sum1 = " + sum1 
            + "\nSum2 = " + sum2 + "\nSum3 = " + sum3
    ) ;
  }
}
