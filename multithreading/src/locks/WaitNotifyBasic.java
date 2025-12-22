package locks;

public class WaitNotifyBasic {

    private final Object lock = new Object();
    private boolean ready = false;

    public void waitForReady() throws InterruptedException {
        synchronized (lock) {
            while (!ready) {
                System.out.println("Waiting...");
                lock.wait(); // release the lock and waits
            }
            System.out.println("Proceeding");
        }
    }

    public void makeReady() {
        synchronized (lock) {
            ready = true;
            lock.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WaitNotifyBasic example = new WaitNotifyBasic();

        Thread waiter = new Thread(() -> {
            try {
                example.waitForReady();
            } catch (InterruptedException ignored) {}
        });

        Thread notifier = new Thread(example::makeReady);

        waiter.start();
        Thread.sleep(1000);
        notifier.start();
    }
}
