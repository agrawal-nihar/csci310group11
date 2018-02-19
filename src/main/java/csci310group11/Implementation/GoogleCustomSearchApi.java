package csci310group11.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class GoogleCustomSearchApi implements Api {
	
		public String execute(String query, RequestType requestType) {
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
				list.setKey(GOOGLE_API_KEY);
				list.setCx(SEARCH_ENGINE_ID);
				list.setImgSize(IMAGE_SIZE);
				list.setSearchType(SEARCH_TYPE);
				Search results = list.execute();
				rs = results.getItems();
			} catch (Exception e) {
				//TODO: add explicit exception
				e.printStackTrace();
			}
			return rs.toString();
		}
		
		/**
		 * optional logger for monitoring 
		 */
		public static void enableLogging() {
			Logger logger = Logger.getLogger(HttpTransport.class.getName());
			logger.setLevel(Level.CONFIG);
			logger.addHandler(new Handler() {

				@Override
				public void close() throws SecurityException {
				}

				@Override
				public void flush() {
				}

				@Override
				public void publish(LogRecord record) {
					// Default ConsoleHandler will print >= INFO to System.err.
					if (record.getLevel().intValue() < Level.INFO.intValue()) {
						System.out.println(record.getMessage());
					}
				}
			});
		}
}
