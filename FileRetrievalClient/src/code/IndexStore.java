package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class IndexStore {
	
	 List<Map<String, String>> listOfMaps = new ArrayList<>();
	 ArrayList<String> allTheWords = new ArrayList<>();
	 public Lock lock;

    public IndexStore(Lock lock) {
        // TO-DO implement constructor
    	this.lock = lock;
    }
    
    public void insertIndex(File document, String folderString, Map<String, Integer> wordCounts) {
    	try {
    	 FileInputStream input = new FileInputStream(document);
         BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    	  Map<String, Integer> temp = new HashMap<>();
          String line;
          while((line = reader.readLine()) != null){
        	  
              String[] inputSplit = line.split(" ");
              for(String word: inputSplit){
            	  
                  if(!wordCounts.containsKey(word)){
                	  wordCounts.put(word, 1);
                	 }
                  else if(wordCounts.containsKey(word)){
                	  		int count = wordCounts.get(word);
                	  		wordCounts.put(word, count + 1);
                              }
              }
              
            	  
              }
          lock.lock();
          Map<String, String> finalMap = new HashMap<>();
              
          for(String word: wordCounts.keySet()) {
        	  finalMap.put(word, folderString + " " + wordCounts.get(word));
        	  allTheWords.add(word);
        	  //System.out.println(allTheWords.size());
        	 }
          
          wordCounts.clear();
          listOfMaps.add(finalMap);
         lock.unlock();
        // TO-DO implement index insert method
          reader.close();
          lock.lock();
          document.delete();
          lock.unlock();
    }
    
    	
        catch (IOException e) {
     e.printStackTrace();
        }
    }

    public ArrayList<String> lookupIndex(String[] searchArr) {
    	boolean breaks;
    	ArrayList<String> dataList = new ArrayList<>();
    	Map<String, Integer> temp = new HashMap<>();
        for(Map<String, String> hashMap: listOfMaps) {
        	breaks = false;
        	int valueOfAllWords = 0;
        	String folderDir = " ";
        	
        	for(String word: searchArr) {
        		word = word.trim();
        		if(hashMap.containsKey(word)) {
        		String value = hashMap.get(word);
        		String[] valueSplit = value.split(" ");
        		 folderDir = valueSplit[0];
        		int count = Integer.parseInt(valueSplit[1]);
        		valueOfAllWords += count;
        		}
        		else {
        			breaks = true;
        			break;
        		}
        		}
        	if(!breaks) {
        	temp.put(folderDir, valueOfAllWords);
        }
        	
        }
        
        List<Map.Entry<String, Integer>> sortedHashMap = new ArrayList<>(temp.entrySet());
        sortedHashMap.sort(Map.Entry.comparingByValue());
        for (int i = sortedHashMap.size() - 1, j = 0; i >= 0 && j < 10; i--, j++) {
        	Map.Entry<String, Integer> entry = sortedHashMap.get(i);
            String word = entry.getKey();
            int value = entry.getValue();
            String fullAnswer = "* " + word + " " + value;
            dataList.add(fullAnswer);
        }
        return dataList;
        }
    
    public void search100Queries() {
    	double start = System.nanoTime();
    	ArrayList<String> dataList = new ArrayList<>();
    	List<Map.Entry<String, Integer>> singleWordMap = new ArrayList<>();
    	double totalTimeForSearch = 0;
    	//Map<String, Integer> temp = new HashMap<>();
    	for(int i = 0; i < 100; i++) {
    		Map<String, Integer> temp = new HashMap<>();
    		int valueOfAllWords = 0;
    		Random random = new Random();
    		int randomNumb = random.nextInt(allTheWords.size());
    		String randomWord = allTheWords.get(randomNumb);
    		//System.out.println("Randomly Selected Word -> " + randomWord);
    		//System.out.println();
    		String folderDir = " ";
    		for(Map<String, String> hashMap: listOfMaps) {
    			
    			if(hashMap.containsKey(randomWord)) {
    				String value = hashMap.get(randomWord);
            		String[] valueSplit = value.split(" ");
            		 folderDir = valueSplit[0];
            		int count = Integer.parseInt(valueSplit[1]);
            		valueOfAllWords += count;
            		temp.put(folderDir, count);
            		}
    		}
    		singleWordMap.addAll(temp.entrySet());
    		singleWordMap.sort(Map.Entry.comparingByValue());
    		for (int j = singleWordMap.size() - 1, k = 0; j >= 0 && k < 10; j--, k++) {
            	Map.Entry<String, Integer> entry = singleWordMap.get(j);
            	String word = entry.getKey();
                int value = entry.getValue();
                String fullAnswer = "* " + word + " " + value;
                dataList.add(fullAnswer);
                //System.out.println(fullAnswer);
                
    		
    	}
    		singleWordMap.clear();
    		//System.out.println("==========================================================");
    	}
    	
    		
    		double end = System.nanoTime();
    		double total = (end - start) /1000.0;
    		totalTimeForSearch += total;
    		
    	double averageSearchTime = totalTimeForSearch / 100;
    	String format = String.format("%.1f", averageSearchTime);
    	System.out.println("Average Time Taken: " + format + " microseconds");
    }
}
