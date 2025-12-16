package threads;

public class SleepExample {

    public static void main(String[] args) {
        System.out.println("Sleeping...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Awake!");
    }
}

