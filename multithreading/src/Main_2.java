import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Creating multiple threads
 * 1 java thread correspond to one system thread
 */
public class Main_2 {

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("Running thread : " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new Task());
            t1.start();
        }
        System.out.println("Main thread");
        System.out.println("=====================");;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.submit(new Task());
        }
    }
}
