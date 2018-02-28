package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Api {

	static final int HTTP_REQUEST_TIMEOUT = 3 * 600000;
	
	static final String GOOGLE_API_KEY = "AIzaSyCXbP7Ick0SwIKrkzhe8veZdFyaOGzXW8U";
	static final String SEARCH_ENGINE_ID = "014013525301963847978:symg0aqu3ti";
	
	static final String FILE_TYPE = ".png";
	/**
	 * "huge": huge 
	 * "icon": icon 
	 * "large": large 
	 * "medium": medium 
	 * "small": small 
	 * "xlarge": xlarge 
	 * "xxlarge": xxlarge
	 */
	static final String IMAGE_SIZE = "Medium";
	static final String SEARCH_TYPE = "image";
	/**
	 * generate search results' urls from Google Custom Search API, and then parse the url into BufferedImage,
	 * if there is insufficient images, it will throw `InsufficientImagesError`
	 * @param query
	 * @return results' Json String
	 * @throws InsufficientImagesFoundError 
	 */
	List<BufferedImage> execute(String query) throws InsufficientImagesFoundError;
}
