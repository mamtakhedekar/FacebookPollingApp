/**
 * 
 */
package edu.stevens.cs581;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.jws.WebMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.types.User;

import java.util.logging.Logger;

/**
 * @author mamta
 *
 */
public class FriendsListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger("edu.stevens.cs581.FriendsListServlet");
	
	@WebMethod(operationName="doGet")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");

		String MY_ACCESS_TOKEN = "";
		String authURL = "https://graph.facebook.com/oauth/access_token?client_id=459011260803995&redirect_uri=http://ec2-23-20-132-138.compute-1.amazonaws.com:8080/OSNAssignment/FriendsListServlet&client_secret=18119c56c44ae6cc7defcea3ab4093ab&code="
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
		Connection<com.restfb.types.User> friends = null;
		
		try {
			friends = facebookClient.fetchConnection("me/friends", User.class);
		} catch (FacebookException e) {			
			e.printStackTrace();
		}
		
		List<com.restfb.types.User> friendsList = friends.getData();
		request.setAttribute("friendsList",friendsList);
		
		log.info("Got friend count as : "+friendsList.size());
		for (User user : friendsList) {
			System.out.println(user.getId() + " : " + user.getName());
			log.info("Got friend as : "+user.getName());
		}
		
		getServletConfig().getServletContext().getRequestDispatcher("/FriendsList.jsp").forward(request, response);
	}
		
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
