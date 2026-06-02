package multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerEx {
    private static final AtomicInteger counter = new AtomicInteger(0);
    
    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(() -> increment("Thread-1"));
        Thread t2 = new Thread(() -> increment("Thread-2"));
        Thread t3 = new Thread(() -> increment("Thread-3"));
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Final count: " + counter.get());
    }

    private static void increment(String threadName) {
        counter.incrementAndGet();//Similar to ++counter
        System.out.print(threadName + " done \n");
    }

    /**
     * Some methods
     * coutner.set(10) -> write
     * counter.getAndIncrement() -> counter++
     * coutner.addAndGet(5) -> counter += 5
     * counter.compareAndSet(10, 20) -> if value == 10 then set to 20 & return true
     * 
     * Note - compareAndSet(x, y) is an atomic operation
     * CAS is a single CPU instruction (read + compare + write happens atomically)
     * 
     * TODO - CAS (lock-free) vs Synchronized
     */
}
