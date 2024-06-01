import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


public class OperationsQueue {
    private final List<Integer> operations = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    private final Condition notEmpty = lock.newCondition();

    /*
    The `addSimulation` method is responsible for adding a specified 
    number of random integers to the `operations` list, simulating the 
    arrival of new customers, and introducing random delays between 
    operations. 
    */
    public void addSimulation(int totalSimulation) {
        for (int i = 0; i < totalSimulation; i++) {
            int random = (int) (Math.random() * 200) - 100;
            if (random != 0) {
                lock.lock();
                try {
                    operations.add(random);
                    notEmpty.signal();  // Signal that a new operation has been added
                } finally {
                    lock.unlock();
                }
            }
            System.out.println(i + ". New operation added: " + random);
            try {
                Thread.sleep((int) (Math.random() * 80));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.lock();
        try {
            operations.add(-9999);
            notEmpty.signalAll();  // Signal all waiting threads for termination
            operations.add(-9999); // Add a second termination signal
            notEmpty.signalAll();  // Signal all waiting threads for termination
        } finally {
            lock.unlock();
        }
    }


    public void add(int amount) {
        lock.lock();
        try {
            operations.add(amount);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }


    public int getNextItem() {
        lock.lock();
        try {
            while (operations.isEmpty()) {
                notEmpty.await();  // Wait until the queue is not empty
            }
              int nextItem = operations.remove(0);
              printQueue();
              return nextItem;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        } finally {
            lock.unlock();
        }
    }
     public synchronized void printQueue() {
        System.out.println("Current Queue: " + operations);
    }
}