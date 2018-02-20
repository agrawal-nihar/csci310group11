package csci310group11.Implementation;

import java.awt.image.BufferedImage;


public class CollageGenerator {
	public BufferedImage addBorderToImages(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage borderedImage = new BufferedImage(width + 6, height + 6, image.getType());
		
		
		
		return borderedImage;
	}
	
	/**
	 * Resize: 1/20th of collage
	 */
	
	/*
	 * make images 1/2*totalarea and lay in center of each quad
	 * rest of images must be 2/13 * totalarea
	 * lay so that they all make it onto the page
	 * 
	 */
}
