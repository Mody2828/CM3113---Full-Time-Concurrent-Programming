package Exercise04_Soluation;

/**
 * This version of exercise 4 cures the "lost updates" problem for sharedCount
 * of CountingThread in same way as Exercise4_synchronized.
 *
 * It then tries to make reading t1 and t2 counts and totalCount into an atomic
 * operation. It succeeds to the extent that most of the discrepancy is removed,
 * but if CPU is fast enough there will still be some Lost recorded due to tiny,
 * but finite time between increaseThisCount() and increaseSharedCount() in
 * run()
 * of CountingThread
 * 
 * We could synchronize increasethisCount on the class rather than the
 * individual
 * threads
 */
public class Example4_fullysynchronized {

    public static void main(String[] args) {
        // long count1, count2;
        long countTotal = 0;
        CountingThread t1 = new CountingThread("t1");
        CountingThread t2 = new CountingThread("t2");
        t1.start();
        t2.start();
        while (countTotal < 1000000000L) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            // count1 = t1.getThisCount();
            // count2 = t2.getThisCount();
            // countTotal = CountingThread.getSharedCount();
            // System.out.println("Actual C1 + C2: " + (count1 + count2)
            // + ", Recorded C1 + C2 " + countTotal
            // + ", Lost: " + (count1 + count2 - countTotal));
            /* refactor so that reading and displaying counts become an atomic operation */
            System.out.println(CountingThread.getCountInfo(t1, t2));
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

        /* synchronizing all methods on the Class object would make Lost=0 */
        public void increaseThisCount() {
            synchronized (CountingThread.class) {
                thisCount++;
            }
        }

        public long getThisCount() {
            synchronized (CountingThread.class) {
                return thisCount;
            }
        }

        /*
         * could try syncronizing the object-level methods so that main can't read
         * thisCount while this Thread increments thisCount
         * this seems to a small but constant loss
         */
        // public synchronized void increaseThisCount() {
        // thisCount++;
        // }

        // public synchronized long getThisCount() {
        // return thisCount;
        // }

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

        public static synchronized String getCountInfo(CountingThread t1, CountingThread t2) {
            long count1 = t1.getThisCount(); // C1
            long count2 = t2.getThisCount(); // C2

            long countTotal = CountingThread.getSharedCount(); // CTot
            return "Actual C1 + C2: " + (count1 + count2)
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal);
        }
    }

}
