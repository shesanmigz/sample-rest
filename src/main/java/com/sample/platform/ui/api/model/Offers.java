package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Offers {
	@JsonProperty("primary")
	private Offer primary;
	
	@JsonProperty("all")
	private List<Offer> all;

	public Offer getPrimary() {
		return primary;
	}

	public void setPrimary(Offer primary) {
		this.primary = primary;
	}

	public List<Offer> getAll() {
		return all;
	}

	public void setAll(List<Offer> all) {
		this.all = all;
	}

	public Offers(Offer primary, List<Offer> all) {
		this.primary = primary;
		this.all = all;
	}

	public Offers() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((all == null) ? 0 : all.hashCode());
		result = prime * result + ((primary == null) ? 0 : primary.hashCode());
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
		Offers other = (Offers) obj;
		if (all == null) {
			if (other.all != null)
				return false;
		} else if (!all.equals(other.all))
			return false;
		if (primary == null) {
			if (other.primary != null)
				return false;
		} else if (!primary.equals(other.primary))
			return false;
		return true;
	}
}
