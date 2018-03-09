package csci310group11.Implementation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import backend.CollageGenerationServlet;

public class TestingDownloadCollage {

	public String ACTION_TYPE = "action";
	public String BUILD = "build";
	public String DOWNLOAD = "download";
	public String TOPIC = "topic";
	public String DOG = "dog";
	public String NEW_USER = "newUser";
	public String FALSE = "false";
	public String TRUE = "true";
	public String CURRENT_COLLAGE_ID = "currentCollageId";
	public String ONE = "1";
	
	@Test
	public void test() throws MalformedURLException, IOException {
		
		HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse mockHttpServletResponse = mock(HttpServletResponse.class);
		
		//NOMINAL COLLAGE GENERATION PROCESS TO BUILD 1 COLLAGE
		/******************************/
		 CollageGenerationServlet collageGenerationServlet = new CollageGenerationServlet();
		
		//input CollageGenerator with dummy images into servlet
		CollageGenerator collageGenerator = new CollageGenerator();
		collageGenerator.testingCollageGeneratorDummyImages = true;
		collageGenerationServlet.collageGenerator = collageGenerator;
	
		//set attributes on request object
		when(mockHttpServletRequest.getParameter(ACTION_TYPE)).thenReturn(BUILD);
		when(mockHttpServletRequest.getParameter(TOPIC)).thenReturn(DOG);
		when(mockHttpServletRequest.getParameter(NEW_USER)).thenReturn(TRUE);
		
		//set attributes on response object
		StringWriter writer = new StringWriter();
		try {
			when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(writer));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//now test the nominal case of collageGenerationServlet
		try {
			collageGenerationServlet.service(mockHttpServletRequest, mockHttpServletResponse);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, collageGenerationServlet.getAllCollages().size());
		assertEquals(!writer.toString().isEmpty(), true);
		/****************** */
		
		//set attributes on request object
		when(mockHttpServletRequest.getParameter(ACTION_TYPE)).thenReturn(DOWNLOAD);
		when(mockHttpServletRequest.getParameter(TOPIC)).thenReturn(DOG);
		when(mockHttpServletRequest.getParameter(NEW_USER)).thenReturn(FALSE);
		when(mockHttpServletRequest.getParameter(CURRENT_COLLAGE_ID)).thenReturn(ONE);

		try {
			collageGenerationServlet.service(mockHttpServletRequest, mockHttpServletResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Long expectedCollageSize = collageGenerationServlet.imageToDownloadSize;
		
		//Read file from local storage
		BufferedImage image = null;
    try {
			image = ImageIO.read(new File(System.getProperty("user.home") + "/Downloads/downloadedCollage1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
		DataBuffer dataBuffer = image.getData().getDataBuffer();
		long sizeBytes = ((long) dataBuffer.getSize()) * 4l;
		Long actualCollageSize = sizeBytes / (1024l * 1024l);
		
		assertEquals(expectedCollageSize, actualCollageSize);
	}

}