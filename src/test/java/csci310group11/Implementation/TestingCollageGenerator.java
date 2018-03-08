package csci310group11.Implementation;
import static org.mockito.Mockito.mock;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class TestingCollageGenerator extends TestCase {
	public static CollageGenerator cg = null;
	
	@Test
	public void testContructor() {
		
	}
	
	@Test
	public void testRotation() throws IOException {
		CollageGenerator.testMode = true;
		cg = mock(CollageGenerator.class);
		BufferedImage dummyImage = new BufferedImage(1, 1, 1);
		cg.rotateAndDrawImage(dummyImage, 0, 0);
		assertEquals(CollageGenerator.test_degreeAfterRotation, true);
	}
}