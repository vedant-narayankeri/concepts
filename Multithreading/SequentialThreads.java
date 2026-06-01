package Multithreading;
public class SequentialThreads
{
    //In Java every object has a built-in monitor lock (also called intrinsic lock)
    //Hence any object can be a lock
    //Methods - wait(), notify(), notifyAll()
    private static final Object lock = new Object();
    private static int currentThread = 1;

    public static void main(String[] args){
        Thread t1 = new Thread(() -> printInOrder(1, "Thread-1"));
        Thread t2 = new Thread(() -> printInOrder(2, "Thread-2"));
        Thread t3 = new Thread(() -> printInOrder(3, "Thread-3"));
        t1.start();
        t2.start();
        t3.start();
    }

    private static void printInOrder(int threadNumber, String threadName) {
       synchronized(lock){
            while(threadNumber != currentThread) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(threadName + " executed");
            currentThread++;
            lock.notifyAll();;
       }
    }
}
