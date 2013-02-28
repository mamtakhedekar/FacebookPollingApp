package edu.stevens.cs581.state;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs581.domain.*;
import edu.stevens.cs581.representation.*;

/**
 * Session Bean implementation class PollState
 */
@Stateless(name="StateBean")
public class PollState implements PollStateRemote, PollStateLocal {


	private static Logger log = Logger.getLogger("edu.stevens.cs581.state.PollState");
	
    @PersistenceContext(unitName="FacebookPoll")
    EntityManager em;
    
    /**
     * Default constructor. 
     */
    public PollState() {
    }

    
    public PollRep saveNewPoll( PollRep pollRep, List<PollConnectionsRep> connections )
    {
    	Polls poll = createNewPoll(pollRep);
    	poll = createConnectionsForPoll(poll, connections);
    	em.persist(poll);
    	em.flush();
    	//log.info("AFTER PERSIST, poll.getPollID===..................."+poll.getPollID());    	
    	pollRep.setId(poll.getPollID());
    	
    	return pollRep;
    }
    
    private edu.stevens.cs581.domain.Polls createNewPoll( PollRep pollRep )
    {
    	///log.info("Inside CREATENEWPOLL...................");
    	Polls poll = new Polls();
    	poll.setIsDeleted(false);
    	poll.setMemberID(pollRep.getPollCreator());
    	poll.setPollShortText(pollRep.getShortText());
    	poll.setPollDescription(pollRep.getDescription());
    	poll.setFbPollId(pollRep.getFbPollId());
    	//log.info("BEFORE RETURNING FROM CREATENEWPOLL...................");
    	return poll;
    }
    
    private Polls createConnectionsForPoll( Polls poll, List<PollConnectionsRep> connections )
    {
    	//log.info("INSIDE CREATECONNECTIONSFORPOLL...................");    	
    	List<ConnectionsPolled> connsPolledDomain = new ArrayList<ConnectionsPolled>();
    	for (PollConnectionsRep pollConnectionsRep : connections) {
			
    		ConnectionsPolled conn = new ConnectionsPolled();
    		
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date = new Date();
        	conn.setFbPollId(poll.getFbPollId());
        	conn.setName(pollConnectionsRep.getName());
    		conn.setPollSentDate((dateFormat.format(date)).toString());
    		conn.setConnectionId(pollConnectionsRep.getConnectionId());
    		conn.setResponse("");
    		conn.setRating(0);
    		connsPolledDomain.add(conn);
		}
    	//log.info("BEFORE RETURNING FROM CREATECONNECTIONSFORPOLL...................");    	
    	poll.setItems(connsPolledDomain);
    	return poll;
    }
    
    public List<edu.stevens.cs581.representation.PollRep> getPollsForUser(PollRep pollRep)
    {
    	javax.persistence.TypedQuery<Polls> query = em.createNamedQuery("SearchByUser", Polls.class).setParameter("key", pollRep.getPollCreator());
    	List<PollRep> pollList = new ArrayList<PollRep>(); 
    	int sizeOfList = query.getResultList().size();
    	log.info("getPollsForUser: Got "+sizeOfList+" polls for user:"+pollRep.getPollCreator()+ " for " );
    	for ( int i=0; i< sizeOfList; i++ )
    	{
    		Polls poll = query.getResultList().get(i);
    		
    		javax.persistence.TypedQuery<ConnectionsPolled> queryConns = 
    				em. createNamedQuery("SearchAllConnectionsForFbPollId", ConnectionsPolled.class).
    				setParameter("pollKey", poll.getFbPollId());
            
            List<ConnectionsPolled> conns = queryConns.getResultList();
        	log.info("GetPollForConnectionResponse: connsSIZE=====:"+conns.size());
            
        	
            poll.setItems(conns);
    		pollList.add(copyToPollRepFromDao(poll));
    	}
    	query.getResultList();
    	return pollList;
    }
    
    private PollRep copyToPollRepFromDao(Polls poll)
    {
    	PollRep rep = new PollRep();
    	
    	rep.setFbPollId(poll.getFbPollId());
    	rep.setId(poll.getPollID());
    	rep.setPollCreator(poll.getMemberID());
    	rep.setShortText(poll.getPollShortText());
    	rep.setDescription(poll.getPollDescription());
    	rep.setConnectionsPolled(new ArrayList<PollConnectionsRep>());

    	int sizeOfPolls = poll.getItems().size();
    	log.info("Number of Polls :" + sizeOfPolls);    	
    	for ( int i = 0 ; i < sizeOfPolls; i++)
    	{
    		ConnectionsPolled conn = poll.getItems().get(i);
    		PollConnectionsRep connRep = new PollConnectionsRep();
    		
    		connRep.setConnectionId(conn.getConnectionId());
    		connRep.setName(conn.getName());
    		connRep.setConnectionResponse(conn.getResponse());
    		connRep.setId(conn.getId());
    		connRep.setPollId(conn.getFbPollId());
    		connRep.setPollSentDate(conn.getPollSentDate());
    		connRep.setResponseReceivedDate(conn.getResponseReceivedDate());
    		connRep.setRating(conn.getRating());
    		rep.getConnectionsPolled().add(connRep);
    	}
    	
    	return rep;
    }
    
    private Polls copyFromPollRepToDao(PollRep pollrep)
    {
    	Polls poll = new Polls();
    	
    	poll.setFbPollId(pollrep.getFbPollId());
    	poll.setPollID((int)pollrep.getId());
    	poll.setMemberID(pollrep.getPollCreator());
    	poll.setPollShortText(pollrep.getShortText());
    	poll.setPollDescription(pollrep.getDescription());
    	poll.setItems(new ArrayList<ConnectionsPolled>());
    	
    	int sizeOfPolls = pollrep.getConnectionsPolled().size();
    	for ( int i = 0 ; i < sizeOfPolls; i++)
    	{
    		PollConnectionsRep connRep = pollrep.getConnectionsPolled().get(i);
    		ConnectionsPolled conn = new ConnectionsPolled();
    		
    		conn.setConnectionId(connRep.getConnectionId());
    		conn.setName(connRep.getName());
    		conn.setResponse(connRep.getConnectionResponse());
    		conn.setId(connRep.getId());
    		conn.setPollSentDate(connRep.getPollSentDate());
    		conn.setResponseReceivedDate(connRep.getResponseReceivedDate());
    		conn.setRating(connRep.getRating());
    		poll.getItems().add(conn);
    	}
    	
    	return poll;
    }
    
    public void savePoll(PollRep pollRep)
    {
    	em.merge(copyFromPollRepToDao(pollRep));
    }
    
    public PollRep getPollForConnectionResponse(PollConnectionsRep connRep)
    {
    	PollRep pollRep = null;
    	
    	javax.persistence.TypedQuery<Polls> query = em.createNamedQuery("SearchByPollId", Polls.class).setParameter("key", connRep.getPollId());

    	List<Polls> polls = query.getResultList();
    	log.info("GetPollForConnectionResponse: Returned:"+polls.size()+" for user:"+connRep.getConnectionId()+" PollId:"+connRep.getPollId());
    	
    	if ( polls.size() == 0 )
    	{
    		return pollRep;
    	}
    	else if (polls.size() >= 1 )
    	{
    		Polls poll = polls.get(0);
    		
    		javax.persistence.TypedQuery<ConnectionsPolled> queryConns = em.createNamedQuery("SearchConnectionByPollFbPollId", ConnectionsPolled.class).setParameter("pollKey", poll.getFbPollId());
    		queryConns.setParameter("connectionKey", connRep.getConnectionId());
            
            List<ConnectionsPolled> conns = queryConns.getResultList();
        	log.info("GetPollForConnectionResponse: connsSIZE=====:"+conns.size());
            
            poll.setItems(conns);    		
    		
    		if (poll.getIsDeleted())
    		{
    			log.info("Poll:"+poll.getFbPollId()+" is deleted already cannot fetch!!!");
    			return pollRep;
    		}
    		else
    			return copyToPollRepFromDao(poll);
    	}
    	return pollRep;
    }
    
    public void saveResponse(PollConnectionsRep connRep) throws PollException
    {

		javax.persistence.TypedQuery<ConnectionsPolled> queryConns = em.createNamedQuery("SearchConnectionByPollFbPollId", ConnectionsPolled.class).setParameter("pollKey", connRep.getPollId());
		queryConns.setParameter("connectionKey", connRep.getConnectionId());
		
		List<ConnectionsPolled> conns = queryConns.getResultList();
		
		log.info("SaveResponse:connpollId: "+ connRep.getPollId() + " connId: "+connRep.getConnectionId()+" returned " + conns.size() + " connections");
		if ( conns.size() != 1 )
		{
			throw new PollException(0, "There are more than one connection entries for a poll");
		}
		
		ConnectionsPolled conn = conns.get(0);
		conn.setResponse(connRep.getConnectionResponse());
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	conn.setResponseReceivedDate(dateFormat.format(date).toString());
    	em.persist(conn);
    }
    
    public void saveRating(PollConnectionsRep connRep) throws PollException
    {

		javax.persistence.TypedQuery<ConnectionsPolled> queryConns = em.createNamedQuery("SearchConnectionByPollFbPollId", ConnectionsPolled.class).setParameter("pollKey", connRep.getPollId());
		queryConns.setParameter("connectionKey", connRep.getConnectionId());
		
		List<ConnectionsPolled> conns = queryConns.getResultList();
		
		log.info("SaveResponse:connpollId: "+ connRep.getPollId() + " connId: "+connRep.getConnectionId()+" returned " + conns.size() + " connections");
		if ( conns.size() != 1 )
		{
			throw new PollException(0, "There are more than one connection entries for a poll");
		}
		
		ConnectionsPolled conn = conns.get(0);
		conn.setRating(connRep.getRating());
    	em.persist(conn);
    }
    
    
    @Deprecated
    public void deletePoll( Polls poll )
    {
    	em.remove(poll);
    }
    
    public void markPollAsDeleted(PollRep pollRep) throws PollException
    {
    	Polls poll = new Polls();
    	poll.setPollID((int)pollRep.getId());
    	em.find(Polls.class, poll);
    	
    	poll.setIsDeleted(true);
    	em.persist(poll);
    }
}
