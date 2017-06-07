package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
	@JsonProperty("id")
	private BigDecimal id;

	@JsonProperty("user")
	private String user;

	@JsonProperty("date")
	private String date;

	@JsonProperty("content")
	private String content;

	@JsonProperty("title")
	private String title;

	@JsonProperty("votes")
	private Votes votes;

	@JsonProperty("voted")
	private BigDecimal voted;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Votes getVotes() {
		return votes;
	}

	public void setVotes(Votes votes) {
		this.votes = votes;
	}

	public BigDecimal getVoted() {
		return voted;
	}

	public void setVoted(BigDecimal voted) {
		this.voted = voted;
	}

	public Review(BigDecimal id, String user, String date, String content, String title, Votes votes,
			BigDecimal voted) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.content = content;
		this.title = title;
		this.votes = votes;
		this.voted = voted;
	}

	public Review() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((voted == null) ? 0 : voted.hashCode());
		result = prime * result + ((votes == null) ? 0 : votes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (voted == null) {
			if (other.voted != null)
				return false;
		} else if (!voted.equals(other.voted))
			return false;
		if (votes == null) {
			if (other.votes != null)
				return false;
		} else if (!votes.equals(other.votes))
			return false;
		return true;
	}
}