package code;

import java.io.File;
import java.lang.System;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientAppInterface {
    private ClientSideEngine engine;
    private CleanFolder cleanFolder = new CleanFolder();

    public ClientAppInterface(ClientSideEngine engine) {
        this.engine = engine;

        // TO-DO implement constructor
        // keep track of the connection with the client
    }

    public void readCommands() {
        // TO-DO implement the read commands method
        Scanner sc = new Scanner(System.in);
        String command;
        
        while (true) {
            System.out.print("> ");
            
            // read from command line
            command = sc.next();

            // if the command is quit, terminate the program       
            if (command.compareTo("quit") == 0) {
                engine.closeConnection();
                break;
            }

            // if the command begins with connect, connect to the given server
            if (command.length() >= 7 && command.substring(0, 7).compareTo("connect") == 0) {
            	String ipAddress = sc.next();
            	String portNumber = sc.next();
            	int port = Integer.parseInt(portNumber);
            	engine.openConnection(ipAddress, port);
                // TO-DO implement index operation
                // call the connect method from the server side engine
                continue;
            }
            
            // if the command begins with index, index the files from the specified directory
            if (command.length() >= 5 && command.substring(0, 5).compareTo("index") == 0) {
            	double startTime = System.currentTimeMillis();
                String dots = sc.next();
                String directoryPath = dots.substring(3);
                File folderDir = new File(directoryPath);
                File[] documents = folderDir.listFiles();
                for(File document: documents) {
                	String docName = folderDir.getName() + "/" + document.getName();
                File cleanedDoc = cleanFolder.cleanData(document);
                engine.indexFiles(cleanedDoc, docName);
                }
                double endTime = System.currentTimeMillis();
                double totalTime = (endTime - startTime)/1000.0;
                System.out.println("Completed indexing in " + totalTime + " seconds");
                continue;
            }

            // if the command begins with search, search for files that matches the query
            if (command.length() >= 6 && command.substring(0, 6).compareTo("search") == 0) {
            	//System.out.println("Search Function");
            	double startTime = System.currentTimeMillis();
            	String searchLine = sc.nextLine();
            	String[] searchLineSplit = searchLine.split("AND");
            	ArrayList<String> inputArr = engine.searchFiles(searchLineSplit);
            	double endTime = System.currentTimeMillis();
            	double totalTime = (endTime - startTime)/1000.0;
            	System.out.println("Searching completed in " + totalTime + " seconds");
            	System.out.println("Search results (top 10): ");
            	for(String word: inputArr) {
            		System.out.println(word);
            	}
                // TO-DO implement index operation
                // extract the terms and call the server side engine method to search the terms for files
                continue;
            }

            System.out.println("unrecognized command!");
        }

        sc.close();
    }
}