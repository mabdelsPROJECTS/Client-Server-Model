package code;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ProcessingEngine {
	 
     
    private IndexStore store;
     CleanDataset cleanDataset = new CleanDataset();
     Map<String, Integer> wordCounts = new HashMap<>();
     ArrayList<Thread> threadsList = new ArrayList<>();
     private int numbOfThreads;

    public ProcessingEngine(IndexStore store, int numbOfThreads) {
        this.store = store;
        this.numbOfThreads = numbOfThreads;
        
        // TO-DO implement constructor
    }
    

    public void indexFiles(String directoryPath) {
    	File cleanedFile = new File("cleanedFile");
    	File fileDirectory = new File(directoryPath);
    	File[] folders = fileDirectory.listFiles();
    	int folderNumb;
    	int numbOfDocs = 0;
    	
    	

    	ArrayDeque<File> fileQueue = new ArrayDeque<>();
    	for(File folder: folders) {
    		fileQueue.add(folder);
    	}
    	ArrayList<Thread> threads = new ArrayList<Thread>();
    	for(int i = 0; i< numbOfThreads; i++) {
    	ThreadClass thread = new ThreadClass(folders, store, cleanDataset, fileQueue);
    	Thread mythread = new Thread(thread);
    	thread.getThreadName(mythread.getName());
    	threads.add(mythread);
    	mythread.start();
    	}
    	while(!fileQueue.isEmpty()) {
    		File folder = fileQueue.pop();
    		File[] documents = folder.listFiles();
    		for(File document: documents) {
    			numbOfDocs++;
    			cleanedFile = cleanDataset.cleanData(document);
    			String folderString = folder.getName() +"/" + document.getName();
    			store.insertIndex(cleanedFile, folderString, wordCounts );
    			
    		}
    		
    	}
    	for(Thread thr: threads) {
    		try {
				thr.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//System.out.println("Main Thread -> " + numbOfDocs);
    }
    
    public ArrayList<String> searchFiles(String[] searchArr) {
    	ArrayList<String> dataArr = store.lookupIndex(searchArr);
    	return dataArr;
    }

    public void stopWorkers() {
        // TO-DO implement gracefully stop workers
    }
    public void executeSearchQueries() {
    	store.search100Queries();
    }
}
