package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================
 * SingleThreadExecutor – Concept & Execution Guarantees
 * =====================================================
 * <p>
 * This example demonstrates the behavior of
 * {@link java.util.concurrent.Executors#newSingleThreadExecutor()}.
 * <p>
 * --------------------------------------------
 * What is a SingleThreadExecutor?
 * --------------------------------------------
 * <p>
 * A SingleThreadExecutor:
 * - Uses exactly ONE worker thread
 * - Executes tasks sequentially
 * - Maintains a FIFO task queue
 * <p>
 * Even though it uses only one thread, it is NOT the same
 * as running code directly on the main thread.
 * <p>
 * --------------------------------------------
 * Key guarantees
 * --------------------------------------------
 * <p>
 * - Tasks execute strictly in submission order
 * - Only one task runs at a time
 * - No interleaving between tasks is possible
 * - If the worker thread crashes, the executor
 * automatically replaces it
 * <p>
 * --------------------------------------------
 * Why not just use a single thread manually?
 * --------------------------------------------
 * <p>
 * SingleThreadExecutor provides:
 * - Task queueing
 * - Lifecycle management
 * - Graceful shutdown
 * - Failure isolation
 * <p>
 * This makes it ideal for:
 * - Event processing
 * - State machines
 * - Serializing access to shared state
 * <p>
 * --------------------------------------------
 * Mental model
 * --------------------------------------------
 * <p>
 * "One worker, infinite inbox, strict order."
 */
public class SingleThreadExecutorExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable task1 = () -> {
            System.out.println("Task 1 started");
            sleepOneSecond();
            System.out.println("Task 1 finished");
        };

        Runnable task2 = () -> {
            System.out.println("Task 2 started");
            sleepOneSecond();
            System.out.println("Task 2 finished");
        };

        Runnable task3 = () -> {
            System.out.println("Task 3 started");
            sleepOneSecond();
            System.out.println("Task 3 finished");
        };

        // Submit tasks in order
        executor.submit(task1);
        executor.submit(task2);
        executor.submit(task3);

        // Shutdown gracefully
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
