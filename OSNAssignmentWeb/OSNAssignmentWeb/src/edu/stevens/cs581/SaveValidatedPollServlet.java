/**
 * 
 */
package edu.stevens.cs581;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.types.User;

import edu.stevens.cs581.representation.PollConnectionsRep;
import edu.stevens.cs581.representation.PollRep;
import edu.stevens.cs581.state.PollStateLocal;

public class SaveValidatedPollServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB(beanName="StateBean")
	private PollStateLocal pollState;
	
	private static Logger log = Logger.getLogger("edu.stevens.cs581.SaveValidatedPollServlet");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.info("In doget topicname ========== ");
	    //System.out.println("topicname = " + name1);

	}
		
	private String getFriendName( List<com.restfb.types.User> friendsList, String friendId )
	{
		for (User user : friendsList) {
			if ( user.getId() == friendId)
			{
				log.info("SaveValidatedPollServlet:getFriendName: Got friend as : Id"+ user.getId() + " Name:" +user.getName());
				return user.getName();
			}
		}
		return "";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String code = request.getParameter("code");

		String MY_ACCESS_TOKEN = "";
		String authURL = "https://graph.facebook.com/oauth/access_token?client_id=459011260803995&redirect_uri=http://ec2-23-20-132-138.compute-1.amazonaws.com:8080/OSNAssignment/SaveValidatedPollServlet&client_secret=18119c56c44ae6cc7defcea3ab4093ab&code="
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
		//request.setAttribute("friendsList",friendsList);

		*/
		
	    String pollId = request.getParameter("pollId");
	    String shortText = request.getParameter("topicName");
	    String description = request.getParameter("topicDesc");
	    String pollCreatorId = request.getParameter("userId");
	    String userIds = request.getParameter("polledConn");

	    
	    log.info("pollId ========== "+pollId+" shortText==========="+shortText+"pollCreatorId========"+pollCreatorId);
	    
	    PollRep pollRep = new PollRep();
	    pollRep.setFbPollId(new Long(pollId));
	    pollRep.setPollCreator(new Long(pollCreatorId));
	    pollRep.setShortText(shortText);
	    pollRep.setDescription(description);
	    
		log.info("USERIDS==========.........................."+ userIds);
	    List<PollConnectionsRep> connectionPolled = new ArrayList<PollConnectionsRep>();
	    
	    String[] splitConnections = userIds.split(",");
		log.info("RESULT==========.........................."+ splitConnections);	     
	    for (int x=0; x<splitConnections.length; x++)
	    {
	    	 PollConnectionsRep connRep = new PollConnectionsRep();
	    	 connRep.setConnectionId(new Long(splitConnections[x]));
	    	 //connRep.setName(getFriendName(friendsList,splitConnections[x]));
	    	 connRep.setName("");
	    	 connectionPolled.add(connRep);
	    }
	 
		log.info("Before saving polls.........................."+ pollId);	    
	    pollState.saveNewPoll(pollRep, connectionPolled);
	    log.info("saved poll"+ pollId);
	    RequestDispatcher dispatcher =  
	    	request.getRequestDispatcher("/index.html");  
	    if (dispatcher != null)  
	    	dispatcher.forward(request, response);

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
