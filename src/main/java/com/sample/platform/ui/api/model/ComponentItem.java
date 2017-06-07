package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComponentItem {
	@JsonProperty("fixed")
	private Item fixed;

	@JsonProperty("fixedPosition")
	private String fixedPosition;

	@JsonProperty("items")
	private List<Item> items;

	public Item getFixed() {
		return fixed;
	}

	public void setFixed(Item fixed) {
		this.fixed = fixed;
	}

	public String getFixedPosition() {
		return fixedPosition;
	}

	public void setFixedPosition(String fixedPosition) {
		this.fixedPosition = fixedPosition;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public ComponentItem(Item fixed, String fixedPosition, List<Item> items) {
		this.fixed = fixed;
		this.fixedPosition = fixedPosition;
		this.items = items;
	}

	public ComponentItem() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixed == null) ? 0 : fixed.hashCode());
		result = prime * result + ((fixedPosition == null) ? 0 : fixedPosition.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		ComponentItem other = (ComponentItem) obj;
		if (fixed == null) {
			if (other.fixed != null)
				return false;
		} else if (!fixed.equals(other.fixed))
			return false;
		if (fixedPosition == null) {
			if (other.fixedPosition != null)
				return false;
		} else if (!fixedPosition.equals(other.fixedPosition))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
}
