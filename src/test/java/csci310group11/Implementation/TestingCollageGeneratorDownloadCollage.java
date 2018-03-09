package csci310group11.Implementation;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

public class TestingCollageGeneratorDownloadCollage {

	@Test
	public void collageGeneratorDownloadCollageTest() throws MalformedURLException, IOException {
		CollageGenerator collageGenerator = new CollageGenerator();
		collageGenerator.testingDownloadCollage = true;
		
		//make call
		String actualBase64String = collageGenerator.downloadCollage(null);;
		
		//validation text
		String validationFile = "downloadCollageBase64String.txt";
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
//		System.out.println("Actual base 64 string length: " + actualBase64String.length());
    
		String degradedActualBase64String = actualBase64String.substring(0, expectedBase64String.length());
		assertEquals(expectedBase64String, degradedActualBase64String);
	}

}

