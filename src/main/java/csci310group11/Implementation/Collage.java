package csci310group11.Implementation;

import java.awt.image.BufferedImage;

public class Collage {
	private BufferedImage collageImage;
	private String topic;

	/**
	 * @param collage
	 * @param topic
	 */
	public Collage(BufferedImage collage, String topic) {
		super();
		this.collageImage = collage;
		this.topic = topic;
	}
	/**
	 * @return the collage
	 */
	public BufferedImage getCollageImage() {
		return collageImage;
	}

}
