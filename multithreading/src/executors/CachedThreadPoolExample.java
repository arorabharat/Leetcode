package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ======================================================
 * CachedThreadPool Executor – Concept & Execution Model
 * ======================================================
 * <p>
 * This example demonstrates how a {@link java.util.concurrent.ExecutorService}
 * created using {@link java.util.concurrent.Executors#newCachedThreadPool()}
 * behaves under varying load.
 * <p>
 * --------------------------------------------
 * What is a CachedThreadPool?
 * --------------------------------------------
 * <p>
 * A CachedThreadPool:
 * - Creates new threads as needed (no fixed upper bound)
 * - Reuses idle threads when possible
 * - Removes idle threads after 60 seconds of inactivity
 * <p>
 * Internally:
 * - Uses a {@code SynchronousQueue} (no task queueing)
 * - A task is handed directly to a thread
 * - If no idle thread exists, a new thread is created
 * <p>
 * --------------------------------------------
 * Execution model
 * --------------------------------------------
 * <p>
 * 1. Task is submitted to the executor
 * 2. Executor tries to find an idle thread
 * 3. If found → task is executed immediately
 * 4. If not found → a new thread is created
 * 5. Idle threads are cached for reuse
 * 6. Threads idle for ~60s are terminated
 * <p>
 * There is no waiting queue for tasks.
 * <p>
 * --------------------------------------------
 * Why it feels "fast"
 * --------------------------------------------
 * <p>
 * Because tasks are not queued:
 * - Latency is low for short-lived tasks
 * - Parallelism grows with load
 * <p>
 * This makes CachedThreadPool suitable for:
 * - Short, non-blocking tasks
 * - Burst traffic
 * <p>
 * --------------------------------------------
 * Danger zone ⚠
 * --------------------------------------------
 * <p>
 * CachedThreadPool has NO upper bound on threads.
 * <p>
 * Submitting many blocking or slow tasks can:
 * - Create thousands of threads
 * - Exhaust memory
 * - Cause heavy context switching
 * <p>
 * It should be used with discipline.
 * <p>
 * --------------------------------------------
 * Example scenario
 * --------------------------------------------
 * <p>
 * Submitting 10 tasks quickly:
 * - Executor may create up to 10 threads
 * - Tasks execute concurrently
 * - Threads are reused if tasks finish quickly
 * <p>
 * --------------------------------------------
 * Mental model
 * --------------------------------------------
 * <p>
 * "Spawn on demand, reuse when idle, kill when bored."
 */
public class CachedThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " started");
            sleepTwoSeconds();
            System.out.println(threadName + " finished");
        };

        // Rapidly submit multiple tasks
        for (int i = 1; i <= 10; i++) {
            executor.submit(task);
        }

        // Graceful shutdown
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static void sleepTwoSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
