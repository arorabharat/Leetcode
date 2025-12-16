package threads;


public class JoinExample {

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {
            System.out.println("Worker started");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Worker finished");
        });

        worker.start();

        System.out.println("Main waiting for worker...");
        worker.join();  // main thread waits here

        System.out.println("Main resumes after worker finishes");
    }
}

