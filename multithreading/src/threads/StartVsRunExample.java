package threads;

public class StartVsRunExample {

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            System.out.println("Running in: " + Thread.currentThread().getName());
        });

        t.start(); // NEW thread
        t.run();   // runs in main thread
    }
}

