package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

public class TestingAPI {
	public static Api api = new GoogleCustomSearchApi();
	public static int sufficientImageAmount = 30;
	
	@Test
	public void testGoogleCustomAPI() throws MalformedURLException, IOException {
		List<BufferedImage> images = null;
		try {
			images = api.execute("dog");
		} catch (InsufficientImagesFoundError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assert(images.size() == sufficientImageAmount);
		
	}
	
	@Test
	public void testInsufficientImg() throws MalformedURLException, IOException {
		GoogleCustomSearchApi.testingInsufficientImagesFoundErrorFlag = true;
		try {
			api.execute("cat");
		} catch (InsufficientImagesFoundError e) {
			// TODO Auto-generated catch block
			return;
		}
	}

}