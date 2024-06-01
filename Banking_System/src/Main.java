

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");


        // Initializes the banking system.
        System.out.printf("Initializing banking system..");
        int totalNumberOfSimulaion = 10;
        OperationsQueue operationsQueue = new OperationsQueue();
        Bank bank = new Bank("123", operationsQueue);


        // Initializes and starts a separate thread to handle the simulation.
        System.out.println("Initializing simulation....");
        Thread simulationThread = new Thread(() -> {
            operationsQueue.addSimulation(totalNumberOfSimulaion);
        });
        simulationThread.start();


        // Initializes and starts a separate thread to handle the deposit operations.
        System.out.printf("Initializing deposit system....");
        Thread depositThread = new Thread(() -> {
            bank.deposit();
        });
        depositThread.start();
        // System.out.println("coompleted");
        System.out.println("Deposit thread started.");


        // Initializes and starts a separate thread to handle the withdraw operations.
        System.out.printf("Initializing withdraw system....");
        Thread withdrawThread = new Thread(() -> {
            bank.withdraw();
        });
        withdrawThread.start();
        // System.out.println("coompleted");
        System.out.println("Withdraw thread started.");


        
        // Main thread waits for simulationThread, depositThread, and withdrawThread to finish
        try {
            simulationThread.join(); // Wait for simulationThread to finish
            depositThread.join(); // Wait for depositThread to finish
            withdrawThread.join(); // Wait for withdrawThread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Prints a completion message.
        // System.out.println("coompleted");
        System.out.println("All threads completed.");
    }
}