package csci310group11.Implementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class CollageGenerator {

	private ArrayList<BufferedImage> images; //change to <BufferedImage> if necessary
	private ArrayList<BufferedImage> borderedImages;
	private Collage collage;

	public CollageGenerator() {
		this.images = new ArrayList<BufferedImage>();
		this.borderedImages = new ArrayList<BufferedImage>();
		this.collage = new Collage();
	}

	/**
	 * Driver method to complete Collage creation process.
	 * This method will call the GoogleCustomSearchAPI and retrieve the images to be compiled.
	 * Each image will be resized, then given a border.
	 * Each image will be complied into one BufferedImage stored in a Collage Object.
	 * 
	 * The Collage Object will be downloaded to the Server storage space and the url will be returned to the Servlet
	 * to be sent back out to the frontend.
	 * 
	 * @param topic the String of terms inputted by the user; this will be passed to the API to make th search
	 * @return URL of the collage on the server's storage system
	 */
	public String collageGeneratorDriver(String topic) {

		//API CALL HERE images = ArrayList<BufferedImages>
		this.resizeImages();
		this.addBorderToImages();
		this.compileCollage();
		return this.downloadCollage(this.collage);
	}

	/**
	 * Getter for the collage image
	 * 
	 * @return the BufferedImage collage
	 */
	public BufferedImage getCollage() {
		return this.collage.getCollageImage();
	}

	/**
	 * Resizes all the BufferedImages inside of images to be 1/20th of the size of the overall collage.
	 * 
	 * Takes the original image and draws it into a new BufferedImage with the proper dimensions
	 * 
	 * Notes:
	 * Should we set collage size 1st or make collage based of images entered?
	 */
	private void resizeImages() {
		//1/20th of collage dimensions
		BufferedImage collageImage = this.collage.getCollageImage();
		int resizeWidth = collageImage.getWidth()/20;
		int resizeHeight = collageImage.getHeight()/20;
		
		//Iterate through all images
		for(int i=0; i < images.size(); i++) {
			BufferedImage img = images.get(i);
			//New BufferedImage with 1/20th dimensions of collage
			BufferedImage resizeImg = new BufferedImage(resizeWidth, resizeHeight, img.getType());

			//Draws the img image into the size of the resizeImg
			Graphics2D graphics = resizeImg.createGraphics();
			graphics.drawImage(img, 0, 0, resizeWidth, resizeHeight, null);

			//replace BufferedImage in images with resizedImg
			images.set(i, resizeImg);
			graphics.dispose(); //not sure if needed
		}
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
		for(int i=0; i < images.size(); i++) {
			BufferedImage image = images.get(i);
			int width = image.getWidth();
			int height = image.getHeight();
			
			//Create image with enough space for 3px border
			BufferedImage borderedImage = new BufferedImage(width + 6, height + 6, image.getType());

			//Setting larger image to all white
			Graphics2D graphics = borderedImage.createGraphics();
			graphics.setPaint(new Color(0,0,0));
			graphics.fillRect(0, 0, borderedImage.getWidth(), borderedImage.getHeight());

			//Paint original image onto new borderedImage	
			graphics.drawImage(image, 2, 2, null);
			this.borderedImages.add(borderedImage);	
			graphics.dispose(); // not sure if needed check with both
		}
	}

	//Dummy method
	// public BufferedImage addBorderToImage(BufferedImage image) {
		
	// 	int width = image.getWidth();
	// 	int height = image.getHeight();
		
	// 	//Create image with enough space for 3px border
	// 	BufferedImage borderedImage = new BufferedImage(width + Constants.BORDER_WIDTH, height + Constants.BORDER_WIDTH, image.getType());

	// 	//Setting larger image to all white
	// 	Graphics2D graphics = borderedImage.createGraphics();
	// 	graphics.setPaint(Color.WHITE);
	// 	graphics.fillRect(0, 0, borderedImage.getWidth(), borderedImage.getHeight());

	// 	//Paint original image onto new borderedImage	
	// 	graphics.drawImage(image, Constants.BORDER_WIDTH/2, Constants.BORDER_WIDTH/2, null);	
	// 	graphics.dispose(); // not sure if needed check with both
	// 	return borderedImage;
	// }



	/*
	 * Cover corners, lay inside and see what happens
	 * 
	 */
	private void compileCollage() {

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

	


	// public static void main(String[] args) throws MalformedURLException {
	// 	CollageGenerator cg = new CollageGenerator();
	// 	try {
	// 		URL url = new URL("https://media.wired.com/photos/5a7cab6ca8e48854db175890/master/pass/norwayskier-915599900.jpg");
	// 		BufferedImage image = ImageIO.read(url);
	// 		BufferedImage borderedImage = cg.addBorderToImage(image);
	// 		File outFile = new File("borderedImage.png");
	// 		ImageIO.write(borderedImage, "png", outFile);
			

	// 	} catch (IOException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	}

	// }

}
