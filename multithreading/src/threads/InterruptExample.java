package threads;

/**
 * ==========================================================
 * Why Thread.currentThread().interrupt() Is Needed
 * ==========================================================
 * <p>
 * Threads in Java support cooperative cancellation using
 * an "interrupt flag".
 * <p>
 * Calling {@link Thread#interrupt()}:
 * - Sets the thread's interrupt flag
 * - Requests the thread to stop when safe
 * <p>
 * ------------------------------------------
 * The subtle problem
 * ------------------------------------------
 * <p>
 * When a thread is blocked in methods such as:
 * - {@link Thread#sleep(long)}
 * - {@link Object#wait()}
 * - {@link java.util.concurrent.locks.Condition#await()}
 * <p>
 * and it is interrupted:
 * 1. An {@link InterruptedException} is thrown
 * 2. The JVM AUTOMATICALLY CLEARS the interrupt flag
 * <p>
 * After catching the exception, the thread is no longer
 * marked as interrupted.
 * <p>
 * ------------------------------------------
 * Why this is dangerous
 * ------------------------------------------
 * <p>
 * Interrupts are commonly used as cancellation signals.
 * <p>
 * Higher-level code may rely on:
 * <p>
 * while (!Thread.currentThread().isInterrupted()) {
 * doWork();
 * }
 * <p>
 * If {@code InterruptedException} is caught and ignored,
 * the interrupt request is silently lost and the thread
 * continues running.
 * <p>
 * ------------------------------------------
 * The correct cooperative behavior
 * ------------------------------------------
 * <p>
 * If a method catches {@link InterruptedException} but does
 * NOT fully terminate the thread, it must restore the
 * interrupt flag:
 * <p>
 * Thread.currentThread().interrupt();
 * <p>
 * This preserves the cancellation request for outer layers
 * of the call stack.
 * <p>
 * ------------------------------------------
 * When restoring is optional
 * ------------------------------------------
 * <p>
 * If the thread exits immediately after catching the
 * exception (e.g., return or throw), restoring the flag
 * is not strictly required.
 * <p>
 * Restoring is still considered best practice unless the
 * thread is guaranteed to terminate.
 * <p>
 * ------------------------------------------
 * Golden rule
 * ------------------------------------------
 * <p>
 * If you catch {@link InterruptedException} and continue
 * execution, restore the interrupt flag.
 * <p>
 * ------------------------------------------
 * Mental model
 * ------------------------------------------
 * <p>
 * interrupt()   → cancellation request
 * exception     → delivery of request
 * restoring flag→ forwarding request upstream
 * <p>
 * Catching an interrupt does NOT mean it has been handled.
 */
public class InterruptExample {

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Working...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // interruption while blocked
                    System.out.println("this catch block");
                    System.out.println(Thread.currentThread().isInterrupted()); // thread interrupt marker is set to false;
                    Thread.currentThread().interrupt(); // thread interrupt marker is set to true;
                    System.out.println(Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt(); // thread interrupt marker is set to true; operation is idempotent
                    System.out.println(Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt(); // restore flag
                    System.out.println(Thread.currentThread().isInterrupted());
                }
            }
            System.out.println("Worker interrupted, exiting");
        });

        worker.start();
        Thread.sleep(1500);
        worker.interrupt();
    }
}
