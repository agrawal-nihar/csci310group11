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
		
		String topic = request.getParameter("topic");
		String url = collageGenerator.collageGeneratorDriver(topic); //should return the URL
		
		request.setAttribute("url", url);
	}


}
