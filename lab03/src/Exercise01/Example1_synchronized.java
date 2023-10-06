package Exercise01;

import java.util.concurrent.atomic.AtomicLong;

public class Example1_synchronized {
    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Counter sharedCounter = new Counter();
        CountingThread t1 = new CountingThread(counter1, sharedCounter, "t1");
        CountingThread t2 = new CountingThread(counter2, sharedCounter, "t2");
        t1.start();
        t2.start();

        for (;;) {
            long count1, count2, countTotal;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // Handle the exception
            }
            count1 = counter1.getCount();
            count2 = counter2.getCount();
            countTotal = sharedCounter.getCount();
            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal));
        }
    }

    static class Counter {
        private AtomicLong theCount;

        public Counter() {
            theCount = new AtomicLong(0L);
        }

        public void increment() {
            theCount.incrementAndGet();
        }

        public long getCount() {
            return theCount.get();
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

        @Override
        public void run() {
            for (;;) {
                thisCounter.increment();
                sharedCounter.increment();
            }
        }
    }
}
