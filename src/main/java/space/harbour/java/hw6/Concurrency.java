package hw6;

import java.util.concurrent.atomic.AtomicInteger;

public class Concurrency {
    static final Integer N = 100_000;
    static AtomicInteger counter = new AtomicInteger(0);

    public static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                System.out.println(getName() + ": " + counter.incrementAndGet());
            }
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.setName("T1");
        t1.start();

        MyThread t2 = new MyThread();
        t2.setName("T2");
        t2.start();
    }
}
