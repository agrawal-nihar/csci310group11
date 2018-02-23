package csci310group11.Implementation;

import java.awt.image.BufferedImage;

public class CollageGenerator {
	private ArrayList<String> imageUrls; //change to <BufferedImage> if necessary
	private ArrayList<BufferedImage> borderedImages;

	public CollageGenerator(ArrayList<String> urls) {
		this.images = new ArrayList<BufferedImage> borderedImages;
		this.imageUrls = new ArrayList<String>();
		this.imageUrls = urls;
		for(int i=0; i < imageUrls.size(); i++) {
			URL url = new URL(imageUrls.get(i));
			this.borderedImages.add(ImageIO.read(url));
		}
	}

	public void addBorderToImages(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage borderedImage = new BufferedImage(width + 6, height + 6, image.getType());
		this.borderedImages.add(borderedImage);
	}
	
	/**
	 * Resize: 1/20th of collage
	 */
	public void resizeImage(BufferedImage image) {

	}

	/*
	 * Cover corners, lay inside and see what happens
	 * 
	 */
}
