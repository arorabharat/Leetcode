package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * =====================================================
 * Condition Example – Waiting for State Change
 * =====================================================
 *
 * This example demonstrates:
 *   - How a thread waits using Condition.await()
 *   - How another thread wakes it using Condition.signal()
 *   - Why a while loop is mandatory
 *
 * This is the canonical use of Condition.
 */
public class ConditionBasicExample {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition dataAvailable = lock.newCondition();

    // Shared state protected by the lock
    private boolean hasData = false;

    /**
     * Consumer waits until data is available.
     */
    public void consume() {
        lock.lock();
        try {
            // Wait until the condition (state) becomes true
            while (!hasData) {
                System.out.println("Consumer waiting for data...");
                dataAvailable.await(); // releases lock + waits
            }
            System.out.println("Consumer consumed data");
            hasData = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Producer creates data and signals waiting thread.
     */
    public void produce() {
        lock.lock();
        try {
            System.out.println("Producer produced data");
            hasData = true;
            dataAvailable.signal(); // notify waiting thread
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionBasicExample example = new ConditionBasicExample();

        Thread consumer = new Thread(example::consume, "Consumer");
        Thread producer = new Thread(example::produce, "Producer");

        consumer.start();
        Thread.sleep(1000); // ensure consumer waits first
        producer.start();
    }
}

