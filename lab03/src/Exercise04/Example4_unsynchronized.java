package Exercise04;

public class Example4_unsynchronized {
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
            count1 = t1.getThisCount();
            count2 = t2.getThisCount();
            countTotal = CountingThread.getSharedCount();
            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal));
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
                increaseSharedCount();
            }
        }

        public void increaseThisCount() {
            thisCount++;
        }

        public static void increaseSharedCount() {
            sharedCount++;
        }

        public long getThisCount() {
            return thisCount;
        }

        public static long getSharedCount() {
            return sharedCount;
        }
    }
}
