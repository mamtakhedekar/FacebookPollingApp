package edu.stevens.cs581;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.stevens.cs581.representation.PollConnectionsRep;
import edu.stevens.cs581.state.PollStateLocal;

/**
 * Servlet implementation class SaveLookUpServlet
 */
@WebServlet("/SaveLookUpServlet")
public class SaveLookUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB(beanName="StateBean")
	private PollStateLocal pollState;
	
	private static Logger log = Logger.getLogger("edu.stevens.cs581.SaveLookUpServlet");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveLookUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String connections = (String) request.getParameter("allUserIds");
		String pollIds = (String) request.getParameter("allFBPollIds");
		String ratings = (String) request.getParameter("allRatings");
		
		String[] connectionFbIds = connections.split(",");
		String[] pollFbIds = pollIds.split(",");
		String[] connectionRatings = ratings.split(",");
		
		
		log.info("SaveLookUpServlet: There are "+ connectionFbIds + " ratings to be saved");	     
	    for (int x=0; x<connectionFbIds.length; x++)
	    {
	    	 PollConnectionsRep connRep = new PollConnectionsRep();
	    	 
	    	 connRep.setConnectionId(new Long(connectionFbIds[x]));
	    	 connRep.setPollId(new Long(pollFbIds[x]));
	    	 connRep.setRating(new Integer(connectionRatings[x]));

	    	try
	 		{
	    		log.info("SaveLookUpServlet: Trying to save rating for pollId: "+connRep.getPollId() + 
	    				" connectionId: " + connRep.getConnectionId() + " rating: "+connRep.getRating());
	 			pollState.saveRating(connRep);
	 		}
	 		catch (Exception ex)
	 		{
	 			ex.printStackTrace();
	 			throw new ServletException(ex.getMessage());
	 		}
	    }
	    getServletConfig().getServletContext().getRequestDispatcher("/index.html").forward(request, response);
	}

}
