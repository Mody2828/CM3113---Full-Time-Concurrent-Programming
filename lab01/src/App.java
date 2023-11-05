public class App {
    public static void main(String[] args) throws Exception {
        Runnable r = () -> {
            for (;;)
                System.out.println(Thread.currentThread().toString());

        };
        Thread t1 = new Thread(r, "t1");
        t1.start();
        Thread t2 = new Thread(r, "t2");
        t2.start();
        Thread t3 = new Thread(r, "t3");
        t3.start();
        // t1,t2,t3 same priority 5 as main
        t2.setPriority(t1.getPriority() - 1);
        // t2 priority now 4
        t3.setPriority(Thread.MIN_PRIORITY);
        // t3 priority now 1
        t1.setPriority(8);
        // t1 priority now 8

    }
}
