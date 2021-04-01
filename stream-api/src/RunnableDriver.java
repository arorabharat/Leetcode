public class RunnableDriver {


    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("I am running");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("I am main thread");
    }
}
