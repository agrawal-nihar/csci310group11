package backend;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310group11.Implementation.CollageGenerator;
import csci310group11.Implementation.InsufficientImagesFoundError;
import csci310group11.Implementation.Utility;

/**
 * Servlet implementation class CollageGenerationServlet
 */
@WebServlet("/CollageGeneratorServlet") 
public class CollageGenerationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CollageGenerator collageGenerator; //not static, change from DESIGN
	
	public ArrayList<String> allCollages = new ArrayList<String>();
	public ArrayList<String> getAllCollages() {
		return allCollages;
	}

	//TESTING DATA MEMBERS
	public Boolean testingServletFlag = false;
	public long imageToDownloadSize = 0L;
	public String allCollagesFile = "/home/student/Desktop/imageLogs.txt";
	public String collageBytesSizeFile = "/home/student/Desktop/byte.txt";

//	private String globalurl = "";
	/**
	 * This servlet will be called whenever the user clicks a any of button in our frontend pages. Depends on what time of
	 * button it is, it will execute different code based on it. One option is to generate a new collage by calling CollageGenerator
	 * Other options is when the user has clicked the Export Collage button which will call downloadCollageToUserStorage.
	 * @param request, response
	 */
	
	//CHANGED TO PUBLIC - -is this an issue??
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
		//Verify user is new user	
		String newUserTruthValue = request.getParameter("newUser");

		checkNewUser(newUserTruthValue);
		collageGenerator = new CollageGenerator();
		
		//Determine which action was requested in the HttpServletRequest object
		String action = request.getParameter(Constants.ACTION_TYPE);
		
		//get response writer
		PrintWriter responseUrl = response.getWriter();
		//Build Collage process

		String topic = request.getParameter("topic");
		if (action.equals(Constants.BUILD_ACTION)) {

				//for testing purposes!
				String url = null;
				if (!testingServletFlag) {
					try {
						url = collageGenerator.collageGeneratorDriver(topic) ;
					} catch (InsufficientImagesFoundError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					 url = "testUrl";
				}
			
				allCollages.add(url);
					
//					BASE64Decoder decoder = new BASE64Decoder(); 
//					byte[] imageBytes = decoder.decodeBuffer(url);
					
				responseUrl.print(url);		
				responseUrl.flush();
				
				Utility.printAllCollagesBase64String(allCollages, allCollagesFile);
				
				//write to file
				byte[] imageBytes = Base64.getDecoder().decode(url);
				System.out.println("Bytes in image file byte array after reading: " + imageBytes.length);
				Utility.writeToFile(String.valueOf(imageBytes.length), collageBytesSizeFile);
		}
		
	//Download Collage (to frontend) process
		else {
//			String url = request.getParameter(Constants.URL);
			Integer currentCollageId = Integer.valueOf(request.getParameter("currentCollageId"));
			downloadCollageToUserStorage(currentCollageId);
			
		}
	} //end of service method

	/**
	 * When the users has created multiple collages in one session, this function will allow the server to
	 * delete all the collages that were made in this session. Since all the collages are stored in /assets directory,
	 * it will delete every file inside of this directory.
	 * @param request
	 * @return newUser: true if new user enters the session or false if it's an old user
	 */
	private Boolean checkNewUser(String truthValue) {
		Boolean newUser = false;
		if (truthValue.equals("true")) {
			newUser = true;
			removePreviousCollages();
		}
		else {
			newUser = false;
		}
		
		return newUser;
	}
	
	/**
	 * When new user enters, all the previous collages will be deleted. This function is responsible for makeing
	 * the deletion for all previous collages under /assests directory. It will first check whether the directory exist
	 * or not, if not it won't do anything.
	 */
	private void removePreviousCollages() {
		allCollages.clear();
	}

	
	/**
	 * This function allows users to download the collage to their storage. It will download to their Downloads directory
	 * The downloaded collage will be in png file and the name of the file will be based on the time it was downloaded.
	 * If the source of image can't be found, it will not download.
	 * @param url
	 */
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

	public void downloadCollageToUserStorage(Integer currentCollageId) throws IOException, ServletException
	{
		String collageFullEncodedString = allCollages.get(currentCollageId-1);

		byte[] imageBytes = Base64.getDecoder().decode(collageFullEncodedString);
		
		BufferedImage img = ImageIO.read((InputStream) new ByteArrayInputStream(imageBytes)); //CHANGE THIS -- NIHAR
		DataBuffer dataBuffer = img.getData().getDataBuffer();
		long sizeBytes = ((long) dataBuffer.getSize()) * 4l;
		imageToDownloadSize = sizeBytes / (1024l * 1024l);
		
		File outputfile = new File(System.getProperty("user.home") + "/Downloads/downloadedCollage" + currentCollageId + ".png");
    ImageIO.write(img, "png", outputfile);
		

			
	}
	
}
