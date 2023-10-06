package Exercise02_Soluation;

/* File: Example2_uynchronized.java    
 * Starting point CM3113 Lab3 Exercise 2 */
/**
 * This version of exercise 2 is synchronized. The critical sections are those
 * that are making changes to the shared data. Once these are synchronized only
 * one thread can access them at a time, ensuring safe access. The solutions
 * still gives a difference of 1 or 2 in the Total from expected, I suspect
 * because of the short but finite time between takeRosource() and useResource()
 * in the run() method. However the final summary line in the output after all
 * resources are all used show that all resources are accounted for.
 */
public class Exercise2_synchronized {

    public static void main(String[] args) {
        long NUMBER_RESOURCES = 100000000L;
        ResourceUser.setResources(NUMBER_RESOURCES);
        ResourceUser t1 = new ResourceUser("t1");
        ResourceUser t2 = new ResourceUser("t2");
        t1.start();
        t2.start();
        System.out.println("Total should be constant:" + NUMBER_RESOURCES);
        while (ResourceUser.getResourceLeft() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(ResourceUser.getReport());
        }
    }

    /* nested class to define the ResourceUser Thread */
    static class ResourceUser extends Thread {

        private static long resource = 100000000L;
        private static long used = 0L;
        Object obj = new Object();

        public ResourceUser(String name) {
            super(name);
        }

        public void run() {
            while (resource > 0) {
                takeResource();
                useResource();
            }
        }

        public static synchronized void takeResource() {
            resource--;
            // Thread.yield();
        }

        public static synchronized void useResource() {
            used++;
            // Thread.yield();
        }

        public static long getResourceLeft() {
            return resource;
        }

        public static long getResourceUsed() {
            return used;
        }

        public static synchronized String getReport() {
            return "Remaining = " + resource + "   Used = " + used + "  Total = " + (resource + used);
        }

        public static void setResources(long nResources) {
            resource = nResources;
        }
    }

}
