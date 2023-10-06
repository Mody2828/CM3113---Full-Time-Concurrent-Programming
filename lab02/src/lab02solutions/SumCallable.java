package lab02solutions;
import java.util.concurrent.Callable;

class SumCallable implements Callable<Double> {
  private Data theData ;
  private double theSum ;

  public SumCallable(Data d) {
    this.theData = d ;
  }

  @Override
  public Double call() {
    theSum = theData.sum() ;
    // java compiler is smart enough to recognise need
    // to "wrap" double value as a Double object
    return theSum;
  }
}
