/**
 * Implementing a basic thread using runnable
 */
class Main_1 {

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("Running thread : " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        // implementing runnable interface
        Thread t1 = new Thread(new Task());
        t1.start();

        System.out.println("Main t1");

        // using the anonymous class
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running t1 : " + Thread.currentThread().getName());
            }
        });
        t2.start();

        // using lambda expression
        Thread t3 = new Thread(() -> System.out.println("Running t1 : " + Thread.currentThread().getName()));
        t3.start();
    }
}
