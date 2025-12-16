package threads;

public class InterruptExample {

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Working...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // interruption while blocked
                    Thread.currentThread().interrupt(); // restore flag
                }
            }
            System.out.println("Worker interrupted, exiting");
        });

        worker.start();
        Thread.sleep(1500);
        worker.interrupt();
    }
}
