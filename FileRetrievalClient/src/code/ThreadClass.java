package code;

import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class ThreadClass implements Runnable {

	private File[] folders;
	private IndexStore store;
	private CleanDataset cleanDataset;
	private int folderNumb;
	Map<String, Integer> wordCounts = new HashMap<>();
	ArrayDeque<File> fileQueue;
	private String threadName;
	
	public ThreadClass(File[] folders, IndexStore store, CleanDataset cleanDataset, ArrayDeque<File> fileQueue) {
		this.folders = folders;
		this.store = store;
		this.cleanDataset = cleanDataset;
		this.fileQueue = fileQueue;
	}
	
	@Override
	public void run() {
		int numbOfDocs = 0;
		while(!fileQueue.isEmpty()) {
		File cleanedFile = new File("cleanedFile");
		File folder = fileQueue.poll();
		if(folder != null) {
		File[] documents = folder.listFiles();
		for(File document: documents) {
			numbOfDocs++;
			cleanedFile = cleanDataset.cleanData(document);
			String folderString = folder.getName() +"/" + document.getName();
			store.insertIndex(cleanedFile, folderString, wordCounts );
			}
		}
		}
		//System.out.println(threadName + " -> " +numbOfDocs);
	}
	public void getThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	}
		
	



