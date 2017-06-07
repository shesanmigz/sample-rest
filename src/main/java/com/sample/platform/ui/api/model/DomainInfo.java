package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainInfo {
	@JsonProperty("name")
	private String name;

	@JsonProperty("link")
	private String link;

	@JsonProperty("domainId")
	private String domainId;

	@JsonProperty("defaultCountry")
	private Country defaultCountry;

	@JsonProperty("currency")
	private Currency currency;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public Country getDefaultCountry() {
		return defaultCountry;
	}

	public void setDefaultCountry(Country defaultCountry) {
		this.defaultCountry = defaultCountry;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public DomainInfo(String name, String link, String domainId, Country defaultCountry, Currency currency) {
		this.name = name;
		this.link = link;
		this.domainId = domainId;
		this.defaultCountry = defaultCountry;
		this.currency = currency;
	}

	public DomainInfo() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((defaultCountry == null) ? 0 : defaultCountry.hashCode());
		result = prime * result + ((domainId == null) ? 0 : domainId.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DomainInfo other = (DomainInfo) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (defaultCountry == null) {
			if (other.defaultCountry != null)
				return false;
		} else if (!defaultCountry.equals(other.defaultCountry))
			return false;
		if (domainId == null) {
			if (other.domainId != null)
				return false;
		} else if (!domainId.equals(other.domainId))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
