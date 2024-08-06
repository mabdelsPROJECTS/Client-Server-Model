package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSideEngine {
    // TO-DO keep track of the connection
	Socket socket;

    public ClientSideEngine() {
        
        // TO-DO implement constructor
    }

    public void indexFiles(File fileToIndex, String docName) {
    	
		
		
    	Map<String, Integer> wordCounts = new HashMap<>();
    	ArrayList<String> serverArrayList = new ArrayList<>();
    	try {
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       	 FileInputStream input = new FileInputStream(fileToIndex);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
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
             for(String word: wordCounts.keySet()) {
            	 int value = wordCounts.get(word);
            	 String stringToSend = word + " " + value;
            	 out.println("Index " + stringToSend + " " + docName);
             }
             //System.out.println(docName);
             //out.println(docName);
             out.println("End");
             reader.close();
    	}
    	
    	catch (IOException e) {
    	     e.printStackTrace();
    	        }
    	fileToIndex.delete();
    	
    	    }
        // TO-DO implement index files method
        // for each file read and count the words and send the counted words to the server
    
    
    public ArrayList<String> searchFiles(String[] searchWords) {
    	
    	String outputWord = "";
    	ArrayList<String> inputArr = new ArrayList<>();
    	try {
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
    		for(String word:searchWords) {
    			word = word.trim();
    		outputWord = outputWord + word + " "; 
    		//out.println("Search " + word);
    	}
    		System.out.println(outputWord);
    		out.println(outputWord);
    		String input = " ";
    		//input = in.readLine();
    		while (!(input = in.readLine()).equals("SearchEnd")) {
    		    //System.out.println(input);
    			inputArr.add(input);
    		}
    		//out.println("EndSearch");
        // TO-DO implement search files method
        // for each term contact the server to retrieve the list of documents that contain the word
        // combine the results of a multi-term query
        // return top 10 results
    }
    	catch (IOException e) {
   	     e.printStackTrace();
   	        }
    	return inputArr;
   	    }

    public void openConnection(String address, int portNumb) {
    	try {
			 socket = new Socket(InetAddress.getByName(address), portNumb);
			System.out.println("Connection Successful!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TO-DO implement connect to server
        // create a new TCP/IP socket and connect to the server
    }

    public void closeConnection() {
    	try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TO-DO implement disconnect from server
        // close the TCP/IP socket
    }
}
