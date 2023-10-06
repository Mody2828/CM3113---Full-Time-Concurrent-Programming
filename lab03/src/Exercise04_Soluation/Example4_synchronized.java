package Exercise04_Soluation;

/**
 * This version of exercise 4 cures the "lost updates" problem for sharedCount
 * of CountingThread by synchronizing the counting methods increaseSharedCount()
 * and getSharedCount() in the that affect the shared data.
 *
 * However when the for-loop in the run() method in the thread is running at
 * unconstrained speed there might still be a difference in the actual and
 * recorded
 * totals because of the time passing between lines marked C1, C2 and CTot in
 * main().
 *
 * However the fix can be confirmed. by slowing down the run() method in the
 * COuntingThread (uncomment the Thread.sleep method in the run method).
 * An alternative is to ensure that no threads can proceed while lines C1 to
 * CTot are being executed (see Example4_fullysynchronized)
 */
public class Example4_synchronized {

    public static void main(String[] args) {
        long count1, count2, countTotal = 0;
        CountingThread t1 = new CountingThread("t1");
        CountingThread t2 = new CountingThread("t2");
        t1.start();
        t2.start();
        while (countTotal < 1000000000L) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            /* between here ... */
            count1 = t1.getThisCount();
            count2 = t2.getThisCount();
            countTotal = CountingThread.getSharedCount();
            /*
             * ... and here the counts can change, so can still get a difference even when
             * access to sharedCount is thread-safe
             */
            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal));
        }
    }

    static class CountingThread extends Thread {

        private static long sharedCount = 0L;
        private long thisCount;

        public CountingThread(String name) {
            super(name);
            thisCount = 0L;
            this.setDaemon(true);
        }

        public void run() {
            for (;;) {
                // try { Thread.sleep(1);} catch (InterruptedException e) {}
                /*
                 * uncomment to slow down how the updates run so that they don't
                 * increment between the counts being read in the main method
                 */
                increaseThisCount();
                increaseSharedCount();
            }
        }

        /*
         * try syncronizing the object-level methods so that main can't read
         * thisCount while his Thread increments thisCount
         */
        public synchronized void increaseThisCount() {
            thisCount++;
        }

        public synchronized long getThisCount() {
            return thisCount;
        }

        /*
         * synchronizing the class-level (i.e. static) methods ensures that
         * individual threads cannot increment sharedCount concurrently
         */
        public synchronized static void increaseSharedCount() {
            sharedCount++;
        }

        public synchronized static long getSharedCount() {
            return sharedCount;
        }
    }

}
