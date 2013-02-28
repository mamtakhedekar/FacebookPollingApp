/**
 * 
 */
package edu.stevens.cs581;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.types.User;

public class CreatePollServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger("edu.stevens.cs581.CreatePollServlet");
	
	@WebMethod(operationName="doGet")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");

		String MY_ACCESS_TOKEN = "";
		String authURL = "https://graph.facebook.com/oauth/access_token?client_id=459011260803995&redirect_uri=http://ec2-23-20-132-138.compute-1.amazonaws.com:8080/OSNAssignmentWeb/CreatePollServlet&client_secret=18119c56c44ae6cc7defcea3ab4093ab&code="
				+ code;
		URL url = new URL(authURL);
		String result = readURL(url);
		String[] pairs = result.split("&");

		for (String pair : pairs) {
			String[] kv = pair.split("=");
			if (kv[0].equals("access_token")) {
				MY_ACCESS_TOKEN = kv[1];
			}
		} // end of for loop

		log.info("Got my access token as : "+MY_ACCESS_TOKEN);
		
		FacebookClient facebookClient = new DefaultFacebookClient(
				MY_ACCESS_TOKEN);
		User user = null;
		try {
			user = facebookClient.fetchObject("me", User.class);
		} catch (FacebookException e) {			
			e.printStackTrace();
		}
		
		request.setAttribute("UserId",user.getId());
		request.setAttribute("UserName",user.getName());
		request.setAttribute("UserLocation",user.getLocation());
		
		log.info("Logged in userId: "+ user.getId() + " userName: " + user.getName() 
				+ " user location :" + user.getLocation());
		
		getServletConfig().getServletContext().getRequestDispatcher("/CreatePoll.jsp").forward(request, response);
	}
		
/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}*/
	
	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		return new String(baos.toByteArray());
	}

}
