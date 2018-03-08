package csci310group11.Implementation;
import org.junit.Assert;
import org.junit.Test;

public class TestingConstants {
	@Test
	public void testBackendConstants() {
		Assert.assertNotNull(new backend.Constants());
	}
	
	public void testImplementationConstants() {
		Assert.assertNotNull(new csci310group11.Implementation.Constants());
	}

}
