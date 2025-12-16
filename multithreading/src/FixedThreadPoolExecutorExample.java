import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================
 * FixedThreadPool Executor – Concept & Execution Model
 * =====================================================
 * <p>
 * This example explains how a {@link java.util.concurrent.ExecutorService}
 * created using {@link java.util.concurrent.Executors#newFixedThreadPool(int)}
 * behaves when multiple tasks are submitted.
 * <p>
 * --------------------------------------------
 * What is a FixedThreadPool?
 * --------------------------------------------
 * <p>
 * A FixedThreadPool:
 * - Creates a fixed number of worker threads at startup
 * - Reuses these threads for executing submitted tasks
 * - Maintains an internal queue for excess tasks
 * <p>
 * Thread count is constant:
 * - No new threads are created beyond the configured size
 * - Idle threads wait for new tasks
 * <p>
 * --------------------------------------------
 * Execution model
 * --------------------------------------------
 * <p>
 * 1. Tasks submitted via {@code submit()} or {@code execute()}
 * are placed into a work queue.
 * 2. Any available worker thread picks the next task from the queue.
 * 3. A worker thread executes the task fully.
 * 4. After completion, the thread returns to the pool and
 * fetches the next queued task.
 * <p>
 * A worker thread never executes more than one task at a time.
 * <p>
 * --------------------------------------------
 * Blocking & sleeping behavior
 * --------------------------------------------
 * <p>
 * If a task:
 * - calls {@code Thread.sleep()}
 * - performs blocking I/O
 * - waits on a lock or condition
 * <p>
 * the worker thread remains assigned to that task. The executor
 * does NOT reassign the thread to another task while it is blocked.
 * <p>
 * --------------------------------------------
 * Ordering guarantees
 * --------------------------------------------
 * <p>
 * - Tasks are dequeued in FIFO order
 * - Execution order is guaranteed ONLY when pool size = 1
 * - For pool size > 1, execution order is non-deterministic
 * <p>
 * --------------------------------------------
 * Context switching clarified
 * --------------------------------------------
 * <p>
 * Context switching is managed by the operating system:
 * - Threads may be paused and resumed
 * - A paused thread always resumes the SAME task
 * <p>
 * The executor never performs task-level context switching.
 * <p>
 * --------------------------------------------
 * Example scenario
 * --------------------------------------------
 * <p>
 * Pool size = 2
 * Tasks submitted = 4
 * <p>
 * Possible execution:
 * - Task1 and Task2 run concurrently
 * - Task3 and Task4 wait in the queue
 * - When a thread finishes, it picks the next task
 * <p>
 * --------------------------------------------
 * Mental model
 * --------------------------------------------
 * <p>
 * Fixed number of workers.
 * Unlimited line of work.
 * Each worker finishes its current job before taking the next.
 */
public class FixedThreadPoolExecutorExample {

    public static void main(String[] args) throws InterruptedException {

        // Fixed thread pool with exactly 2 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            for (int i = 1; i <= 3; i++) {
                System.out.println(threadName + " executing step " + i);
                sleepOneSecond();
            }
        };

        // Submit multiple tasks
        executor.submit(task);
        executor.submit(task);
        executor.submit(task);
        executor.submit(task);

        // Graceful shutdown
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    private static void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
