package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Offer {
	@JsonProperty("offerId")
	private String offerId;
	
	@JsonProperty("price")
	private BigDecimal price;
	
	@JsonProperty("seller")
	private String seller;
	
	@JsonProperty("deliveryDate")
	private String deliveryDate;
	
	@JsonProperty("condition")
	private String condition;
	
	@JsonProperty("itemNote")
	private String itemNote;
	
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
	
	public String getSeller() {
		return seller;
	}
	
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getItemNote() {
		return itemNote;
	}
	
	public void setItemNote(String itemNote) {
		this.itemNote = itemNote;
	}
	
	public Offer(String offerId, BigDecimal price, String seller, String deliveryDate, String condition,
			String itemNote) {
		this.offerId = offerId;
		this.price = price;
		this.seller = seller;
		this.deliveryDate = deliveryDate;
		this.condition = condition;
		this.itemNote = itemNote;
	}
	
	public Offer() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		result = prime * result + ((itemNote == null) ? 0 : itemNote.hashCode());
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
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
		Offer other = (Offer) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
		if (itemNote == null) {
			if (other.itemNote != null)
				return false;
		} else if (!itemNote.equals(other.itemNote))
			return false;
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
		if (seller == null) {
			if (other.seller != null)
				return false;
		} else if (!seller.equals(other.seller))
			return false;
		return true;
	}
}
