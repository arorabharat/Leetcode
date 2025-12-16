package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ============================================
 * FixedThreadPool Execution & Context Switching
 * ============================================
 * <p>
 * This example demonstrates how tasks are executed in a
 * {@link java.util.concurrent.ExecutorService} created using
 * {@link java.util.concurrent.Executors#newFixedThreadPool(int)}.
 * <p>
 * --------------------------------------------
 * Key Concept: Task execution is NOT preemptive
 * --------------------------------------------
 * <p>
 * In a fixed thread pool, each worker thread executes exactly
 * one task at a time. Once a thread starts executing a task,
 * it will continue running that task until:
 * - the task completes normally, or
 * - the task throws an exception, or
 * - the thread is interrupted
 * <p>
 * The executor will NOT pause a running task to execute another
 * task on the same thread.
 * <p>
 * --------------------------------------------
 * Single-thread pool (size = 1)
 * --------------------------------------------
 * <p>
 * When the pool size is 1:
 * - Tasks are executed strictly in submission order
 * - Only one task can run at any moment
 * - Other submitted tasks wait in the queue
 * <p>
 * Even if a task calls {@code Thread.sleep()}, performs blocking
 * I/O, or waits on a lock, the thread remains logically assigned
 * to that task. Sleeping does NOT allow the executor to switch
 * the thread to a different task.
 * <p>
 * Result:
 * - No interleaving (tangled output) is possible
 * - Behavior is equivalent to a single-threaded program with
 * a task queue
 * <p>
 * --------------------------------------------
 * Multi-thread pool (size > 1)
 * --------------------------------------------
 * <p>
 * When the pool size is greater than 1:
 * - Multiple tasks may execute concurrently
 * - Output order becomes non-deterministic
 * - Interleaving between tasks becomes possible
 * <p>
 * --------------------------------------------
 * Context switching clarified
 * --------------------------------------------
 * <p>
 * Context switching is handled by the operating system, not the
 * executor. The OS may pause and resume threads at any time,
 * but a paused thread always resumes the SAME task it was
 * executing before.
 * <p>
 * Important distinction:
 * - OS schedules threads
 * - ExecutorService schedules tasks
 * <p>
 * A task is never preempted by another task on the same thread.
 * <p>
 * --------------------------------------------
 * Mental model to remember
 * --------------------------------------------
 * <p>
 * One thread  -> one task at a time -> until completion
 * <p>
 * Thread.sleep(), blocking calls, or waiting do not change this rule.
 */

public class SingleThreadExecutorDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Runnable task1 = () -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Task1: " + i);
                sleepOneSecond();
            }
        };

        Runnable task2 = () -> {
            for (int i = 11; i <= 20; i++) {
                System.out.println("Task2: " + i);
                sleepOneSecond();
            }
        };

        executor.submit(task1);
        executor.submit(task2);

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
