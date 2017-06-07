package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartOffer {
	@JsonProperty("offerId")
	private String offerId;

	@JsonProperty("price")
	private BigDecimal price;

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public CartOffer(String offerId, BigDecimal price) {
		this.offerId = offerId;
		this.price = price;
	}

	public CartOffer() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		CartOffer other = (CartOffer) obj;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
}
