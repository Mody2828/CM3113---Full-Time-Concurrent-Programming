package lab02;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Example demonstrating use of ExecutorService objects to manage threads
 */
public class Cm3113Lect02Ex3 {
    
    private static int numberTasks = 4;
    private static int numberThreadsInPool = 2;
    private static int steps = 3;
    private static int stepInterval = 1;

    public static void main(String[] args) {
        System.out.println("This machine has " 
                + Runtime.getRuntime().availableProcessors() + " processors");
        long mainStart = System.nanoTime();
        ArrayList<RunnableTask> tasks = new ArrayList<RunnableTask>();
               
        ExecutorService e = Executors.newFixedThreadPool(numberThreadsInPool);
        //ExecutorService e = Executors.newCachedThreadPool();
        //ExecutorService e = Executors.newSingleThreadExecutor();
        
        for (int n = 0; n < numberTasks; n++) {
            RunnableTask r = new RunnableTask(n);
            tasks.add(r); // add reference to task to task list
            e.execute(r); // submit task to Executor
        }
        
        e.shutdown();
        
        try {
            e.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Interruption: main waited for tasks to finish");
        }
        
        System.out.println("main: " + (System.nanoTime()-mainStart)/1e6 + "ms");
        
        /* may want to comment out the following if running a high numberTasks */
//        for (int n = 0; n < numberTasks; n++) {
//            System.out.println("Task " + n + " total was " + tasks.get(n).getTotal());
//        }
    }

    /* Runnable is stored as a nested class within ExecutorExample */
    static class RunnableTask implements Runnable {
        int id, total;
        long taskStart, calcStart, taskFinish;
        
        public RunnableTask(int n) {
            id = n; total = 0; taskStart = System.nanoTime();
        }

        @Override public void run() {
            calcStart = System.nanoTime();
            for (int i = 1; i <= steps; i++) {
                work();
                System.out.println(Thread.currentThread() 
                        + " running task " + id + " at step " + i);
                pause(stepInterval);
            }
            taskFinish = System.nanoTime();
            System.out.println("Task " + id + " completed, finishing after " 
                    + (taskFinish - taskStart) / 1e6 
                    + "ms, running for " 
                    + (taskFinish - calcStart) / 1e6
                    + "ms, of which waiting was " + stepInterval*steps + "ms");
        }
        
        void work(){ // just a method to do some non-trivial calculation
            for(int i = 0; i < 20000; i++) total+= (int)Math.sqrt(i);
        }
        
        public int getTotal(){
            return total;
        }
    }
    
    /* a convenience method to make run() method of Runnable look tidier */
    static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            System.out.println("Interrupt sleep in " +  Thread.currentThread());
        }
    }
}

