package csci310group11.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.layout.BorderImage;

public class CollageGenerator {
	private ArrayList<String> imageUrls; //change to <BufferedImage> if necessary
	private ArrayList<BufferedImage> images;
	private ArrayList<BufferedImage> borderedImages;
	private BufferedImage collage;

	public CollageGenerator(ArrayList<String> urls) {
		this.imageUrls = new ArrayList<String>();
		this.images = new ArrayList<BufferedImage>();
		this.borderedImages = new ArrayList<BufferedImage>();
		this.collage = new BufferedImage();

		this.imageUrls = urls;
		for(int i=0; i < imageUrls.size(); i++) {
			URL url = new URL(imageUrls.get(i));
			this.images.add(ImageIO.read(url));
		}
		this.resizeImages();
		this.addBorderToImages();
		this.compileCollage();
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

	/**
	 * Getter for the collage image
	 * 
	 * @return the BufferedImage collage
	 */
	public BufferedImage getCollage() {
		return this.collage;
	}

	/**
	 * Responsible for adding a 3px white border to each image to be added to the collage.
	 * 
	 * Creates a new BufferedImage that is 6px taller and wider than the original BufferedImage in images.
	 * 
	 * Sets thes the graphics of the larger BufferedImage to white. Paints the original image onto the 
	 * new BufferedImage to create a 3px "border". Adds the bordred BuffereImage to this.borderedImages.
	 */
	private void addBorderToImages() {
		for(int i=0; i < image.size(); i++) {
			BufferedImage image = image.get(i);
			int width = image.getWidth();
			int height = image.getHeight();
			
			//Create image with enough space for 3px border
			BufferedImage borderedImage = new BufferedImage(width + 6, height + 6, image.getType());

			//Setting larger image to all white
			Graphics2D graphics = borderedImage.getGraphics();
			graphics.setPaint(new Color(0,0,0));
			graphics.fillRect(0, 0, borderedImage.getWidth(), borderedImage.getHeight());

			//Paint original image onto new borderedImage	
			graphics.drawImage(image, 2, 2, null);
			this.borderedImages.add(borderedImage);
			
		}
	}
	
	/**
	 * Resize: 1/20th of collage
	 */
	private void resizeImages() {

	}

	/*
	 * Cover corners, lay inside and see what happens
	 * 
	 */
	private void compileCollage() {

	}
}
