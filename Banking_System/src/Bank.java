import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bank {
    String accountNumber;
    OperationsQueue operationsQueue;  

    int balance = 0;
    private final Lock lock = new ReentrantLock();

    public Bank(String accountNumber, OperationsQueue operationsQueue) {
        this.accountNumber = accountNumber;
        this.operationsQueue = operationsQueue;
    }

    // A deposit function that will run in parallel on a separate thread. It will be a loop where in each iteration, it read the amount from the operationQueue and deposit the amount.
    public void deposit() {
        while (true) {
            int amount = operationsQueue.getNextItem();
            if(amount == -9999) {
                break;
            }
            if (amount>0) {
                lock.lock();
                try{
                    balance =  balance + amount;
                    System.out.println("Deposited: " + amount + " Balance: " + balance);
                } finally {
                    lock.unlock();
                }
                
            }
            else{
                operationsQueue.add(amount);
                System.out.println("operation added back "+amount);
            }

        }
    }

    // A withdraw function that will run in parallel on a separate thread. It will be a loop where in each iteration, it read the amount from the operationQueue and withdraw the amount.
    public void withdraw() {
        while (true) {
            int amount = operationsQueue.getNextItem();
            if(amount == -9999) {
                break;
            }

            lock.lock();
            try {
                if (balance + amount < 0) {
                    System.out.println("Not enough balance to withdraw " + amount);
                    continue;
                }

                if (amount < 0) {
                    balance = balance + amount;
                    System.out.println("Withdrawn: " + amount + " Balance: " + balance);
                } else {
                    operationsQueue.add(amount);
                    System.out.println("operation added back " + amount);
                }
            } finally {
                lock.unlock();
            }

        }
    }
}