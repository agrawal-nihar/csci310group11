package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Utility {
	
	//helper method to clear all logging files
	public static void clearAllLoggingFiles(ArrayList<String> list) throws FileNotFoundException {
		for (int i = 0; i < list.size(); i++) {
			clearFile(list.get(i));
		}
	}
	
	
	//helper method to clear log file 
	public static void clearFile(String filename) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(filename);
		writer.print("");
		writer.close();
	}
	
	//helper method to pass degree of rotation to log file
	public static void writeToFile(String data, String filename) throws IOException {
		System.out.println("Supposed to write: " + data + " to " + filename);
		FileWriter fileWriter = new FileWriter(filename, true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		bw.write(data + "\n");

		fileWriter.flush();
		bw.flush();
		fileWriter.close();
		bw.close();
		
		//write URL to file
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//helper method to read from file
	public void readFromFile(String filename) {
		System.out.println(filename + " PRINTOUT is: ");
		String lineRead = "";
		String fileContents = "";
	  FileReader fileReader = null;
	  try {
			fileReader = new FileReader(filename);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((lineRead = bufferedReader.readLine()) != null) {
			    
			    fileContents += lineRead;
			}
			
			bufferedReader.close();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();   
		} 
	  System.out.println(fileContents);
	}
	
	public static void printImageByteSize(BufferedImage image, String destination) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		writeToFile(String.valueOf(imageInByte.length), destination);
	}
	
	public static void printAllCollagesBase64String(ArrayList<String> data, String allCollagesFile) throws IOException {
		clearFile(allCollagesFile);
		for (int i = 0; i < data.size(); i++) {
			writeToFile(data.get(i), allCollagesFile);
		}
	}

}
