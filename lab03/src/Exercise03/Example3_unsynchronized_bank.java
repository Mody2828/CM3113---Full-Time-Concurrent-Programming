package Exercise03;

public class Example3_unsynchronized_bank {
    public static void main(String[] args) {
        Bank b = new Bank();
        for (int i = 0; i < Bank.NACCOUNTS; i++) {
            new Transactions(b, i).start();
        }
    }

    /* Nested class defining our Transactions thread */
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
                if (to == from)
                    continue; // don’t transfer to same account
                int amount = (int) (Bank.INITIAL_BALANCE * Math.random() * 0.1);
                bank.transfer(from, to, amount);
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /* Nested class defining our Bank thread */
    static class Bank {
        public static final int INITIAL_BALANCE = 1000;
        public static final int NACCOUNTS = 10;
        private long[] accounts;
        private int ntransacts;

        public Bank() {
            ntransacts = 0;
            accounts = new long[NACCOUNTS];
            for (int i = 0; i < NACCOUNTS; i++) {
                accounts[i] = INITIAL_BALANCE;
            }
            test();
        }

        public void transfer(int from, int to, int amount) {
            while (accounts[from] < amount) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            }
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
            if (ntransacts % 100 == 0) {
                test();
            } // test total every 100th
        }

        public void test() {
            long sum = 0;
            for (int i = 0; i < NACCOUNTS; i++) {
                sum += accounts[i];
            }
            System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
        }
    }
} // end of Example
