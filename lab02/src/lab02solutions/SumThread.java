package lab02solutions;

public class SumThread extends Thread {
  private Data theData ;
  private double theSum ;

  public SumThread(Data d) {
    this.theData = d ;
  }

  @Override
  public void run() {
    theSum = theData.sum() ;
  }

  public double getSum() {
    return theSum;
  }
}
