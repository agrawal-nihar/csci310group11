//package csci310group11.Implementation;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.junit.Test;
//
//public class CollageGeneratorCompileTest {
//
//	@Test
//	public void test() {
//		CollageGenerator collageGenerator = new CollageGenerator();
//		collageGenerator.testingCollageGeneratorDummyImages = true;
//		collageGenerator.testingRotation = true;
//		
//		collageGenerator.collageGeneratorDriver("dog");
//		String returnURL = collageGenerator.returnURL;
//		
//		//write URL to file
//		FileReader fr = null;
//		try {
//			fr = new FileReader("collageBase64String.txt");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String validationBase64String = fr.
//				
//
//	  byte[] encoded = FileUtils.readFileToString(new File("collageBase64String.txt")); //FIX THIS!!!!
//	  return new String(encoded, encoding);
//			
//		
//		System.out.println("Validation base 64 string length: " + validationBase64String.length());
//		System.out.println("Actual base 64 string length: " + returnURL.length());
//
//		assertEquals(validationBase64String, returnURL);
//
//	}
//
//}
