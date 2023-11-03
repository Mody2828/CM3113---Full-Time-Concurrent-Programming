
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise03unsafe {

    private static int N;
    private static boolean finished;

    public static void main(String[] args) throws InterruptedException {
        int Number = 4;
        finished = false;
        ExecutorService e = Executors.newFixedThreadPool(Number);
        for (int i = 0; i < Number; i++)
            e.execute(new Task(i));
        System.out.println("Threads running at A = " + Thread.activeCount());
        Thread.sleep(1000L); /* waits 1 second */
        finished = true;
        // System.out.println("N=" + N);
        e.shutdown();
        e.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("Threads running at B = " + Thread.activeCount());
        System.out.println("Main method is finished, with N = " + N);
        System.out.println("finished = " + finished + " so other " + Number + " threads should stop");
    }

    static class Task extends Thread {
        int n;
        int id;

        public Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (!finished) {
                N++; /* adds 1 to overall total */
                n++; /* adds 1 for this thread */
            }
            System.out.println("Thread " + id + " is finished, with n = " + n);
        }
    }
}
