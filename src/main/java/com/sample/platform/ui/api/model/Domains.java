package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class Domains {
	private final List<DomainLink> domains;

	@JsonValue
	public List<DomainLink> getDomains() {
		return domains;
	}

	public Domains(List<DomainLink> domains) {
		this.domains = domains;
	}
}
