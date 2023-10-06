package Exercise02;

public class Example2_synchronized {
    public static void main(String[] args) {
        ResourceUser t1 = new ResourceUser("t1");
        ResourceUser t2 = new ResourceUser("t2");
        t1.start();
        t2.start();
        while (ResourceUser.getResourceLeft() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(ResourceUser.getReport());
        }
    }

    /* Nested class for our ResourceUser objects */
    static class ResourceUser extends Thread {
        private static long resource = 100000000L;
        private static long used = 0L;

        public ResourceUser(String name) {
            super(name);
        }

        public void run() {
            while (resource > 0) {
                synchronized (ResourceUser.class) {
                    takeResource();
                }
                Thread.yield();
                synchronized (ResourceUser.class) {
                    useResource();
                }
            }
        }

        public static void takeResource() {
            resource--;
        }

        public static void useResource() {
            used++;
        }

        public static long getResourceLeft() {
            return resource;
        }

        public static long getResourceUsed() {
            return used;
        }

        public static String getReport() {
            return "Remaining = " + resource + " Used = " + used
                    + " Total = " + (resource + used);
        }
    } // end of ResourceUser class
} // end of Main Application
