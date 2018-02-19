package csci310group11.Implementation;

public interface Api {

	static final int HTTP_REQUEST_TIMEOUT = 3 * 600000;
	
	static final String GOOGLE_API_KEY = "AIzaSyCXbP7Ick0SwIKrkzhe8veZdFyaOGzXW8U";
	static final String SEARCH_ENGINE_ID = "014013525301963847978:symg0aqu3ti";
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
	 * generate search results' urls from Google Custom Search API
	 * 
	 * @param query
	 * @param requestType
	 * @return results' Json String
	 */
	String execute(String query, RequestType requestType);
}
