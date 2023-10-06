package lab02solutions;

class SumRunnable implements Runnable {
  private Data theData ;
  private double theSum ;

  public SumRunnable(Data d) {
    this.theData = d ;
  }

  @Override
  public void run() {
    theSum = theData.sum() ;
  }
  
  public double getResult(){
    return theSum;
  }
}
