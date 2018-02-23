package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

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

	private String downloadCollage(Collage collage) {
		String filename = "";
		BufferedImage image = collage.getCollageImage();
	
		try {
			//get destination path in assets folder of server
			File assetsDirectory = new File(System.getProperty("user.dir") + "/assets");
			assetsDirectory.mkdir(); //no exception if directory already exists
			
			filename += System.getProperty("user.dir") + "/assets/"; //current system context path
			filename += "topicName";
			filename += System.currentTimeMillis() + ".png";    
			File outputFile = new File(filename);
			ImageIO.write(image, "png", outputFile);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return filename;
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
