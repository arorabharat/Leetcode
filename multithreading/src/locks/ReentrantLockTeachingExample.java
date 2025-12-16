package locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ==========================================================
 * ReentrantLock – A Guided, Practical Example
 * ==========================================================
 *
 * This example demonstrates:
 *   1. Basic locking / unlocking
 *   2. Reentrancy (same thread acquiring the lock multiple times)
 *   3. tryLock() with timeout
 *   4. Interruptible lock acquisition
 *   5. Condition variables (await / signal)
 *   6. Fair vs non-fair locking behavior
 *
 * ----------------------------------------------------------
 * Mental model
 * ----------------------------------------------------------
 *
 * - A ReentrantLock is an explicit mutual exclusion lock
 * - It tracks:
 *     • Owner thread
 *     • Hold count
 *
 * Same thread can acquire the lock multiple times.
 * Lock is released only when hold count reaches zero.
 *
 * ----------------------------------------------------------
 * Golden rule
 * ----------------------------------------------------------
 *
 * Always unlock in a finally block.
 * Forgetting to unlock = guaranteed deadlock.
 */
public class ReentrantLockTeachingExample {

    /**
     * Fair lock:
     *   - Threads acquire lock in FIFO order
     *   - Slightly lower throughput
     *   - Prevents starvation
     *
     * Change to `false` to observe non-fair behavior.
     */
    private final ReentrantLock lock = new ReentrantLock(true);

    /**
     * Condition variables:
     *   - Similar to wait/notify
     *   - Each Condition has its own wait queue
     */
    private final Condition notEmpty = lock.newCondition();

    private int sharedResource = 0;

    /**
     * ------------------------------------------------------
     * 1. Basic locking + Reentrancy
     * ------------------------------------------------------
     *
     * This method acquires the lock and then calls another
     * method that also acquires the same lock.
     *
     * This works ONLY because the lock is reentrant.
     */
    public void outerMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + " acquired lock in outerMethod");
            innerMethod(); // Reentrant acquisition
        } finally {
            System.out.println(Thread.currentThread().getName()
                    + " unlock lock in outerMethod");
            lock.unlock();
        }
    }

    private void innerMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + " re-acquired lock in innerMethod");
            sharedResource++;
        } finally {
            System.out.println(Thread.currentThread().getName()
                    + " unlock lock in innerMethod");
            lock.unlock();
        }
    }

    /**
     * ------------------------------------------------------
     * 2. tryLock() with timeout
     * ------------------------------------------------------
     *
     * Thread attempts to acquire the lock within a time limit.
     * If it fails, it can do something else instead of blocking.
     */
    public void tryLockExample() {
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName()
                            + " acquired lock using tryLock()");
                    sharedResource++;
                } finally {
                    System.out.println(Thread.currentThread().getName()
                            + " unlock lock using tryLock()");
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " could NOT acquire lock using tryLock()");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * ------------------------------------------------------
     * 3. Interruptible lock acquisition
     * ------------------------------------------------------
     *
     * Unlike synchronized, a thread waiting on this lock
     * can be interrupted.
     */
    public void interruptibleLockExample() {
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName()
                        + " acquired lock interruptibly");
                Thread.sleep(2000);
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()
                    + " was interrupted while waiting for the lock");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * ------------------------------------------------------
     * 4. Condition variable example (await / signal)
     * ------------------------------------------------------
     *
     * Demonstrates producer-consumer style coordination.
     *
     * await():
     *   - Releases the lock
     *   - Suspends the thread
     *   - Re-acquires the lock before returning
     */
    public void awaitCondition() {
        lock.lock();
        try {
            while (sharedResource == 0) {
                System.out.println(Thread.currentThread().getName()
                        + " waiting for resource");
                notEmpty.await();
            }
            System.out.println(Thread.currentThread().getName()
                    + " consumed resource");
            sharedResource--;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void signalCondition() {
        lock.lock();
        try {
            sharedResource++;
            System.out.println(Thread.currentThread().getName()
                    + " produced resource");
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * ------------------------------------------------------
     * Main method to drive the demo
     * ------------------------------------------------------
     */
    public static void main(String[] args) throws InterruptedException {

        ReentrantLockTeachingExample example = new ReentrantLockTeachingExample();

        // Reentrancy demo
        new Thread(example::outerMethod, "Thread-A").start();

        Thread.sleep(500);

        // tryLock demo
        new Thread(example::tryLockExample, "Thread-B").start();

        Thread.sleep(500);

        // Interruptible lock demo
        Thread t = new Thread(example::interruptibleLockExample, "Thread-C");
        t.start();
        Thread.sleep(500);
        t.interrupt();

        Thread.sleep(500);

        // Condition demo
        new Thread(example::awaitCondition, "Consumer").start();
        Thread.sleep(1000);
        new Thread(example::signalCondition, "Producer").start();
    }
}
