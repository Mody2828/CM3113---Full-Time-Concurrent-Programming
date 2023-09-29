
public class Ex1 {
    public static void main(String[] args) {

        MyRunnable r = new MyRunnable(); /* create a MyRunnable object */
        Thread t0 = new Thread(r, "t0"); /*
                                          * create a Thread object and associate
                                          * it with the runnable object
                                          */
        t0.start(); /* starts concurrent execution of thread t0 */
        /* this has started a new thread, running concurrently with main method */
    }

    static class MyRunnable implements Runnable {
        // supply the public void run() method required by Runnable interface
        @Override
        public void run() {
            /*
             * put code to be executed when a thread starts in the run() method.
             * This example prints the thread name and a count
             */
            String tname = Thread.currentThread().getName();
            int count = 0;
            for (;;) { // empty for loop creates an infinite loop, or use while(true)
                System.out.println("This thread is " + tname + " " + count++);
                // try{Thread.sleep(10);}
                // catch(InterruptedException e){/* ignore */}
            }
        }
    }
}
