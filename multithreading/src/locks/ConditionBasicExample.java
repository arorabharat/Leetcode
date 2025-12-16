package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ======================================================
 * Example 1: Basic Thread Communication using Condition
 * ======================================================
 * <p>
 * Goal:
 * - One thread waits for a signal
 * - Another thread sends the signal
 * <p>
 * This is equivalent to wait()/notify(), but explicit.
 */
public class ConditionBasicExample {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition waitCondition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        // Waiting thread
        Thread waiter = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Waiter: waiting for signal...");
                waitCondition.await(); // releases lock + waits
                System.out.println("Waiter: received signal, resuming work");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("Waiter: unlock");
                lock.unlock();
            }
        });

        // Signaling thread
        Thread signaler = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Signaler: doing some work");
                sleepOneSecond();
                System.out.println("Signaler: sending signal");
                waitCondition.signal(); // wakes one waiting thread
            } finally {
                System.out.println("Signaler: unlock");
                lock.unlock();
            }
        });

        waiter.start();
        Thread.sleep(500); // ensure waiter starts waiting first
        signaler.start();
    }

    private static void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
