package multithreading;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanEx {
        //To ensure something runs only Once across threads
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    public static void main(String[] args){
        for(int i = 1;i<=5;i++){
            int id = i;
            new Thread(() -> initialize("Thread" + id)).start();
        }
    }

    private static void initialize(String name){
        if(initialized.compareAndSet(false, true)){
            System.out.println(name + "- I'm initializing the system!");
        } else {
            System.out.println( name + "- Already initialized, skipping");
        }
    }
}
