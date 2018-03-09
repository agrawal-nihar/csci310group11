package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;

import junit.framework.TestCase;

public class TestingImgToBase64String extends TestCase {
	
	@Test
	public void testImgToBase64String() throws IOException {
			
			//using dummy base 64 converter
			String expectedBase64String = Utility.readFromFile("imgToBase64Validation.txt");
			String imageURL = "https://vignette.wikia.nocookie.net/outfit7talkingfriends/images/8/8b/Talking_Tom_Cat_for_iPad.png/revision/latest?cb=20111217192245";
			URL url = null;
			try {
				url = new URL(imageURL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedImage image = null;
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String formatName = "png";
			
			//actual testing
			System.out.println(CollageGenerator.imgToBase64String(image, formatName));
//			PrintWriter pw = null;
//			try {
//				pw = new PrintWriter("text.txt");
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			pw.println(CollageGenerator.imgToBase64String(image, formatName));
			String actualBase64String = CollageGenerator.imgToBase64String(image, formatName); 
			
			//define substring length for acceptability
			Integer substringBegin = 0;
			Integer substringEnd = expectedBase64String.length(); //WHAT IS GOING ON HERE -- EXPECTED??
			
			//substrings to compare
			String actualBase64StringPart = actualBase64String.substring(substringBegin, substringEnd);
			String expectedBase64StringPart = expectedBase64String.substring(substringBegin, substringEnd);

			if (actualBase64StringPart.equals(expectedBase64StringPart)) {
				System.out.println("Strings are equal");
				System.out.println("actual is long: " + actualBase64String.length());
				System.out.println("expected is long: " + expectedBase64String.length());

			}
			assertEquals(actualBase64StringPart, expectedBase64StringPart);
			
		}
		
}
