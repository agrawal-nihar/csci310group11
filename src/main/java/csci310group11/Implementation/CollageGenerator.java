package csci310group11.Implementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class CollageGenerator {

	private ArrayList<BufferedImage> images; //change to <BufferedImage> if necessary
	private ArrayList<BufferedImage> borderedImages;
	private BufferedImage collageImage;
	private GoogleCustomSearchApi api;

	public CollageGenerator() {
		this.images = new ArrayList<BufferedImage>();
		this.borderedImages = new ArrayList<BufferedImage>();
		this.collageImage = new BufferedImage(1000, 750, BufferedImage.TYPE_INT_ARGB);
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
//		try {
//			this.images = (ArrayList<BufferedImage>) this.api.execute(topic); //API call
//			System.out.println("After images API Call");
//		} catch (InsufficientImagesFoundError iife) { //Error is thrown if less than 30 images are found
//			System.out.println("iife: " + iife.getMessage());
//			return null;
//		}
//
//		this.resizeImages();
//		this.addBorderToImages();
//		this.compileCollage();
//
//		Collage collage = new Collage(this.collageImage, topic);
		
		Collage collage = null;
		
		//NIHAR --
		String returnURL = downloadCollage(collage);
//		allCollages.put(returnURL, collage.getCollageImage());
		return returnURL;
	}

	/**
	 * Resizes all the BufferedImages inside of images to be 1/20th of the size of the overall collage.
	 * 
	 * Each images will be 1/5 as wide as the collage and 1/4 as tall as the collage.
	 * These newly sized images will replace the original images in this.images.
	 */
	private void resizeImages() {
		//1/20th of collage dimensions
		int resizeWidth = this.collageImage.getWidth()/5;
		int resizeHeight = this.collageImage.getHeight()/4;
		
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
			
			graphics.dispose(); //releases the resources used by graphics
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
		//iterate through every image
		for(int i=0; i < images.size(); i++) {
			BufferedImage image = images.get(i);
			int width = image.getWidth();
			int height = image.getHeight();
			
			//Create image with enough space for 3px border
			BufferedImage borderedImage = new BufferedImage(width + 2*Constants.BORDER_WIDTH, height + 2*Constants.BORDER_WIDTH, image.getType());

			//Setting larger image to all white
			Graphics2D graphics = borderedImage.createGraphics();
			graphics.setPaint(Color.WHITE);
			graphics.fillRect(0, 0, borderedImage.getWidth(), borderedImage.getHeight());

			//Paint original image onto new borderedImage	
			graphics.drawImage(image, Constants.BORDER_WIDTH, Constants.BORDER_WIDTH, null);
			this.borderedImages.add(borderedImage);	
			
			graphics.dispose(); //releases the resources used by graphics
		}
	}

	/*
	 * Responsible for creating the this.collageImage.
	 * 
	 * Sets the collage to all white and then paints the images in this.borderedImages onto this.collageImage.
	 * Rotates each individual image and then paints onto this.collageImage in order to allow for cropping
	 * at the borders. 
	 * 
	 * The basic layout of the collage is 5 rows of 6 images in a "grid".
	 * Minor adjustments are made to ensure that the border of the collage are covered regardless of individual
	 * rotation. 
	 */
	private void compileCollage() {
		Graphics2D graphics = this.collageImage.createGraphics();
		graphics.setPaint(Color.WHITE); //check for "whitespace"
		graphics.fillRect(0, 0, this.collageImage.getWidth(), this.collageImage.getHeight());

		for(int r=0; r < 5; r++) { //5 rows of images
			for(int c = 0; c < 6; c++) { //6 columns of images
				BufferedImage currImage = borderedImages.get(6*r + c); //retrieves proper borderedImage
				int row = this.collageImage.getHeight()/5 * r; //calculation for y-coordinate

				//Adjustments to ensure border coverage
				if(r == 0) {
					row -= 25;
				}
				if(r == 1) {
					row -= 10;
				}
				if(r == 4) {
					row += 2;
				}
				
				int col = this.collageImage.getWidth()/6 * c; //calculation for x-coordinate
				
				//Adjustments to ensure border coverage
				if(c == 0) {
					col -= 20;
				}
				if(c == 5) {
					col += 10;
				}

				//Helper method to rotate and draw the currImage
				this.rotateAndDrawImage(currImage, row, col);
			}
		}
	}

	/**
	 * Helper method to rotate images. Will draw them onto this.collageImage
	 * 
	 * @param image the BufferedImage to be drawn
	 * @param row the y-coordinate in this.collageImage
	 * @param col the x-coordinate in this.collageImage
	 */
	private void rotateAndDrawImage(BufferedImage image, int row, int col) {
		AffineTransform at = new AffineTransform(); //Object for transformation

		at.translate(col, row); //specifies where in this.collageImage to paint image

		int degree = (int) (Math.random() * 91 - 45); //random degree in range: -45 to 45
		at.rotate(Math.toRadians(degree), image.getWidth()/2, image.getHeight()/2); //rotates image about its origin

		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR); //performs the transformation
		op.filter(image, this.collageImage); //paints onto collageImage
	}
	
	/**
	 * Responsible for downloading the Collage created to the server filespace.
	 * Creates a unique filename by writing the Collage to a file named "<topic><creation time>.png"
	 * 
	 * @param collage the Collage object to be saved
	 * @param filename the String containing the location of the file
	 */
	
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

	private String downloadCollage(Collage collage) {
//		String filename = "";
//		BufferedImage image = collage.getCollageImage();
		String webContentUrl = "";
		String partialWebContentUrl = "";
		BufferedImage image = null;
		String filename = null;
		String returnUrl = "";
		try {
			//just for testing purposes: read an image from the Internet to fill TMP DIR
			image = ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Cerebral_lobes.png/300px-Cerebral_lobes.png"));
	
			
			//get destination path in assets folder of server
//			File assetsDirectory = new File("../WebContent/assets");
//			System.out.println("Assets Direcotry File path: " + assetsDirectory.getAbsolutePath());
//			assetsDirectory.mkdir(); //no exception if directory already exists
//			System.out.println("Created assets directory");
			
//			webContentUrl = ""; //current system context path
//			partialWebContentUrl = "assets/birds";
//			partialWebContentUrl += System.currentTimeMillis() + ".png";    
////			webContentUrl += partialWebContentUrl;
//
//			webContentUrl = partialWebContentUrl;
//			System.out.println("WebContentUrl: " + webContentUrl);
//			File webContentUrlFile = new File(webContentUrl);
//			ImageIO.write(image, "png", webContentUrlFile);
//			System.out.println("Downloaded collage at: " + webContentUrlFile.getPath());
			
			
//			filename = "";
//			//BufferedImage image = collage.getCollageImage();
//		
//			//get destination path in assets folder of server
//			File assetsDirectory = new File(System.getProperty("user.dir") + "/assets");
//			assetsDirectory.mkdir(); //no exception if directory already exists
//			
//			filename += System.getProperty("user.dir") + "/assets/"; //current system context path
//			filename += "topicName";
//			filename += System.currentTimeMillis() + ".png";    
//			File outputFileForFrontend = new File(filename);
//			ImageIO.write(image, "png", outputFileForFrontend);
//				
//			returnUrl = filename;
			
			
			
			
			
			//DOWNLOAD TO TOMCAT TMP DIRECTORY

			//NEW CODE WITH TMP DIR
//			filename = TMP_DIR; 
//
//			System.out.println("In downloadCollageMethod");
//			
////			filename += collage.getTopic();
//			filename += "birds";
//			
//			filename += System.currentTimeMillis() + ".png";    
//			File outputFile = new File(filename);
//			ImageIO.write(image, "png", outputFile);
//			System.out.println("After download colalge method");
//
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		returnUrl = imgToBase64String(image, "png");
		System.out.println("Base 64 string for image: " + returnUrl);
		return returnUrl;
	}
	
	//from stack overflow
	private static String imgToBase64String(BufferedImage img, final String formatName)
	{
	  final ByteArrayOutputStream os = new ByteArrayOutputStream();

	  try
	  {
	    ImageIO.write(img, formatName, os);
	    System.out.println("Base 64 image in helper method: " + Base64.getEncoder().encodeToString(os.toByteArray()));
	    return Base64.getEncoder().encodeToString(os.toByteArray());
	  }
	  catch (final IOException ioe)
	  {
	    throw new UncheckedIOException(ioe);
	  }
	}
	
}
