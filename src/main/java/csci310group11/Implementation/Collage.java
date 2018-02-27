package csci310group11.Implementation;

import java.awt.image.BufferedImage;

public class Collage {
	private BufferedImage collageImage;
	private String topic;

	/**
	 * Default constructor to ease CollageGenerator processes. MAY REMOVE LATER
	 */
	public Collage() {
		this.collageImage = null;
		this.topic = null;
	}


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
	/**
	 * @param collage the collage to set
	 */
	public void setCollageImage(BufferedImage collage) {
		this.collageImage = collage;
	}
	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	

}
