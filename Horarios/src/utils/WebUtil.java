package utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/* Clase que nos permite realizar el forward de forma más sencilla */ 
 
public class WebUtil {

	public final static void forwardTo(ServletRequest request,
			ServletResponse response, String url) throws IOException,
			ServletException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);

		requestDispatcher.forward(request, response);

	}

}
