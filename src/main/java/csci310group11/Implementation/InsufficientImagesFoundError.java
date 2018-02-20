package csci310group11.Implementation;

public class InsufficientImagesFoundError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	InsufficientImagesFoundError(int i) {
		System.out.println("Current images amount: " + i);
	}
}
