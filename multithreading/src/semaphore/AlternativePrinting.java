package semaphore;

import java.util.concurrent.Semaphore;

class AlternativePrinting {

    private final int n;
    final Semaphore runFoo, runBar;

    public AlternativePrinting(int n) {
        this.n = n;
        runFoo = new Semaphore(1);
        runBar = new Semaphore(0);
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            runFoo.acquire();
            printFoo.run();
            runBar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            runBar.acquire();
            printBar.run();
            runFoo.release();
        }
    }
}