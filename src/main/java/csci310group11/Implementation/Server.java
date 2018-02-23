package csci310group11.Implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server {

	static final HttpServletRequest request;
	static final HttpServletResponse response;

	//Sends image from server to client
	public void service(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		//call element id collage display .append("<img src= '" + collageURL + "' \>"); 
	}

	//Download collage to client's storage
	public void downloadCollageToUserStorage(String collageURL) throws ServletException, IOException
	{
		string fileName = request.getParameter(“fileName”); //named the file from frontend would be easier
		if (fileName == null || fileName.equals(“”)) {
			throw new ServletExcpetion(“File Name can’t be null”);
		}
		File file = new File(collageURL);
		//File file = new File(request.getServletContext().getAttribute(“FILES_DIRECTORY”) + File.separator + fileName);
		if (!file.exists()) {
			throw new ServletExcpetion(“File doesn’t exists on server.”);
		}
		//System.out.println(“File location on server = “ + file.getAbsolutePath());
		ServletContext servCon = getServletContext();
		InputStream input = new FileInputStream(file);
		String memeType = servCon.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null? memeType: “application/octet-stream”);
		response.setContentLength((int) file.length());
		response.setHeader(“Content-Disposition”, “attachment; filename=\ “” + fileName + “\””);
		
		ServletOutputStream os = response.getOutputStream();
		byte[ ] bufferData = new byte[1024];
		int read = 0;
		while ((read = input.read(bufferData)) != -1) {
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		input.close();
		//System.out.println(“File downloaded at client successfully”);
	}
}
