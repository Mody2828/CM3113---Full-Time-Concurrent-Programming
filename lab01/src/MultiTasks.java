import java.time.Duration;
import java.time.LocalTime;

public class MultiTasks {

    public static final long MAX = 1000000000L;
    public static final int NUMBEROFTASKS = 4;
    private static int tasksLeft;
    private static LocalTime mainstart;

    public static void main(String[] args) {
        mainstart = LocalTime.now();
        System.out.println("Main thread started at " + mainstart);
        tasksLeft = NUMBEROFTASKS;

        for (int i = 0; i < NUMBEROFTASKS; i++) {
            task(i);
        }
        LocalTime finish = LocalTime.now();
        System.out.println("Main thread ended at " + finish + " after running for "
                + Duration.between(mainstart, finish).toMillis() + " ms");
    }

    public static void task(int id) {
        LocalTime start = LocalTime.now();
        System.out.println("Task " + id + " started at " + start);
        long sum = 0;
        for (long i = 0; i < MAX; i++) { // this creates a time-consuming loop
            sum++;
        }
        LocalTime finish = LocalTime.now();
        System.out.println("Task " + id + " ended at " + finish
                + " with sum = " + sum + " after running for "
                + Duration.between(start, finish).toMillis() + "ms");
        tasksLeft--;
        if (tasksLeft == 0) {
            System.out.println("Last Task ended at " + finish
                    + " total run time for " + NUMBEROFTASKS + " tasks is "
                    + Duration.between(mainstart, finish).toMillis() + "ms");
        }
    }
}
