package locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * =====================================================
 * Plain ReentrantLock Example (No Condition)
 * =====================================================
 * <p>
 * This example demonstrates:
 * - Mutual exclusion using ReentrantLock
 * - Safe access to shared mutable state
 * - Proper lock/unlock discipline
 * <p>
 * What this example does NOT do:
 * - No thread communication
 * - No waiting for state changes
 * - No signaling
 * <p>
 * This is the simplest and most common valid use of a lock.
 */
public class PlainLockExample {

    private int counter = 0;
    private int counterWithoutLock = 0;


    // Explicit lock protecting the shared state
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Only one thread at a time can execute this method.
     * The lock ensures atomicity and visibility.
     */
    public void increment() {
        lock.lock(); // acquire lock
        try {
            counter++; // critical section
        } finally {
            lock.unlock(); // ALWAYS release lock
        }
    }

    public void incrementWithoutLock() {
        counterWithoutLock++; // critical section
    }

    /**
     * Reading shared state also needs locking to guarantee
     * visibility and consistency.
     */
    public int getCounter() {
        lock.lock();
        try {
            return counter;
        } finally {
            lock.unlock();
        }
    }

    public int getCounterWithoutLock() {
        return counterWithoutLock;
    }

    public static void main(String[] args) throws InterruptedException {
        lockExampleOnSharedResource();
        withoutLockExampleOnSharedResourceLeadIncorrectOutput();
    }

    private static void lockExampleOnSharedResource() throws InterruptedException {
        PlainLockExample example = new PlainLockExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        };

        Thread t1 = new Thread(task, "Worker-1");
        Thread t2 = new Thread(task, "Worker-2");
        Thread t3 = new Thread(task, "Worker-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final counter value: " + example.getCounter());
    }

    private static void withoutLockExampleOnSharedResourceLeadIncorrectOutput() throws InterruptedException {
        PlainLockExample example = new PlainLockExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.incrementWithoutLock();
            }
        };

        Thread t1 = new Thread(task, "Worker-1");
        Thread t2 = new Thread(task, "Worker-2");
        Thread t3 = new Thread(task, "Worker-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // this will give different value every time, may give right value sometime
        System.out.println("Final without counter value: " + example.getCounterWithoutLock());
    }
}
