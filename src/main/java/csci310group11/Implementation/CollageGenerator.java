package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CollageGenerator {
	
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
}
