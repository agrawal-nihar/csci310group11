package backend;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310group11.Implementation.CollageGenerator;
import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class CollageGenerationServlet
 */
@WebServlet("/CollageGeneratorServlet") 
public class CollageGenerationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CollageGenerator collageGenerator; //not static, change from DESIGN
	private ArrayList<String> allCollages = new ArrayList<String>();
	private String globalurl = "";
	/*
	 * This servlet will be called whenever the user clicks a any of button in our frontend pages. Depends on what time of
	 * button it is, it will execute different code based on it. One option is to generate a new collage by calling CollageGenerator
	 * Other options is when the user has clicked the Export Collage button which will call downloadCollageToUserStorage.
	 */


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//TESTING
		
		
		
		
		
		
		//Verify user is valid user
		
		
		
		
		System.out.println("Beg of REQUEST arguments");
		System.out.println(request.getParameter("newUser"));
		System.out.println(request.getParameter("url"));
		System.out.println("End of REQUEST arguments");
		
		
		String newUserTruthValue = request.getParameter("newUser");
		Boolean newUser = checkNewUser(newUserTruthValue);
		collageGenerator = new CollageGenerator();
		System.out.println("After collageGenerator");

		//Determine which action was requested in the HttpServletRequest object
		String action = request.getParameter(Constants.ACTION_TYPE);
		
		//get response writer
		PrintWriter responseUrl = response.getWriter();
		//Build Collage process
		System.out.println("Before build collage");

		String topic = request.getParameter("topic");
		if (action.equals(Constants.BUILD_ACTION)) {

			if (topic != null) {
				
//				String url = "assets/DOG1519722937895.png";
				String url = collageGenerator.collageGeneratorDriver(topic); //should return the URL ADD BACK IN
				allCollages.add(url);
				
				System.out.println("URL Printed: " + url);
//				this.globalurl = url;
//				String url = "brain.png"; 
				responseUrl.print(url);					
				responseUrl.flush();
			}
			
		}
		
	//Download Collage (to frontend) process
		else if (action.equals(Constants.DOWNLOAD_ACTION)) {
			System.out.println("IN COLLAGE GENERATION SERVLET's DOWNLOAD ACTION");
			String url = request.getParameter(Constants.URL);
			Integer currentCollageId = Integer.valueOf(request.getParameter("currentCollageId"));
			downloadCollageToUserStorage(currentCollageId);
			
			if (url != null) {
//				downloadCollageToUserStorage(request, response, url); 
			}
		}
	} //end of service method

	/*
	 * When the users has created multiple collages in one session, this function will allow the server to
	 * delete all the collages that were made in this session. Since all the collages are stored in /assets directory,
	 * it will delete every file inside of this directory.
	 */
	private Boolean checkNewUser(String truthValue) {
		Boolean newUser = false;
		if (truthValue.equals("true")) {
			newUser = true;
			removePreviousCollages();
		}
		
		return newUser;
	}
	
	private void removePreviousCollages() {
		File assetsDirectory = new File(System.getProperty("user.dir") + "/WebContent/assets");
		File[] allCollages = assetsDirectory.listFiles();
		if(allCollages != null) {
			for (int i = 0; i < allCollages.length; i++) {
				allCollages[i].delete();
			}
		
			assetsDirectory.delete();
		}
		
	}

	
	/*
	 * This fucntion allow users to download the collage to their storage. It will download to their Downloads directory
	 * The downloaded collage will be in png file and the name of the file will be based on the time it was downloaded.
	 * If the source of image can't be found, it will not download.
	 */
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

	private void downloadCollageToUserStorage(Integer currentCollageId) throws IOException, ServletException
	{
		String collageFullEncodedString = allCollages.get(0);
	
		System.out.println("URL received in downloadCollageToUserStorage():" + collageFullEncodedString.substring(0, 10));

		BASE64Decoder decoder = new BASE64Decoder();

		//String base64Image = parsedUrl;
		System.out.println(collageFullEncodedString);
		byte[] imageBytes = decoder.decodeBuffer(collageFullEncodedString);
		
		System.out.println("bytes: " + imageBytes.length);
		BufferedImage img = ImageIO.read((InputStream) new ByteArrayInputStream(imageBytes));
		if(img == null) {
			System.out.println("null image");
		}

		try {
				File outputfile = new File(System.getProperty("user.home") + "/Downloads/downloadedCollage" + currentCollageId + ".png");
		    ImageIO.write(img, "png", outputfile);
		} 
		catch (IOException e) {
		    System.err.println("IOException: " + e);
		}
			
			
			
		//parse URL
//		String parsedUrl = url.substring(23);
			//System.out.println("Parsed URL: " + parsedUrl);
	    
//			try {
		
//		ImageIO.write(retrievedImage, "png", downloads);
		
		
//		System.out.println("Request servlet context" + request.getServletContext().getContextPath());
//		
////		//just for testing purposes: read an image from the Internet to fill TMP DIR
//////		BufferedImage bufferedImage = ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Cerebral_lobes.png/300px-Cerebral_lobes.png"));
////		
////		//get TMP DIR
////		String filePath = getServletConfig().getServletContext().getRealPath("/assets");   
////		File assetsDirectory = new File(filePath);
////		assetsDirectory.mkdir(); //no exception if directory already exists
////    System.out.println(String.format("ASSETS DIRECTORY File: %s", assetsDirectory.getAbsolutePath()));
////    
////    
////    File folder = (File) getServletContext().getAttribute(ServletContext.TEMPDIR);
////    File result = new File(folder, url);
////    
////    
//////		String filename = TMP_DIR; 
//////		filename += "TestTopic";
//////		filename += System.currentTimeMillis() + ".png";    
//////		File outputFile = new File(filename);
//////		ImageIO.write(bufferedImage, "png", outputFile);
////		
////		//READ URl and PARSE
////    String rawUrl = request.getParameter("url");
//////    String parsedUrl = parseTmpDirUrl(rawUrl);
////    String parsedUrl = rawUrl;
////		System.out.println("Parsed URL: " + parsedUrl);
////		
////		//FOR TESTING ---
////    File retrievedFile = null;
////		System.out.println("ALl files in TMP DIR");
////    File curDir = new File(TMP_DIR);
////    File[] filesList = curDir.listFiles();
////    for(File f : filesList){
////        if(f.isFile()){
//////            System.out.println(f.getName());
////            if (f.getName().equals(parsedUrl)) {
////            		System.out.println("FOUND");
////            		retrievedFile = f;
////            }
////        }
////    }
////    
////		//read from TMP DIR
////		String filename = TMP_DIR + parsedUrl;
////		System.out.println("RECEIVED FILE NAME:" + parsedUrl);
////
////		
////		BufferedImage retrievedImage = ImageIO.read(retrievedFile);
////		File downloads = new File(System.getProperty("user.home") + "/Downloads/" + url);
////		ImageIO.write(retrievedImage, "png", downloads);

		
				//String fileName = request.getParameter("filename"); //named the file from frontend would be easier
				
//			String filename = url;
//				if (filename == null || filename.equals("")) {
//					throw new ServletException("File name is null");
//				}
////				File file = new File(collageURL);
//				request.getServletContext().setAttribute("javax.servlet.context.tempdir","tempFolderName");
//				
//				//temporarily write to this temporary directory
//				
//				//read from directory
//				System.out.println(request.getServletContext().getAttribute("tempFolderName") + File.separator + filename);
//				File file = new File(request.getServletContext().getAttribute("FILES_DIRECTORY") + File.separator + filename);
//				if (!file.exists()) {
//					throw new ServletException("File doesn’t exists on server.");
//				}
//				//System.out.println(“File location on server = “ + file.getAbsolutePath());
//				ServletContext servCon = getServletContext();
//				InputStream input = new FileInputStream(file);
//				String memeType = servCon.getMimeType(file.getAbsolutePath());
//				response.setContentType(memeType != null? memeType: "application/octet-stream");
//				response.setContentLength((int) file.length());
//				response.setHeader("Content-Disposition", "attachment; filename= \"" + filename + "\"");
//				
//				ServletOutputStream os = response.getOutputStream();
//				byte[ ] bufferData = new byte[1024];
//				int read = 0;
//				while ((read = input.read(bufferData)) != -1) {
//					os.write(bufferData, 0, read);
//				}
//				os.flush();
//				os.close();
//				input.close();
//				System.out.println("File downloaded at client successfully");
		
//		File file = new File(request.getServletContext().getAttribute(FILES_DIRECTORY) + File.separator + fileName);

//				String absolutePath = FileSystems.getDefault().getPath(url).normalize().toAbsolutePath().toString();
//
//		System.out.println("Final URL " + request.getServletContext());
////		File collage = new File(request.getServletContext());
//		File collage = null;
//		if (!collage.exists()) {
//			throw new IOException("Collage not found on server"); //replace with Constant string
//		}
//		
//		File downloadDestination = new File(System.getProperty("user.home") + "/Downloads/" + System.currentTimeMillis() + ".png");
//		
//		Path sourcePath = collage.toPath();
//		Path destinationPath = downloadDestination.toPath();
//		try {
//			Files.copy(sourcePath, destinationPath);
//			System.out.println("IMage downloaded");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}
	
}

//private String parseTmpDirUrl(String url) {
//int startOfRealUrlString = 0;
//System.out.println("Substrings");
//for (int i = 0; i < url.length() - 3; i++) {
////System.out.println(url.substring(i, i + 2));
//if (url.substring(i, i + 6).equals("/assets/")) {
//	startOfRealUrlString = i + 6;
//	break;
//}
//}

//System.out.println("Index of start of real url string " + startOfRealUrlString);
//String realUrlString = url.substring(startOfRealUrlString, url.length() );

////System.out.println("Real URL String: " + realUrlString);
//return realUrlString;
//}
