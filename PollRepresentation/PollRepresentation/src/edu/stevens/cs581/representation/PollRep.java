package edu.stevens.cs581.representation;

import java.util.List;


public class PollRep {

	private long pollCreator;
	private String shortText;
	private String description;
	private long id;
	private long fbPollId;
	private String yelpResult;
	
	private List<PollConnectionsRep> connectionsPolled;
	
	public long getPollCreator() {
		return pollCreator;
	}
	public void setPollCreator(long pollCreator) {
		this.pollCreator = pollCreator;
	}
	public String getShortText() {
		return shortText;
	}
	public void setShortText(String shortText) {
		this.shortText = shortText;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the fbPollId
	 */
	public long getFbPollId() {
		return fbPollId;
	}
	/**
	 * @param fbPollId the fbPollId to set
	 */
	public void setFbPollId(long fbPollId) {
		this.fbPollId = fbPollId;
	}
	/**
	 * @return the connectionsPolled
	 */
	public List<PollConnectionsRep> getConnectionsPolled() {
		return connectionsPolled;
	}
	/**
	 * @param connectionsPolled the connectionsPolled to set
	 */
	public void setConnectionsPolled(List<PollConnectionsRep> connectionsPolled) {
		this.connectionsPolled = connectionsPolled;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the yelpResult
	 */
	public String getYelpResult() {
		return yelpResult;
	}
	/**
	 * @param yelpResult the yelpResult to set
	 */
	public void setYelpResult(String yelpResult) {
		this.yelpResult = yelpResult;
	}
	
}
