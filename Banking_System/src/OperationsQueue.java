import java.util.List;
import java.util.ArrayList;

public class OperationsQueue {
    private final List<Integer> operations = new ArrayList<>();

    
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
                operations.add(random);
            }
            System.out.println(i + ". New operation added: " + random);
            try {
                Thread.sleep((int) (Math.random() * 80));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        operations.add(-9999);
    }


    public void add(int amount) {
        operations.add(amount);
    }



    /*
    the `getNextItem` is responsible for safely retrieving and removing the
    next operation from the queue, ensuring that the queue operations are 
    thread-safe and that threads do not continuously check for new 
    operations, thus conserving CPU resources. 
    */
    public synchronized int getNextItem() {

        //  This loop is used to block the thread until there's an operation available in the queue.
        while(operations.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return operations.remove(0);
    }
}