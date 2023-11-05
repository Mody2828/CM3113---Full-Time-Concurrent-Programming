package lab01;

public class Ex6 {
  
   public static final long MAX = 100000000L ;
   public static final int NUMBEROFTASKS = 4;

    public static void main(String[] args) {

      Clock clock = new Clock(10);
      clock.start();
        
      for(int i = 0; i < NUMBEROFTASKS; i++){ 
        Thread t = new Thread(new Task(i));
        t.start();
      }
    }
  

    static class Task implements Runnable{

        private int id;
        
        public Task(int id){
          this.id = id;
        }
    
        @Override public void run(){
          long sum = 0 ;
          for (long  i = 0 ; i < MAX ; i++){
            sum ++  ;
            if (sum % 1000000 == 0) System.out.println("Thread " + id + " is at " + sum);
          } // this creates a time-consuming loop but only prints once every 1000000 times
     
      }
    }
}
  

