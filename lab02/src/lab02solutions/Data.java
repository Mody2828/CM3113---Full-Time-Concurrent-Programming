package lab02solutions;

public class Data { 
  private double[] theData ;

  public Data(int size, double range) {
      theData = new double[size] ; // set size of data array
      fillData(size, range);
  }

  public void fillData(int size, double range) {      
      for (int i = 0 ; i < size ; i++) theData[i] = Math.random() * range ; 
  }
 
  public double sum() {
      System.out.println(Thread.currentThread() + " sum starts at " + System.currentTimeMillis());
      double sum = 0.0 ; 
      for (int i = 0 ; i < theData.length ; i++) 
          sum += theData[i] ; 
      System.out.println(Thread.currentThread() + " sum ends at " + System.currentTimeMillis());
      return sum ; 
  }    
} 






