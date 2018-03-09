package csci310group11.Implementation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.imageio.ImageIO;


public class CollageGenerator {

	public ArrayList<BufferedImage> images; //change to <BufferedImage> if necessary
	public ArrayList<BufferedImage> borderedImages;
	
	//changed to public for testing
	public BufferedImage collageImage;
	
	private GoogleCustomSearchApi api;
	
	//testing objects
	public ArrayList<BufferedImage> dummyImages = null;
	public ArrayList<BufferedImage> dummyImagesWithNull = null;
	public ArrayList<Integer> dummyRotationDegrees = new ArrayList<Integer>(Arrays.asList(-11, 0, 7, -41, -31, -36, 13, 
																															18, -29, 18, 6, 11, 11, -11, -9, 
																															42, 41, 21, 16, 5, -27, 2, 6, 25, 
																															17, 27, -35, 29, 44, -28)); 
	public Integer currentDummyRotationDegreeIndex = 0;
	public String returnURL = null;
	public BufferedImage downloadCollageDummyImage;
	
	//testing flags
	public Boolean testingCollageGeneratorDummyImages = true; //flag to determine whether API should be made
	public Boolean testingCollageGeneratorDummyImagesWithNull = false; 
	public Boolean testingRotation = false;
	public Boolean testingDownloadCollage = false; 
	public static  Boolean testingResizeImageReturnNull = false;
//	public static boolean testMode = false;
	public String rotationFile = "/home/student/Desktop/rotation.txt";
	public String subImagesSizeFile = "/home/student/Desktop/size.txt";
	public String collageSizeFile = "/home/student/Desktop/byte.txt";

	public CollageGenerator() throws MalformedURLException, IOException {
		this.images = new ArrayList<BufferedImage>();
		this.borderedImages = new ArrayList<BufferedImage>();
		this.collageImage = new BufferedImage(1000, 750, BufferedImage.TYPE_INT_ARGB);
		this.api = new GoogleCustomSearchApi();
		
		//testing data members -- 
//		try {
		downloadCollageDummyImage = ImageIO.read(new URL("https://i.imgur.com/Tgywof3.jpg"));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		try {
			dummyImages = new ArrayList<BufferedImage>(Arrays.asList(
					ImageIO.read(new URL("https://i.imgur.com/Tgywof3.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/NINXdNF.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/vWD85qB.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/BUG2NOZ.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/t6rfewd.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/iv5iLkj.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/teIakKI.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/5jdgEU4.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/s1i5OGd.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/yTV8L0s.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/fuG4fpj.gif"))
					,ImageIO.read(new URL("https://i.imgur.com/crLuiua.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/OzaawFv.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/XFZE79u.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/0gXBfVm.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/6G0JhxF.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/lMOAQBs.jpg?1"))
					,ImageIO.read(new URL("https://i.imgur.com/lxHeItW.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/DWOQF38.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/Lj2UGJh.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/PtKFycU.jpg?1"))
					,ImageIO.read(new URL("https://i.imgur.com/VgmBH30.png"))
					,ImageIO.read(new URL("https://i.imgur.com/4qmmRFj.png"))
					,ImageIO.read(new URL("https://i.imgur.com/qVkzrLq.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/m8mpGoK.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/xmRJOOP.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/bzPQBoB.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/90yLceB.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/NFS0WeC.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/tl91hI5.gif")) ));
//		} catch (IOException ioe) {
//			System.out.println("ioe: " + ioe.getMessage());
//		}
		
//		try {
			dummyImagesWithNull = new ArrayList<BufferedImage>(Arrays.asList(
					null
					,ImageIO.read(new URL("https://i.imgur.com/NINXdNF.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/vWD85qB.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/BUG2NOZ.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/t6rfewd.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/iv5iLkj.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/teIakKI.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/5jdgEU4.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/s1i5OGd.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/yTV8L0s.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/fuG4fpj.gif"))
					,ImageIO.read(new URL("https://i.imgur.com/crLuiua.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/OzaawFv.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/XFZE79u.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/0gXBfVm.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/6G0JhxF.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/lMOAQBs.jpg?1"))
					,ImageIO.read(new URL("https://i.imgur.com/lxHeItW.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/DWOQF38.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/Lj2UGJh.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/PtKFycU.jpg?1"))
					,ImageIO.read(new URL("https://i.imgur.com/VgmBH30.png"))
					,ImageIO.read(new URL("https://i.imgur.com/4qmmRFj.png"))
					,ImageIO.read(new URL("https://i.imgur.com/qVkzrLq.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/m8mpGoK.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/xmRJOOP.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/bzPQBoB.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/90yLceB.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/NFS0WeC.jpg"))
					,ImageIO.read(new URL("https://i.imgur.com/tl91hI5.gif")) ));
//		} catch (IOException ioe) {
//			System.out.println("ioe: " + ioe.getMessage());
//		}
	}
	
	
	
	/**
	 * Method to include dummy parameters if we are in testing process
	 * @return true or false whether some flag was set
	 */
	public Boolean enableTestingFlags() {
		if (testingCollageGeneratorDummyImages) {
			this.images = dummyImages;
			return true;
		} else if (testingCollageGeneratorDummyImagesWithNull) {
			this.images = dummyImagesWithNull;
			return true;
		} else return false;
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
	 * @throws IOException 
	 * @throws InsufficientImagesFoundError 
	 */
	public String collageGeneratorDriver(String topic) throws IOException, InsufficientImagesFoundError {
		
		//clear all logging files
		clearAllLoggingFiles(); 
		
		//To check testing flags and enable them if they were set in test case
//		try {
			if (!enableTestingFlags()) {
				this.images = (ArrayList<BufferedImage>) this.api.execute(topic); //API call
			}
			else {
				this.images = dummyImages;
			}
//		} catch (InsufficientImagesFoundError iife) { //Error is thrown if less than 30 images are found
//			return null;
//		}

		this.resizeImages();
		this.addBorderToImages();
		this.compileCollage();

		Collage collage = new Collage(this.collageImage, topic);
		
		String returnURL = this.downloadCollage(collage);
		
		//testing
		System.out.println("returnURL: " + returnURL.substring(0, 10));
		this.returnURL = returnURL;
		
		
		
//		readFromFile(rotationFile);
//		readFromFile(collageSizeFile);
//		readFromFile(subImagesSizeFile);
//		
//		printPathOfFile(rotationFile);
//		readFromFile(collageSizeFile);
//		readFromFile(subImagesSizeFile);
		
		return returnURL;
	}
	
	
	//helper method to clear all logging files
	public void clearAllLoggingFiles() throws FileNotFoundException {
		ArrayList<String> files = new ArrayList<String>();
		files.add(rotationFile);
		files.add(subImagesSizeFile);
		files.add(collageSizeFile);

		Utility.clearAllLoggingFiles(files);
	}
	
//	//identify path of file
//	public void printPathOfFile(String filename) {
//		File file = new File(filename);
//		System.out.println(filename + " should be at: ");
//
//		System.out.println(file.getAbsolutePath());
//		
//	}
	/**
	 * Resizes all the BufferedImages inside of images to be 1/20th of the size of the overall collage.
	 * 
	 * Each images will be 1/5 as wide as the collage and 1/4 as tall as the collage.
	 * These newly sized images will replace the original images in this.images.
	 */
	public void resizeImages() {
		//1/20th of collage dimensions
		
		//To check testing flags and enable them if they were set in test case
		enableTestingFlags();
		
		int resizeWidth = this.collageImage.getWidth()/5;
		int resizeHeight = this.collageImage.getHeight()/4;
		
		//Iterate through all images
		for(int i=0; i < images.size(); i++) {
			BufferedImage img = images.get(i);
			//New BufferedImage with 1/20th dimensions of collage
			BufferedImage resizeImg = null;
			if (testingResizeImageReturnNull) {
				img = null;
			}
			if (img != null) {
				resizeImg = new BufferedImage(resizeWidth, resizeHeight, img.getType());
				//Draws the img image into the size of the resizeImg
				Graphics2D graphics = resizeImg.createGraphics();
				graphics.drawImage(img, 0, 0, resizeWidth, resizeHeight, null);

				//replace BufferedImage in images with resizedImg
				images.set(i, resizeImg);
				
				graphics.dispose(); //releases the resources used by graphics
			}
			else {
				images.set(i, new BufferedImage(1, 1, 1)) ; //DUMMY Picture for null URL
			}

		}
	}
	
	/**
	 * Responsible for adding a 3px white border to each image to be added to the collage.
	 * 
	 * Creates a new BufferedImage that is 6px taller and wider than the original BufferedImage in images.
	 * 
	 * Sets the graphics of the larger BufferedImage to white. Paints the original image onto the 
	 * new BufferedImage to create a 3px "border". Adds the bordered BuffereImage to this.borderedImages.
	 */
	public void addBorderToImages() {
		
		//To check testing flags and enable them if they were set in test case
		enableTestingFlags();
		
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

	/**
	 * Responsible for creating the this.collageImage.
	 * 
	 * Sets the collage to all white and then paints the images in this.borderedImages onto this.collageImage.
	 * Rotates each individual image and then paints onto this.collageImage in order to allow for cropping
	 * at the borders. 
	 * 
	 * The basic layout of the collage is 5 rows of 6 images in a "grid".
	 * Minor adjustments are made to ensure that the border of the collage are covered regardless of individual
	 * rotation. 
	 * @throws IOException 
	 */
	public void compileCollage() throws IOException {
		Graphics2D graphics = this.collageImage.createGraphics();
		graphics.setPaint(Color.WHITE); //check for "whitespace"
		graphics.fillRect(0, 0, this.collageImage.getWidth(), this.collageImage.getHeight());

		//logging method
		String collageSizeString = this.collageImage.getWidth() + "\n" + this.collageImage.getHeight();
		Utility.writeToFile(collageSizeString, subImagesSizeFile); 
		//end of logging
		
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

				//logging method
				String subImageSizeString = images.get(6*r + c).getWidth() + "\n" + images.get(6*r + c).getHeight();
				Utility.writeToFile(subImageSizeString, subImagesSizeFile); 
				//end of logging
				
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
	 * @throws IOException 
	 */
	public void rotateAndDrawImage(BufferedImage image, int row, int col) throws IOException {
		AffineTransform at = new AffineTransform(); //Object for transformation

		at.translate(col, row); //specifies where in this.collageImage to paint image

		/* these two params are for later testing, it only allocates a little resource but provide huge convenience to later test */
		double prev_x = at.getScaleX();
		double prev_y = at.getScaleY();
		/* end of assigning test params */
		
		//testing
		Integer degree = null;
		if (!testingRotation) {
			degree = (int) (Math.random() * 91 - 45); //random degree in range: -45 to 45
		} else {
			degree = dummyRotationDegrees.get(currentDummyRotationDegreeIndex++);
			
			//testing
			System.out.println("Current dummyRotationDegree: " + degree);
		}
		
		//write rotations to file
		Utility.writeToFile(Integer.toString(degree), rotationFile);
		//end of logging
		 
		at.rotate(Math.toRadians(degree), image.getWidth()/2, image.getHeight()/2); //rotates image about its origin

		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR); //performs the transformation
		
				
		/* if it's in test mode, below codes will exec once, else it won't be invoked */
//		if (testMode) {
//			double alt_x = op.getTransform().getScaleX();
//			double alt_y = op.getTransform().getScaleY();
//			double rad = Math.toRadians(degree);
//			if(degree > 0) {
//						double comp_x = prev_x * Math.cos(rad) + prev_y * Math.sin(rad);
//						double comp_y = -prev_x * Math.sin(rad) + prev_y * Math.cos(rad);
//						if (comp_x == alt_x && comp_y == alt_y) test_degreeAfterRotation = true;
//						else test_degreeAfterRotation = false;
//					} else {
//						double comp_x = prev_x * Math.cos(rad) - prev_y * Math.sin(rad);
//						double comp_y = prev_x * Math.sin(rad) + prev_y * Math.cos(rad);
//						if (comp_x == alt_x && comp_y == alt_y) test_degreeAfterRotation = true;
//						else test_degreeAfterRotation = false;
//					}
//				}
		/* end of test case */
				
			
		
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

	/**
	 * This function will download the generated collage into user local storage. This file extension
	 * is png. This function will convert the file into base64 and return the path.
	 * @param collage
	 * @return returnUrl
	 * @throws IOException 
	 */
	public String downloadCollage(Collage collage) throws IOException {

		String returnUrl = "";
		//testing
		BufferedImage before = null;
		if (!testingDownloadCollage) {
			 before = collage.getCollageImage();
		}
		else {
			before = downloadCollageDummyImage;
		}
	
		int w = before.getWidth();
		int h = before.getHeight();
				
		//from stackoverflow
		BufferedImage after = new BufferedImage(w/2, h/2, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(0.5, 0.5);
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(before, after);
		
		returnUrl = imgToBase64String(after, "png");
		return returnUrl;
		
	}
	
	//from stack overflow
	public static String imgToBase64String(BufferedImage img, final String formatName) throws IOException
	{
	  final ByteArrayOutputStream os = new ByteArrayOutputStream();

//	  try
//	  {
	    ImageIO.write(img, formatName, os);
	    return Base64.getEncoder().encodeToString(os.toByteArray());
//	  }
//	  catch (final IOException ioe)
//	  {
//	    throw new UncheckedIOException(ioe);
//	  }
	}
	
}
