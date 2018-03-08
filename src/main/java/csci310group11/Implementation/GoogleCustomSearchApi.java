package csci310group11.Implementation;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class GoogleCustomSearchApi implements Api {
	public static boolean testInsufficientImage = false;
	/**
	 * generate search results' urls from Google Custom Search API, and then parse the url into BufferedImage,
	 * if there is insufficient images, it will throw `InsufficientImagesError`
	 * @param query
	 * @return results' Json String
	 * @throws InsufficientImagesFoundError 
	 */
	public List<BufferedImage> execute(String query) throws InsufficientImagesFoundError {
			Customsearch cs = null;

			try {
				cs = new Customsearch(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
						try {
							// set connect and read timeouts
							httpRequest.setConnectTimeout(HTTP_REQUEST_TIMEOUT);
							httpRequest.setReadTimeout(HTTP_REQUEST_TIMEOUT);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<Result> rs = new ArrayList<Result>();
			try {
				Customsearch.Cse.List list = cs.cse().list(query);
				for(int i = 0; i < 3; i++) {
					/* Set parameters for the search, because each search will only return 10 results at maxium,
					 * so it has to make the request three times*/
					list.setKey(GOOGLE_API_KEY);
					list.setCx(SEARCH_ENGINE_ID);
					list.setImgSize(IMAGE_SIZE);
					list.setSearchType(SEARCH_TYPE);
					list.setFileType(FILE_TYPE);
					
					list.setStart((long) (i+1) * 10);
					Search results = list.execute();
				//	if (results != null) {
						rs.addAll(results.getItems());
					//}
//					else {
//						System.out.println("Results are null");
//						return null;
//					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(rs.size() < 30 || testInsufficientImage) {
				throw new InsufficientImagesFoundError(rs.size());
			}
			
			List<BufferedImage> images = new ArrayList<BufferedImage>();

			try {
				for(Result r : rs) {
					URL url = new URL(r.getLink());
					BufferedImage bf = ImageIO.read(url);
					images.add(bf);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return images;
		}
		
}
