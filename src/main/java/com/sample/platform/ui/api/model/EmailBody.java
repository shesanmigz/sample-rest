package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailBody {
	@JsonProperty("template")
	private String template;
	
	@JsonProperty("receivers")
	private List<EmailReceivers> receivers;
	
	@JsonProperty("placeholders")
	private List<EmailPlaceHolders> placeholders;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<EmailReceivers> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<EmailReceivers> receivers) {
		this.receivers = receivers;
	}

	public List<EmailPlaceHolders> getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(List<EmailPlaceHolders> placeholders) {
		this.placeholders = placeholders;
	}

	public EmailBody(String template, List<EmailReceivers> receivers, List<EmailPlaceHolders> placeholders) {
		this.template = template;
		this.receivers = receivers;
		this.placeholders = placeholders;
	}

	public EmailBody() {
		
	}
}
