package backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310group11.Implementation.CollageGenerator;

/**
 * Servlet implementation class CollageGenerationServlet
 */
@WebServlet("/CollageGeneratorServlet") //TELL YISA!!!
public class CollageGenerationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CollageGenerator collageGenerator; //not static, change from DESIGN
	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Verify user is valid user
		checkUserToken(request);
		
		//Determine which action was requested in the HttpServletRequest object
		String action = request.getParameter(Constants.ACTION_TYPE);
		
		//Build Collage process
		if (action.equals(Constants.BUILD_ACTION)) {
			
			String topic = request.getParameter("topic");
			if (topic != null) {
				String url = collageGenerator.collageGeneratorDriver(topic); //should return the URL
				request.setAttribute("url", url);			
			}
			
		}
		
	//Download Collage (to frontend) process
		else if (action.equals(Constants.DOWNLOAD_ACTION)) {

			String url = request.getParameter(Constants.URL);
			if (url != null) {
				Server.downloadCollageToUserLocalStorage(url); //MAKE STATIC METHOD
				
			}
			
		}
		
	} //end of service method

	private void checkUserToken(HttpServletRequest request) {
		String userTokenString = request.getParameter(Constants.USER_TOKEN);
		Integer userToken = Integer.parseInt(userTokenString);
		if (userToken == Constants.INVALID_TOKEN) {
			Server.removePreviousCollages();
			request.setAttribute(Constants.UPDATED_USER_TOKEN, Constants.VALID_TOKEN); //MUST BE READ IN ON FRONTEND
		}
	}
	
}
