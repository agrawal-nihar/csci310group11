package csci310group11.Implementation;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

public class TestingCollageGeneratorCompile {

	@Test
	public void test() throws IOException, InsufficientImagesFoundError {
		CollageGenerator collageGenerator = new CollageGenerator();
		collageGenerator.testingCollageGeneratorDummyImages = true;
		collageGenerator.testingRotation = true;
		
		collageGenerator.collageGeneratorDriver("dog");
		String returnURL = collageGenerator.returnURL;
			
		//validation text
		String validationFile = "collageBase64String.txt";
		String lineRead = null;
		
		//write URL to file
		FileReader fr = null;
		try {
			fr = new FileReader(validationFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String expectedBase64String = "";
    FileReader fileReader = null;
    try {
			fileReader = new FileReader(validationFile);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((lineRead = bufferedReader.readLine()) != null) {
//			    System.out.println(lineRead.substring(0, 10));
			    
			    expectedBase64String += lineRead;
			}
			
			bufferedReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();   
		} 
			
//		System.out.println("Validation base 64 string length: " + expectedBase64String.length());
//		System.out.println("Actual base 64 string length: " + returnURL.length());
//
//    		System.out.println("Expected String: " + expectedBase64String.substring(0, 15));
//	String expectedBase64StringToTest = returnURL.substring(0, actualBase64String.length());
   		
    		String actualBase64String = returnURL;		
    		String degradedActualBase64String = actualBase64String.substring(0, expectedBase64String.length());
    		assertEquals(expectedBase64String, degradedActualBase64String);
    		
//		 assertEquals(expectedBase64String, actualBase64StringToTest);

	}
	
	@Test
	public void testingRotation() {
		CollageGenerator collageGenerator = null;
		try {
			collageGenerator = new CollageGenerator();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		collageGenerator.testingRotation = false;
		collageGenerator.resizeImages();
		assertEquals(collageGenerator.images.size(), 30);
	}

}
