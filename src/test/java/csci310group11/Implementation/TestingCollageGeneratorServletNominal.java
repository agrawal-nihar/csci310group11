package csci310group11.Implementation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import backend.CollageGenerationServlet;

public class TestingCollageGeneratorServletNominal {
	
	//data members for tests
	public String ACTION_TYPE = "action";
	public String BUILD = "build";
	public String TOPIC = "topic";
	public String DOG = "dog";
	public String NEW_USER = "newUser";
	public String NEW_USER_TRUTH = "true";
	
	@Test
	public void testingCollageGeneratorServletNominal() throws MalformedURLException, IOException {
		CollageGenerationServlet collageGenerationServlet = new CollageGenerationServlet();
		
		//input CollageGenerator with dummy images into servlet
		CollageGenerator collageGenerator = new CollageGenerator();
		collageGenerator.testingCollageGeneratorDummyImages = true;
		collageGenerationServlet.collageGenerator = collageGenerator;
		
		HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse mockHttpServletResponse = mock(HttpServletResponse.class);
		
		//set attributes on request object
		when(mockHttpServletRequest.getParameter(ACTION_TYPE)).thenReturn(BUILD);
		when(mockHttpServletRequest.getParameter(TOPIC)).thenReturn(DOG);
		when(mockHttpServletRequest.getParameter(NEW_USER)).thenReturn(NEW_USER_TRUTH);
		
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
	}

}
