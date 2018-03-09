package csci310group11.Implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class TestingUtility {

	@Test
	public void testWriteAndRead() throws IOException {
		Utility.clearFile("test.txt");
		Utility.writeToFile("test", "test.txt");
		assertEquals("test", Utility.readFromFile("test.txt"));
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(new Utility());
	}

}
