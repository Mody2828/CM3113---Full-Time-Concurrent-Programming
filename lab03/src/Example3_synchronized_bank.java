// 1- Added the synchronized keyword to the transfer and test methods to ensure that only one thread can execute them at a time.
// 2- Removed the unnecessary empty catch blocks for InterruptedException. It's good practice to at least print a message or log the exception

public class Example3_synchronized_bank {
    public static void main(String[] args) {
        Bank b = new Bank();
        for (int i = 0; i < Bank.NACCOUNTS; i++) {
            new Transactions(b, i).start();
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
                if (to == from)
                    continue; // don’t transfer to same account
                int amount = (int) (Bank.INITIAL_BALANCE * Math.random() * 0.1);
                bank.transfer(from, to, amount);
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

        public synchronized void transfer(int from, int to, int amount) {
            while (accounts[from] < amount) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

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

        public synchronized void test() {
            long sum = 0;
            for (int i = 0; i < NACCOUNTS; i++) {
                sum += accounts[i];
            }
            System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
        }
    }
}
