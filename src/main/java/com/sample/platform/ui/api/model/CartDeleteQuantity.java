package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartDeleteQuantity {
	@JsonProperty("quantity")
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartDeleteQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartDeleteQuantity() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		CartDeleteQuantity other = (CartDeleteQuantity) obj;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
}
