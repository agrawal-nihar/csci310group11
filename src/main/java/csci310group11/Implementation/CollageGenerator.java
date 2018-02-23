package csci310group11.Implementation;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class CollageGenerator {
	
	//download collage to the server
	public void downloadCollage(Collage collage) {
		try {
			BufferedImage image = collage.getCollageImage(); //getter?
			String fileName = collage.getTopic();
			fileName += "_" + collage.id  + ".png";    //if collage has a unique id?
			File outputFile = new File(fileName);
			ImageIO.write(image, "png", outputFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
