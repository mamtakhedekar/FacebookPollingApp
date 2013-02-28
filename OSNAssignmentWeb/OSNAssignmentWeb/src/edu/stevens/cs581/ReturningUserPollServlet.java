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

import edu.stevens.cs581.representation.PollConnectionsRep;
import edu.stevens.cs581.representation.PollRep;
import edu.stevens.cs581.state.PollStateLocal;

public class ReturningUserPollServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger("edu.stevens.cs581.ReturningUserPollServlet");
	@EJB(beanName="StateBean")
	private PollStateLocal pollState;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.info("In doget topicname ========== ");
	    //System.out.println("topicname = " + name1);

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String code = request.getParameter("code");
		String pollId = request.getParameter("pollId");

		String MY_ACCESS_TOKEN = "";
		String authURL = "https://graph.facebook.com/oauth/access_token?client_id=459011260803995&redirect_uri=http://ec2-23-20-132-138.compute-1.amazonaws.com:8080/OSNAssignmentWeb/ReturningUserPollServlet?pollId="+pollId+ "&client_secret=18119c56c44ae6cc7defcea3ab4093ab&code="
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
		
		//String pollId = request.getParameter("pollId");
		//String userIdString = request.getParameter("userId");
		
	    long userId = new Long(user.getId()).longValue();
	    log.info("User: "+userId+" fbPollId: "+pollId);
	    
	    PollConnectionsRep connRep = new PollConnectionsRep();
	    connRep.setPollId(new Long(pollId));
	    connRep.setConnectionId(userId);
	    
	    PollRep pollRep = pollState.getPollForConnectionResponse(connRep);
	    
	    if ( pollRep != null )
	    {
	    	List<PollConnectionsRep> conns = pollRep.getConnectionsPolled();
	    	
	    	for ( int i = 0; i < conns.size(); i++)
	    	{
	    		PollConnectionsRep connFound = conns.get(i);
	    		
	    		if ( connFound.getConnectionId() != userId)
	    			continue;
	    		else
	    		{
	    			request.setAttribute("PollText",pollRep.getShortText());
	    			request.setAttribute("PollDescription",pollRep.getDescription());
	    	    	log.info("RETURING USER POLL SERVLET:::::::SHORTTEXT: "+pollRep.getShortText());	    			
	    			request.setAttribute("PollCreator",pollRep.getPollCreator());
	    			request.setAttribute("ConnectionResponse",connFound.getConnectionResponse());
	    			request.setAttribute("ConnectionResponseDate",connFound.getResponseReceivedDate());
	    			request.setAttribute("ConnectionPollSentDate",connFound.getPollSentDate());
	    			request.setAttribute("ConnectionRating",connFound.getRating());
	    			
	    			request.setAttribute("pollId", pollRep.getFbPollId());
	    			request.setAttribute("connPollId", connFound.getId());
	    		}
	    	}
	    }
	    else
	    {
	    	//@TODO redirect to index.html
	    	log.info("RETURING USER POLL SERVLET:::::::User: "+userId+" fbPollId: "+pollId+" not found.");
	    }

	    getServletConfig().getServletContext().getRequestDispatcher("/ReturningUser.jsp").forward(request, response);
	}	
	
}
