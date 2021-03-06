package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HandlingCheckout {
	@JsonProperty("selected")
	private List<Handling> selected;

	@JsonProperty("all")
	private List<Handling> all;

	public List<Handling> getSelected() {
		return selected;
	}

	public void setSelected(List<Handling> selected) {
		this.selected = selected;
	}

	public List<Handling> getAll() {
		return all;
	}

	public void setAll(List<Handling> all) {
		this.all = all;
	}

	public HandlingCheckout(List<Handling> selected, List<Handling> all) {
		this.selected = selected;
		this.all = all;
	}

	public HandlingCheckout() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((all == null) ? 0 : all.hashCode());
		result = prime * result + ((selected == null) ? 0 : selected.hashCode());
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
		HandlingCheckout other = (HandlingCheckout) obj;
		if (all == null) {
			if (other.all != null)
				return false;
		} else if (!all.equals(other.all))
			return false;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		return true;
	}
}