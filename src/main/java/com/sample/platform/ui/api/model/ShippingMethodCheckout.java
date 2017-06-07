package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingMethodCheckout {
	@JsonProperty("selected")
	private ShippingMethod selected;

	@JsonProperty("all")
	private List<ShippingMethod> all;

	public ShippingMethod getSelected() {
		return selected;
	}

	public void setSelected(ShippingMethod selected) {
		this.selected = selected;
	}

	public List<ShippingMethod> getAll() {
		return all;
	}

	public void setAll(List<ShippingMethod> all) {
		this.all = all;
	}

	public ShippingMethodCheckout(ShippingMethod selected, List<ShippingMethod> all) {
		this.selected = selected;
		this.all = all;
	}

	public ShippingMethodCheckout() {
		
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
		ShippingMethodCheckout other = (ShippingMethodCheckout) obj;
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
