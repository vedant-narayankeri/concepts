public class SingletonPattern {
    //If no private constructor, the default constructor is public & hence it can be instantiated multiple times -> defeating the purpose
    private SingletonPattern(){}

    //Volatile - Complex logic
    /**
     * When we run instance = new SingletonPattern()
     * Underlying 3 things happen - 
     * 1. Allocate memory for Singleton object
     * 2. Run constructor (initialize fields)
     * 3. Assign memory address to "instance" variable
     * 
     * CPU/Compiler can re-order these to any order for performance reasons -> called "instruction re-ordering"
     * 
     * Without volatile -
     * Thread A can do Step 1 & 3 -> allocate memoryu and then allocate instance a memory address
     * At this point instance is not NULL
     * If Thread B checks instance == null, evaluates to false and will get half-built object
     * 
     * With volatile -
     * 1. Prevents reordering - Steps 1, 2, 3 happen in order, no thread sees half-built object
     * 2. Visibility - When Thread A writes instance object, Thread B immediatly sees the update value (not stale CPU-cache copy)
     */
    private static volatile SingletonPattern instance;

    public static SingletonPattern getInstance(){
        if(instance == null){
            //SinglePattern.class - Class object is one per class in JVM
            //synchronized() method needs an object to lock on. Since getInstance() is a static method, there is no this. 
            //Hence we just use the class object itself
            //We just need to pass a single object that can be used as a lock for a static method
            synchronized (SingletonPattern.class){
                if(instance == null){
                    instance = new SingletonPattern();
                }
            }
        }
        return instance;
    }
}
