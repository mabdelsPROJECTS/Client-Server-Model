package code;



import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileRetrievalEngine {

	public static void main(String[] args) {
		
		int numbOfThreads = Integer.parseInt(args[0]);
		
		Lock lock = new ReentrantLock();
		IndexStore store = new IndexStore(lock);
        ProcessingEngine engine = new ProcessingEngine(store, numbOfThreads);
        AppInterface appInterface = new AppInterface(engine);
        
       
        

        appInterface.readCommands();
        
    }

	

	

}
