/* File: Exercise1_synchronized.java    
 * Sample solution to CM3113 Lab3 Exercise 1 */
package Exercise01_Soluation;

/**
 * This version of exercise 1 cures the "lost updates" problem by synchronising
 * the counting methods increment() and getCount() in the Counter class.
 * It does not give "correct" answers however there is still a small number of
 * lost counts,
 * but this is due to the small time difference between getCount() calls in
 * lines /## C1 , ## C2 and ## Ctot in main method that allows some increments
 * and small time difference between increment() calls in run() method of
 * CountingThread
 * 
 * A solution that synchronizes that code as well is in
 * Exercise1_fullysynchronized.java
 * 
 * The fix can be alternatively be confirmed by running the counting threads
 * for a fixed number of iterations and then comparing the sum of theLoopCount
 * for each thread with theTotalLoopCount for all threads, or by slowing down
 * the loop in the run method of the CountingThread
 *
 */
public class Exercise1_synchronized {

    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Counter sharedCounter = new Counter(); // this is the shared data

        CountingThread t1 = new CountingThread(counter1, sharedCounter, "t1");
        CountingThread t2 = new CountingThread(counter2, sharedCounter, "t2");
        t1.start();
        t2.start();

        for (;;) { // regularly print out the counters to check for lost updates
            long count1, count2, countTotal, lost;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }

            count1 = counter1.getCount(); // ##C1
            count2 = counter2.getCount(); // ##C2
            countTotal = sharedCounter.getCount(); // ##Ctot

            lost = count1 + count2 - countTotal;

            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (lost));
        }
    }

    static class Counter {
        private volatile long theCount;

        public Counter() {
            theCount = 0L;
        }

        public synchronized void increment() {
            theCount++;
        }

        public synchronized long getCount() {
            return theCount;
        }
    }

    static class CountingThread extends Thread {

        private Counter thisCounter;
        private Counter sharedCounter;

        public CountingThread(Counter counter, Counter shared, String name) {
            super(name);
            thisCounter = counter;
            sharedCounter = shared;
        }

        public void run() {
            for (;;) { // start of another loop
                /* Can slow the Thread down by uncommenting this sleep method call */
                // try {Thread.sleep(10L);} catch (InterruptedException e) {}
                thisCounter.increment();
                sharedCounter.increment(); // count one more loop for all threads

            }
        }

    }
}