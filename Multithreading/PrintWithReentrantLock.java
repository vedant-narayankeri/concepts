package multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintWithReentrantLock {
    private static final int MAX = 100;
    private static int currentThreadNumber = 1;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args){
        Thread t1 = new Thread(() -> printNumbers(0, "thread-1"));
        Thread t2 = new Thread(() -> printNumbers(1, "thread-2"));
        Thread t3 = new Thread(() -> printNumbers(2, "thread-3"));
        t1.start();
        t2.start();
        t3.start();
    }

    private static void printNumbers(int threadNumber, String threadName) {
        lock.lock();
        try{
            //We run logic until we go beyond MAX, don't worry about currentThread number for the bounds
            while(currentThreadNumber <= MAX){
            //You send a signal when it is done
                if((currentThreadNumber-1)%3 == threadNumber){
                    System.out.println(currentThreadNumber + ": " + threadName);
                    currentThreadNumber++;
                    condition.signalAll();
                } else {
                    //Like wait() method - sleeps and releases lock
                    condition.await();
                }
            }
            condition.signalAll();//Need to inform yourself so that we can exit the while loop. Say T1 calls signalAll, T2,T3 check while condition they are done but still T1 stuck.
            //When we put singalAll() outside while() then both T2,T3 notify T1 and we can end program
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        } finally {
            //We reach here once we exceed MAX
            lock.unlock();
        }
    }


    //Benefits of using Reentrant lock over synchronized
    /* 
        1. if(lock.tryLock(2, TimeUnit.SECONDS)) -> Don't block forever, give up if you don't acquire lock in 2 seconds
        2. new ReentrantLock(true) -> FIFO - longest waiting thread gets lock first, we get Fairness
        3. We can have separate condition per thread - 
            Condition t1Turn = lock.newCondition();
            This can enable us to conditionally signal particular threads.

            //Maitain list of conditions
            Condition[] conditions = {
                lock.newCondition(), lock.newCondition(), lock.newCondition()
            }
            
            Usage -
            condition[threadId].await()
            condition[nextThreadId].signal()
    */
}
