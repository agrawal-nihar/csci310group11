package csci310group11.Implementation;

public class InsufficientImagesFoundError extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * The custom exception class to be thrown when there is less than 30 images being found from
	 * the google search api. It will simply print the the current images amount into the console. On the
	 * frontend, if caught this error, there will pop up a "insufficient images found error".
	 * @param i
	 */
	InsufficientImagesFoundError(int i) {
		System.out.println("Current images amount: " + i);
	}
}
