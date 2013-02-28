package edu.stevens.cs581.state;

import java.util.List;

import edu.stevens.cs581.representation.PollConnectionsRep;
import edu.stevens.cs581.representation.PollException;
import edu.stevens.cs581.representation.PollRep;

public interface PollStateInterface {

	public PollRep saveNewPoll(PollRep pollRep, List<PollConnectionsRep> connectionPolled);
	public List<edu.stevens.cs581.representation.PollRep> getPollsForUser(PollRep poll);
	public PollRep getPollForConnectionResponse(PollConnectionsRep connRep);
	public void savePoll(PollRep pollRep);
	public void saveResponse(PollConnectionsRep connRep) throws PollException;
	public void markPollAsDeleted(PollRep pollRep) throws PollException;
	public void saveRating(PollConnectionsRep connRep) throws PollException;
}
