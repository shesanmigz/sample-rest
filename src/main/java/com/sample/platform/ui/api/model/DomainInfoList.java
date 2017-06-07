package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class DomainInfoList {
	private final List<DomainInfo> domainInfoList;
	
	@JsonValue
	public List<DomainInfo> getDomainInfoList() {
		return domainInfoList;
	}

	public DomainInfoList(List<DomainInfo> domainInfoList) {
		this.domainInfoList = domainInfoList;
	}
}
