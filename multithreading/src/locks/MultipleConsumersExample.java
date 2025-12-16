package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleConsumersExample {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition dataAvailable = lock.newCondition();
    private int data = 0;

    public void consume(String name) {
        lock.lock();
        try {
            while (data == 0) {
                System.out.println(name + " waiting");
                dataAvailable.await();
            }
            data--;
            System.out.println(name + " consumed data");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void produce() {
        lock.lock();
        try {
            data = 2;
            System.out.println("Producer produced data");
            dataAvailable.signalAll(); // wake all consumers
        } finally {
            // comment this line to see deadlock. It also shows that signal does not release lock
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MultipleConsumersExample example = new MultipleConsumersExample();

        new Thread(() -> example.consume("Consumer-1")).start();
        new Thread(() -> example.consume("Consumer-2")).start();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        new Thread(example::produce).start();
    }
}
