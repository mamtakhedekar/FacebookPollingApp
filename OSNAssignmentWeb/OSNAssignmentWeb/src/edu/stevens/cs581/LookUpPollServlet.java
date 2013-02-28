/**
 * 
 */
package edu.stevens.cs581;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.types.User;

import edu.stevens.cs581.representation.PollRep;
import edu.stevens.cs581.state.PollStateLocal;
import edu.stevens.cs581.yelp.YelpAPI;


public class LookUpPollServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger("edu.stevens.cs581.LookUpPollServlet");
	
	@EJB(beanName="StateBean")
	private PollStateLocal pollState;
	
	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
			}
		return new String(baos.toByteArray());
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String code = request.getParameter("code");

		String MY_ACCESS_TOKEN = "";
		String authURL = "https://graph.facebook.com/oauth/access_token?client_id=459011260803995&redirect_uri=http://ec2-23-20-132-138.compute-1.amazonaws.com:8080/OSNAssignmentWeb/LookUpPollServlet&client_secret=18119c56c44ae6cc7defcea3ab4093ab&code="
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
		
		String pollId = request.getParameter("pollId");
	    long userId = new Long(user.getId()).longValue();
	    log.info("User:"+userId+"fbPollId:"+pollId);
	    
    
	    PollRep poll = new PollRep();
	    poll.setPollCreator(userId);
		List<PollRep> pollRep = pollState.getPollsForUser(poll);
	   	    
		YelpAPI yelp = new YelpAPI();
		
	    log.info("length==="+pollRep.size());
		request.setAttribute("pollList",pollRep);
		
		//List<Long> allUserIds = new ArrayList<Long>();
		//List<Long> allFBPollIds = new ArrayList<Long>();
		
		for ( int i = 0; i < pollRep.size(); i++)
		{
			log.info("LookUpPollServlet Yelp search for location: " + user.getLocation().getName() +
					" text: " + pollRep.get(i).getShortText());
			if (user.getLocation().getName() != null && pollRep.get(i).getShortText() != null )
				pollRep.get(i).setYelpResult(yelp.searchYelp(user.getLocation().getName(), pollRep.get(i).getShortText()));
			else
				pollRep.get(i).setYelpResult("No results from Yelp!");
		}
		
		/*
		request.setAttribute("allUserIds",allUserIds);
		request.setAttribute("allFBPollIds",allFBPollIds);*/

	    getServletConfig().getServletContext().getRequestDispatcher("/LookUpPoll.jsp").forward(request, response);
	}	
	
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PollState state = new edu.stevens.cs581.state.PollState(); 
		String userId = request.getParameter("userId");
		PollRep pollRep = new PollRep();
		pollRep.setPollCreator(new Long(userId));
		pollState.getPollsForUser(pollRep);

		/**
		 * 
		 */
		
		//@TODO set the values in the request object
		//getServletConfig().getServletContext().getRequestDispatcher("/LookUpPoll.jsp").forward(request, response);

	//}
}
