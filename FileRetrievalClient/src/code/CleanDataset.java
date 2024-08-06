package code;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CleanDataset {
		
	public  File cleanData(File document){
		File outputFile = new File(document.getName());

	        ArrayList<Character> delimitersList = new ArrayList<>();
	        try {
	            FileInputStream input = new FileInputStream(document);
	          
	            FileOutputStream output = new FileOutputStream(outputFile);
	            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
	            int numOfBytesRead;
	            int previousByte = 0;
	            int bufferSize = 1024;
	            int currentByte;
	            byte[] buffer = new byte[bufferSize];

	            while ((numOfBytesRead = input.read(buffer)) != -1) {
	            	for(int i = 0; i<numOfBytesRead;i++){
	                   currentByte = buffer[i];

	                if(Character.isLetterOrDigit((char)currentByte)){
	                    if(!delimitersList.isEmpty()){
	                        bufferedOutput.write(delimitersList.get(delimitersList.size() -1));
	                        delimitersList.clear();
	                    }
	                    bufferedOutput.write(currentByte);
	                    previousByte = currentByte;
	                }
	                else if(currentByte == '\r'){
	                }
	                 else if (currentByte == '\n'  || currentByte == ' ' || currentByte == '\t'  ) {
	                       if(previousByte != ' ' ){
	                         previousByte = currentByte;
	                         delimitersList.add((char)currentByte);
	                       }
	                 }
	                 else if(!Character.isLetterOrDigit((char) currentByte)){
	                	 if(previousByte != ' ') {
	                	 currentByte = ' ';
	                	 previousByte = currentByte;
	                	 bufferedOutput.write(currentByte);
	                 }
	               }
	            }
	        }
	            bufferedOutput.close();
	            input.close();
	        }
	        
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        return outputFile;
		}
}
	



