package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ======================================================
 * Example 2: Correct Condition usage with a predicate
 * ======================================================
 * <p>
 * Rule:
 * Always await() inside a while loop.
 */
class ConditionWithPredicate {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition hasData = lock.newCondition();
    private boolean dataReady = false;

    public void consume() {
        lock.lock();
        try {
            while (!dataReady) { // MUST be while, not if
                System.out.println("Consumer: waiting for data");
                hasData.await();
            }
            System.out.println("Consumer: consumed data");
            dataReady = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void produce() {
        lock.lock();
        try {
            System.out.println("Producer: producing data");
            dataReady = true;
            hasData.signal();
        } finally {
            lock.unlock();
        }
    }
}
