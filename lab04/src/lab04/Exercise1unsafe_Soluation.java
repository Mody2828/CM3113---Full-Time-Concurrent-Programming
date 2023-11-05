
/* File: Example1_unsynchronizedExercise4.java    Starting point CM3113 Lab4 Exercise 1 */

/**
 * This version of exercise 1 is unsynchronized. The version suffers from "lost
 * updates" to the shared object countShared. The effect of lost updates is
 * easily verified by running each of the CountingThread threads for a fixed
 * number of iterations and comparing the final sum of theLoopCount for each
 * thread and theTotalLoopCount
 */
import java.util.concurrent.locks.*;

public class Exercise1unsafe_Soluation {

    public static boolean running;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Counter sharedCounter = new Counter();
        CountingThread t1 = new CountingThread(counter1, sharedCounter, lock, "t1");
        CountingThread t2 = new CountingThread(counter2, sharedCounter, lock, "t2");
        t1.start();
        t2.start();

        running = true;
        while (running) { // wake up main() occasionally and test state of counters
            long count1, count2, countTotal;
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }

            /* capture state of current counts */
            count1 = counter1.getCount();
            count2 = counter2.getCount();
            countTotal = sharedCounter.getCount();

            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Total Recorded " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal));

            if (countTotal > 100000000)
                running = false;
        }
    }

    static class Counter { /* Nested class for Counter objects */

        private volatile long theCount;

        public Counter() {
            theCount = 0L;
        }

        public void increment() {
            theCount++;
        }

        public long getCount() {
            return theCount;
        }
    }

    /*
     * creating a nested class for the CountingThread - could make as
     * separate file but since CountingClass is only used in Example 1
     * saves having multiple files and naming conflicts if we make changes
     */
    static class CountingThread extends Thread {

        private Counter thisCounter;
        private Counter sharedCounter;
        private ReentrantLock lock;;

        public CountingThread(Counter counter, Counter shared, ReentrantLock lock, String name) {
            super(name);
            thisCounter = counter;
            sharedCounter = shared;

            /*
             * if CoutingThread is non-daemon then it will continue after main
             * method has finishes, and prevent program from closing
             */
            this.lock = lock;
            this.setDaemon(true);

        }

        public void run() {
            while (true) { // start of another loop
                // try {Thread.sleep(10L);} catch (InterruptedException e) {}
                try {
                    lock.lock(); // acquire ownership of the lock (or be blocked)
                    thisCounter.increment(); // count one more loop for this thread
                    sharedCounter.increment(); // count one more loop for all threads
                } finally {
                    lock.unlock();
                }

            }
        }
    }
}
