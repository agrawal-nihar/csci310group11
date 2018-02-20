package csci310group11.Implementation;

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
}
