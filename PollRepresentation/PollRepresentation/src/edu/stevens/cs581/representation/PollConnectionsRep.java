package edu.stevens.cs581.representation;

public class PollConnectionsRep {

	private long connectionId;
	private String name;
	private String connectionResponse;
	private String pollSentDate;
	private String responseReceivedDate;
	private int rating;
	private long id;
	private long pollId;
	
	public long getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(long connectionId) {
		this.connectionId = connectionId;
	}
	public String getConnectionResponse() {
		return connectionResponse;
	}
	public void setConnectionResponse(String connectionResponse) {
		this.connectionResponse = connectionResponse;
	}
	public String getPollSentDate() {
		return pollSentDate;
	}
	public void setPollSentDate(String pollSentDate) {
		this.pollSentDate = pollSentDate;
	}
	public String getResponseReceivedDate() {
		return responseReceivedDate;
	}
	public void setResponseReceivedDate(String responseReceivedDate) {
		this.responseReceivedDate = responseReceivedDate;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPollId() {
		return pollId;
	}
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
