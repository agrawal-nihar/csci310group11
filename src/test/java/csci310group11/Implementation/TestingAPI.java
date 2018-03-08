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
	public void testGoogleCustomAPI() throws InsufficientImagesFoundError, MalformedURLException, IOException {
		List<BufferedImage> images = null;
		images = api.execute("dog");
		assert(images.size() == sufficientImageAmount);
		
		GoogleCustomSearchApi.testingInsufficientImagesFoundErrorFlag = true;
		api.execute("cat");
		
	}

}