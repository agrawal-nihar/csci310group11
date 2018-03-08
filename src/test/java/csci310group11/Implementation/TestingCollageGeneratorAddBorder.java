package csci310group11.Implementation;


import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.Test;

public class TestingCollageGeneratorAddBorder {

	//values to check against
	Integer WHITE_RED_ATTRIBUTE = 255;
	Integer WHITE_GREEN_ATTRIBUTE = 255;
	Integer WHITE_BLUE_ATTRIBUTE = 255;
	Integer BORDER_WIDTH = 3;
	
	@Test
	public void collageGeneratorAddBorderTest() {
		CollageGenerator collageGenerator = new CollageGenerator();
		collageGenerator.testingCollageGeneratorDummyImages = true;
		
		collageGenerator.addBorderToImages();
		ArrayList<BufferedImage> images = collageGenerator.borderedImages;
	
//		for (BufferedImage image : images) {
		BufferedImage image = images.get(0);
			int numRows = image.getHeight();
			int numCols = image.getWidth();
			System.out.println("numRows: " + numRows + " numCols: " + numCols);
			
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					
					//indicates the presence of the border					
					if (row < BORDER_WIDTH || row > (numRows - 1 - BORDER_WIDTH) 
							|| col < BORDER_WIDTH || col > (numCols - 1 - BORDER_WIDTH)  ) {
						Integer rgb = image.getRGB(row, col); //always returns TYPE_INT_ARGB
						Integer red =   (rgb >> 16) & 0xFF;
						Integer green = (rgb >>  8) & 0xFF;
						Integer blue =  (rgb      ) & 0xFF;
						assertEquals(WHITE_RED_ATTRIBUTE, red);
						assertEquals(WHITE_GREEN_ATTRIBUTE, green);
						assertEquals(WHITE_BLUE_ATTRIBUTE, blue);
						
					}
				}
			}
		//}

	}

}
