package csci310group11.Implementation;

<<<<<<< HEAD
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
=======
import javq.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class CollageGenerator {
	
	//download collage to the server
	public void downloadCollage(Collage collage) {
		try {
			BufferedImage image = collage.collage; //getter?
			String fileName = collage.topic;
			fileName+= “_” + collage.id  + “.png”;    //if collage has a unique id?
			File outputFile = new File(topic);
			ImageIO.write(image, “png”, outputFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
>>>>>>> d72df59eab3ae3171239cf4f8f1757970575c851
}
