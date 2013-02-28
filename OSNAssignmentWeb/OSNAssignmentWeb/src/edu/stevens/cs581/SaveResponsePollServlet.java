/**
 * 
 */
package edu.stevens.cs581;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.stevens.cs581.representation.PollConnectionsRep;
import edu.stevens.cs581.state.PollStateLocal;

public class SaveResponsePollServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger("edu.stevens.cs581.SaveResponsePollServlet");
	@EJB(beanName="StateBean")
	private PollStateLocal pollState;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.info("In doget topicname ========== ");
	    //System.out.println("topicname = " + name1);

	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userReply = (String) request.getParameter("userReply");
		String pollId = (String) request.getParameter("pollId");
		String userId = (String) request.getParameter("userId");
//		String connectionId = (String)request.getParameter("connPollId"); 
		
		PollConnectionsRep connRep = new PollConnectionsRep();
		
		log.info("SaveResponsePollServlet: pollId: "+pollId+ " connectionId: " + userId + " userReply: "+userReply);
		connRep.setConnectionId(new Long(userId));
		connRep.setConnectionResponse(userReply);
		connRep.setPollId(new Long(pollId));
		
		try
		{
			pollState.saveResponse(connRep);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new ServletException(ex.getMessage());
		}
		
	    getServletConfig().getServletContext().getRequestDispatcher("/index.html").forward(request, response);
	}	

}
