package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteBody {
	@JsonProperty("vote")
	private Boolean vote;

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

	public VoteBody(Boolean vote) {
		this.vote = vote;
	}

	public VoteBody() {
		
	}
}