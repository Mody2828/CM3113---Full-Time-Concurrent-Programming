/* File: Exercise1_synchronized.java    
 * Sample solution to CM3113 Lab3 Exercise 1 */
package Exercise01_Soluation;

/**
 * This version of exercise 1 cures the "lost updates" problem by synchronising
 * the counting methods increment() and getCount() in the Counter class.
 * and sychronizes the increments and getCounts, so that Threads may not
 * increment when being read
 * 
 * The fix can be alternatively be confirmed by running the counting threads
 * for a fixed number of iterations and then comparing the sum of theLoopCount
 * for each thread with theTotalLoopCount for all threads, or by slowing down
 * the loop in the run method of the CountingThread
 *
 */
public class Exercise1_fullysynchronized {

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

            synchronized (sharedCounter) // commenting this out will cause errors in counts
            {
                /*
                 * synchronize all three getCount() calls in one block
                 * locked by same object MUTEX that is protecting the increments
                 * in CountingThread, so that incrementments can't occur in
                 * middle of reading the data
                 */
                count1 = counter1.getCount();
                count2 = counter2.getCount();
                countTotal = sharedCounter.getCount();

                lost = count1 + count2 - countTotal;
            }
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
                synchronized (sharedCounter)
                /*
                 * synchronizes access to increments of counters so that only one thread
                 * can access increase counters at a time
                 */
                {
                    thisCounter.increment();
                    sharedCounter.increment(); // count one more loop for all threads
                    /*
                     * however as you'll see if you run it at full speed
                     * i.e. no sleeps there are still a couple missing
                     * - so see Exercise1_fullysynchronized
                     */
                }
            }
        }

    }
}