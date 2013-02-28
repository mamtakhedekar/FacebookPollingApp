package edu.stevens.cs581.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

import com.sun.istack.Nullable;

/**
 * Entity implementation class for Entity: ConnectionsPolled
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "SearchConnectionForPoll", query = "select k from ConnectionsPolled as k where k.connectionId = :connectionKey and k.pollId = :pollKey"),
	@NamedQuery(name = "SearchConnectionByPollFbPollId", query = "select k from ConnectionsPolled as k where k.connectionId = :connectionKey and k.fbPollId = :pollKey"),
	@NamedQuery(name = "SearchAllConnectionsForFbPollId", query = "select k from ConnectionsPolled as k where k.fbPollId = :pollKey"),
})

@Table(name="ConnectionsPolled")
public class ConnectionsPolled implements Serializable {

	@Id @GeneratedValue
	private long id;
	@ManyToOne(targetEntity=edu.stevens.cs581.domain.Polls.class,optional=true)
	@JoinColumn(name="pollId")
	private Polls pollId;
	
	@Column(name="name")
	private String Name;
	
	@Column(name="fbPollId")
	private long fbPollId;
	
	private long connectionId;
	public long getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(long connectionId) {
		this.connectionId = connectionId;
	}
	
	@Nullable
	private String reponseString;
	private String pollSentDate;
	private String responseReceivedDate;
	private int rating;
	
	private static final long serialVersionUID = 1L;

	public ConnectionsPolled() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public Polls getPollId() {
		return this.pollId;
	}

	public void setPollId(Polls pollId) {
		this.pollId = pollId;
	}   
	public String getResponse() {
		return this.reponseString;
	}

	public void setResponse(String Response) {
		this.reponseString = Response;
	}   
	public String getPollSentDate() {
		return this.pollSentDate;
	}

	public void setPollSentDate(String pollSentDate) {
		this.pollSentDate = pollSentDate;
	}   
	public String getResponseReceivedDate() {
		return this.responseReceivedDate;
	}

	public void setResponseReceivedDate(String ResponseReceivedDate) {
		this.responseReceivedDate = ResponseReceivedDate;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
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
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
   
}
