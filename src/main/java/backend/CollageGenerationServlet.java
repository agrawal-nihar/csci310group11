package backend;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310group11.Implementation.CollageGenerator;

/**
 * Servlet implementation class CollageGenerationServlet
 */
@WebServlet("/CollageGeneratorServlet") 
public class CollageGenerationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CollageGenerator collageGenerator; //not static, change from DESIGN
	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Verify user is valid user
		Boolean newUser = checkNewUser(request);
		
		//Determine which action was requested in the HttpServletRequest object
		String action = request.getParameter(Constants.ACTION_TYPE);
		
		//get response writer
		PrintWriter responseUrl = response.getWriter();
		
		//Build Collage process
		if (action.equals(Constants.BUILD_ACTION)) {
			
			String topic = request.getParameter("topic");
			if (topic != null) {
				//String url = null;
				String url = collageGenerator.collageGeneratorDriver(topic); //should return the URL ADD BACK IN
				
				//for testing
				//url = "https://static.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg"; 
				responseUrl.print(url);	
				responseUrl.flush();
			}
			
		}
		
	//Download Collage (to frontend) process
		else if (action.equals(Constants.DOWNLOAD_ACTION)) {
			String url = request.getParameter(Constants.URL);
			if (url != null) {
				downloadCollageToUserStorage(url); 
			}
		}
	} //end of service method

	private Boolean checkNewUser(HttpServletRequest request) {
		String userToken = request.getParameter(Constants.NEW_USER);
		Boolean newUser = false;
		if (userToken == Constants.NEW) {
			newUser = true;
			removePreviousCollages();
		}
		
		return newUser;
	}
	
	//removes all previous collages
	private void removePreviousCollages() {
		File assetsDirectory = new File(System.getProperty("user.dir") + "/assets");
		File[] allCollages = assetsDirectory.listFiles();
		
		for (int i = 0; i < allCollages.length; i++) {
			allCollages[i].delete();
		}
		
		assetsDirectory.delete();
	}
	
	//Download collage to client's storage -- localhost!
	private void downloadCollageToUserStorage(String url) throws IOException
	{
//		url = "/Users/Nagrawal/Desktop/cat.png";
		File collage = new File(url);
		if (!collage.exists()) {
			throw new IOException("Collage not found on server"); //replace with Constant string
		}
		
		File downloadDestination = new File(System.getProperty("user.home") + "/Downloads/" + System.currentTimeMillis() + ".png");
			
		Path sourcePath = collage.toPath();
		Path destinationPath = downloadDestination.toPath();
		try {
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
