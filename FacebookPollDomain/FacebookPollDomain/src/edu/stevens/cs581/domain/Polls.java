package edu.stevens.cs581.domain;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

import com.sun.istack.NotNull;

import edu.stevens.cs581.domain.ConnectionsPolled; 

/**
 * Entity implementation class for Entity: Polls
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "SearchAll", query = "select k from Polls k"),
	@NamedQuery(name = "DeleteAll", query = "delete from Polls k"),
	@NamedQuery(name = "SearchByUser", query = "select k from Polls as k where k.IsDeleted <> TRUE and k.MemberID = :key"),
	@NamedQuery(name = "DeleteByUser", query = "delete from Polls k where k.MemberID = :key"),
	@NamedQuery(name = "SearchByPollId", query = "select k from Polls as k where k.fbPollId = :key"),
	@NamedQuery(name = "DeleteByPollId", query = "delete from Polls k where k.fbPollId = :key"),
})

@Table(name="Polls")
public class Polls implements Serializable {

	
	@Id @GeneratedValue
	@Column(name="pollId")
	private long PollID;
	@NotNull
	@Column(name="fbPollId")
	private long fbPollId;
	@NotNull
	@Column(name="memberId")
	private long MemberID;
	@NotNull
	@Column(name="pollshortText")
	private String PollShortText;
	@NotNull
	@Column(name="pollDescription")
	private String PollDescription;
	@NotNull
	@Column(name="isdeleted")
	private Boolean IsDeleted;
	private static final long serialVersionUID = 1L;

	public Polls() {
		super();
	}   
	public long getPollID() {
		return this.PollID;
	}

	public void setPollID(int PollID) {
		this.PollID = PollID;
	}   
	public Long getMemberID() {
		return this.MemberID;
	}

	public void setMemberID(Long MemberID) {
		this.MemberID = MemberID;
	}   
	public String getPollShortText() {
		return this.PollShortText;
	}

	public void setPollShortText(String PollShortText) {
		this.PollShortText = PollShortText;
	}   
	public Boolean getIsDeleted() {
		return this.IsDeleted;
	}

	public void setIsDeleted(Boolean IsDeleted) {
		this.IsDeleted = IsDeleted;
	}

	@OneToMany(mappedBy = "pollId",cascade=CascadeType.ALL, targetEntity=edu.stevens.cs581.domain.ConnectionsPolled.class)
	@OrderBy
	private List<ConnectionsPolled> connections;

	public List<ConnectionsPolled> getItems() {
		return connections;
	}

	public void setItems(List<ConnectionsPolled> items) {
		this.connections = items;
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
	 * @return the pollDescription
	 */
	public String getPollDescription() {
		return PollDescription;
	}
	/**
	 * @param pollDescription the pollDescription to set
	 */
	public void setPollDescription(String pollDescription) {
		PollDescription = pollDescription;
	}

	
}
