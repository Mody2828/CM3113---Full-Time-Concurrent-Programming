/* File: Exercise3_fullysynchronized.java    
 * Sample solution to CM3113 Lab2 Exercise 3 */
package Exercise03_Soluation;

/**
 *
 * @author david davidson This version of exercise 4 fixes the lost updates
 *         problem and synchronizes calls to test() when Transaction threads are
 *         not in the middle of a transfer operation.
 */
public class Exercise3_fullysynchronized {

    public static void main(String[] args) {
        Bank b = new Bank();
        for (int i = 0; i < Bank.NACCOUNTS; i++) {
            new Transactions(b, i).start();
        }
    }

    static class Bank {

        public static final int INITIAL_BALANCE = 10000;
        public static final int NACCOUNTS = 10;
        private long[] accounts;
        private int ntransacts;

        public Bank() {
            accounts = new long[NACCOUNTS];
            for (int i = 0; i < NACCOUNTS; i++) {
                accounts[i] = INITIAL_BALANCE;
            }
            ntransacts = 0;
            test();
        }

        public void transfer(int from, int to, int amount) {
            while (accounts[from] < amount) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            }

            synchronized (this) {
                /* NB calls to yield() increase chances of unsafe operation */
                long temp = accounts[from];
                if (Math.random() < 0.05) {
                    Thread.yield();
                }
                accounts[from] = temp - amount;
                temp = accounts[to];
                if (Math.random() < 0.05) {
                    Thread.yield();
                }
                accounts[to] = temp + amount;
                ntransacts++;
            }
            if (ntransacts % 100 == 0) {
                test();
            }
        }

        public void test() {
            long sum;

            synchronized (this) {
                sum = 0;
                for (int i = 0; i < NACCOUNTS; i++) {
                    sum += accounts[i];
                }
            }
            System.out.println("Transactions:" + ntransacts
                    + " Sum: " + sum);
        }
    }

    static class Transactions extends Thread {

        private Bank bank;
        private int from;

        public Transactions(Bank b, int i) {
            from = i;
            bank = b;
        }

        public void run() {
            for (;;) {
                int to = (int) (Bank.NACCOUNTS * Math.random());
                if (to == from) {
                    to = (to + 1) % Bank.NACCOUNTS;
                }
                int amount = (int) (Bank.INITIAL_BALANCE * Math.random() * 0.1);
                bank.transfer(from, to, amount);
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
