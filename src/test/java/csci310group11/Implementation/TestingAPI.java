package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Google Custom API")
public class TestingAPI {
	public static Api api = new GoogleCustomSearchApi();
	public static int sufficientImageAmount = 30;
	
	@SuppressWarnings("null")
	@Test
	public void testGoogleCustomAPI() {
		List<BufferedImage> images = null;
		try {
			images = api.execute("dog");
		} catch (NullPointerException npe) {
			assert(images == null);
		} catch (InsufficientImagesFoundError iife) {
			assert(images.size() != sufficientImageAmount);
		}
		assert(images.size() == sufficientImageAmount);
		
		GoogleCustomSearchApi.testInsufficientImage = true;
		
		
	}

}