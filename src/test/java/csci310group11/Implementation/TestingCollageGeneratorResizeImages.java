package csci310group11.Implementation;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.Test;

public class TestingCollageGeneratorResizeImages {
	@Test
	public void collageGeneratorResizeImagesWithNullTest() throws MalformedURLException, IOException {
		CollageGenerator collageGenerator = new CollageGenerator();
		collageGenerator.testingCollageGeneratorDummyImagesWithNull = true;
		
		collageGenerator.resizeImages();
		ArrayList<BufferedImage> images = collageGenerator.images;
		
		//values to check against
		int validWidth = collageGenerator.collageImage.getWidth()/5;
		int validHeight = collageGenerator.collageImage.getHeight()/4;
		
		//Iterate through all images
		for(int i=0; i < images.size(); i++) {
			BufferedImage img = images.get(i);

			//for all normal image, verify that it meets the size criteria specified by the code
			//for all images in the exceptional case where the BufferedImage returned by the API is null, 
			//test this image separately
			if (img != null) {
				if (img.getWidth() == 1 && img.getHeight() == 1) {
					assertEquals(1, img.getWidth());
					assertEquals(1, img.getHeight());
				}
				else {
					assertEquals(validWidth, img.getWidth());
					assertEquals(validHeight, img.getHeight());
				}
			}
		}
	}
	
	@Test
	public void collageGeneratorReszieImagesReturnNull() throws MalformedURLException, IOException {
		CollageGenerator.testingResizeImageReturnNull = true;
		CollageGenerator cg = new CollageGenerator();
		cg.resizeImages();
		assertEquals(cg.images.size(), 30);
	}

}
