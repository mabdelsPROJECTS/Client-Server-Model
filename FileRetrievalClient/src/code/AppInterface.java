package code;

import java.lang.System;
import java.util.ArrayList;
import java.util.Scanner;

public class AppInterface {
    private ProcessingEngine engine;

    public AppInterface(ProcessingEngine engine) {
        this.engine = engine;

        // TO-DO implement constructor
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
                engine.stopWorkers();
                break;
            }
            
            // if the command begins with index, index the files from the specified directory
            else if (command.length() >= 5 && command.substring(0, 5).compareTo("index") == 0) {
            	double startTime = System.currentTimeMillis();
                String dots = sc.next();
                String directoryPath = dots.substring(3);
                engine.indexFiles(directoryPath);
                double endTime = System.currentTimeMillis();
                double totalEndTime = (endTime - startTime)/ 1000.0;
                System.out.println("Completed indexing in " + totalEndTime + " seconds");
                continue;
            }
            
            else if(command.equals("search100")) {
            	engine.executeSearchQueries();
            	}

            // if the command begins with search, search for files that matches the query
            else if (command.length() >= 6 && command.substring(0, 6).compareTo("search") == 0) {
            	double startTime = System.currentTimeMillis();
            	String searchLine = sc.nextLine();
            	String[] searchLineSplit = searchLine.split("AND");
            	
            	ArrayList<String> dataArr = engine.searchFiles(searchLineSplit);
            	double endTime = System.currentTimeMillis();
            	double totalEndTime = (endTime - startTime);
            	System.out.println("Search completed in " + totalEndTime + " milliseconds" );
            	System.out.println("Search results (top 10):");
            	for(String string: dataArr) {
            	System.out.println(string);
            	}
            	
                continue;
            }
            
            
            	
        
            else {
            System.out.println("unrecognized command!");
        }
        }
    }
}
