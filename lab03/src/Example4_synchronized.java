// I introduced a static variable countTotal that will be used to keep track of the total count.
// I added synchronized blocks when reading count1, count2, and updating countTotal. These synchronized blocks ensure that only one thread can access these variables at a time
public class Example4_synchronized {
    private static long countTotal = 0;

    public static void main(String[] args) {
        long count1, count2;
        CountingThread t1 = new CountingThread("t1");
        CountingThread t2 = new CountingThread("t2");
        t1.start();
        t2.start();
        while (countTotal < 1000000000L) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (CountingThread.class) {
                count1 = t1.getThisCount();
                count2 = t2.getThisCount();
                System.out.println("Actual C1 + C2: " + (count1 + count2)
                        + ", Recorded C1 + C2 " + countTotal
                        + ", Lost: " + (count1 + count2 - countTotal));
            }
        }
    }

    /* Nested class defining our CountingThread thread */
    static class CountingThread extends Thread {
        private static long sharedCount = 0L;
        private long thisCount;

        public CountingThread(String name) {
            super(name);
            thisCount = 0L;
            this.setDaemon(true); // so that Thread closes if main closes
        }

        public void run() {
            for (;;) { // can slow down by uncommenting the sleep statement
                // try {Thread.sleep(10L);} catch (InterruptedException e) {}
                increaseThisCount();
                synchronized (CountingThread.class) {
                    increaseSharedCount();
                    countTotal = sharedCount;
                }
            }
        }

        public void increaseThisCount() {
            thisCount++;
        }

        public static synchronized void increaseSharedCount() {
            sharedCount++;
        }

        public long getThisCount() {
            return thisCount;
        }
    }
}
