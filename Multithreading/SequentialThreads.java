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
        //thread.start() -> JVM creates new OS thread -> Internally calls thread.run() -> Executes runnable lambda supplied
        //Note - For a busy/multi-core CPU, T2, T3 could win race. Even though usually T1 runs first but we never know
        //Analogy -> Printer submitted 3 jobs, potentially J2 runs before as the file is smaller and hence downloads faster
        //Our job is to submit the Job, but Printer decides the order
        //We use synchronization for the Guarantee 
        t1.start();
        t2.start();
        t3.start();
    }

    private static void printInOrder(int threadNumber, String threadName) {
       synchronized(lock){
            //Need while() loop because there is possibility of Spurious wakeups
            //JVM spec allows threads to wake up randomly even without noitfy() being called
            //In case we put logic in if() condition, then we just exit if and execute the code below, leading to random behaviour
            //Java concurrency guide - Standard rule
            while(threadNumber != currentThread) {
                try {
                    lock.wait();//release lock and wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(threadName + " executed");
            currentThread++;
            lock.notifyAll();//Wake up all waiting threads
       }
    }
}
