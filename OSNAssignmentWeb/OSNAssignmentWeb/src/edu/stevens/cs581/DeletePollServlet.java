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

import edu.stevens.cs581.representation.PollException;
import edu.stevens.cs581.representation.PollRep;
import edu.stevens.cs581.state.PollState;
import edu.stevens.cs581.state.PollStateLocal;



public class DeletePollServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger("edu.stevens.cs581.DeletePollServlet");
	
	@EJB(beanName="StateBean")
	private PollStateLocal pollState;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PollState state = new edu.stevens.cs581.state.PollState(); 
		String userId = request.getParameter("userId");
		PollRep pollRep = new PollRep();
		pollRep.setPollCreator(new Long(userId));
		try {
			pollState.markPollAsDeleted(pollRep);
		} catch (PollException e) {
			e.printStackTrace();
		}
		
		//@TODO set the values in the request object
		getServletConfig().getServletContext().getRequestDispatcher("/Delete.jsp").forward(request, response);

	}
}
