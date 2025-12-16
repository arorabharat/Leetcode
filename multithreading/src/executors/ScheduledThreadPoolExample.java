package executors;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * =========================================================
 * ScheduledThreadPool Executor – Concept & Execution Model
 * =========================================================
 * <p>
 * This example demonstrates how a {@link java.util.concurrent.ScheduledExecutorService}
 * created using {@link java.util.concurrent.Executors#newScheduledThreadPool(int)}
 * executes delayed and periodic tasks.
 * <p>
 * --------------------------------------------
 * What is a ScheduledThreadPool?
 * --------------------------------------------
 * <p>
 * A ScheduledThreadPool:
 * - Executes tasks after a given delay
 * - Executes tasks periodically
 * - Uses a fixed number of worker threads
 * <p>
 * Unlike a timer:
 * - Multiple tasks can run concurrently
 * - One slow task does not block others (if threads are available)
 * <p>
 * --------------------------------------------
 * Scheduling types
 * --------------------------------------------
 * <p>
 * 1. schedule(...)
 * - One-shot execution after a delay
 * <p>
 * 2. scheduleAtFixedRate(...)
 * - Runs at a fixed rate
 * - Period is measured from scheduled start time
 * - If a task runs long, next execution may start immediately
 * <p>
 * 3. scheduleWithFixedDelay(...)
 * - Runs with a fixed delay
 * - Delay is measured from task completion time
 * - Execution never overlaps
 * <p>
 * --------------------------------------------
 * Threading behavior
 * --------------------------------------------
 * <p>
 * - Pool size limits concurrency
 * - Each execution occupies one thread
 * - Periodic tasks may overlap if pool size > 1
 * <p>
 * --------------------------------------------
 * Mental model
 * --------------------------------------------
 * <p>
 * "A clock + a thread pool."
 */
public class ScheduledThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {

        // Pool with 2 threads
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        // One-time delayed task
        scheduler.schedule(() -> {
            System.out.println(time() + " One-shot task executed");
        }, 3, TimeUnit.SECONDS);

        // Fixed-rate task (runs every 2 seconds)
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(time() + " Fixed-rate task start");
            sleepOneSecond();
        }, 1, 2, TimeUnit.SECONDS);

        // Fixed-delay task (runs 2 seconds AFTER previous run finishes)
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println(time() + " Fixed-delay task start");
            sleepOneSecond();
        }, 1, 2, TimeUnit.SECONDS);

        // Let it run for a while
        Thread.sleep(15000);

        // Graceful shutdown
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);
    }

    private static String time() {
        return LocalTime.now().toString();
    }

    private static void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
