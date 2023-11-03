
import java.util.ArrayList;
import java.util.concurrent.locks.*;

/* File: Example2_unsynchronized.java    
 * Starting point CM3113 Lab3 Exercise 2 */
/**
 * This version of exercise 2 is unsynchronized. The version suffers from "lost
 * updates" to the shared static variables resource and used.
 */
public class Exercise2unsafe_Soluation {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        long NUMBER_RESOURCES = 10000000L;
        ResourceUser.setResources(NUMBER_RESOURCES);
        ArrayList<ResourceUser> users = new ArrayList<ResourceUser>();
        int N = 10;
        for (int i = 0; i < N; i++) {
            ResourceUser user = new ResourceUser("user" + i, lock);
        }

        for (ResourceUser user : users)
            user.start();

        System.out.println("Total should be constant:" + NUMBER_RESOURCES);
        while (ResourceUser.getResourceLeft() > 0) {
            try {
                Thread.sleep(100);
                System.out.println(ResourceUser.getReport());
            } catch (InterruptedException e) {
            }

        }
    }

    /* Nested class for our ResourceUser objects */
    static class ResourceUser extends Thread {

        private static long resource = 100000000L;
        private static long used = 0L;
        private final Lock lock;

        public ResourceUser(String name, Lock lock) {
            super(name);
            this.lock = lock;
        }

        public void run() {
            while (getResourceLeft() > 0) {
                lock.lock();
                try {
                    takeResource();
                } finally {
                    lock.unlock();
                }
            }
        }

        public static void takeResource() {
            resource--;
            used++;
        }

        public static long getResourceLeft() {
            return resource;
        }

        public static String getReport() {
            String report = "";
            report = "Remaining = " + resource + "   Used = "
                    + used + "  Total = " + (resource + used);
            return report;
        }

        public static void setResources(long nResources) {
            resource = nResources;
        }
    }
}
