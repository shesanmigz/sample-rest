package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderLine {
	@JsonProperty("quantity")
	private BigDecimal quantity = null;

	@JsonProperty("title")
	private String title = null;

	@JsonProperty("subtitle")
	private String subtitle = null;

	@JsonProperty("duin")
	private String duin = null;

	@JsonProperty("imageUrl")
	private String imageUrl = null;

	@JsonProperty("merchantName")
	private String merchantName = null;

	@JsonProperty("merchantId")
	private String merchantId = null;

	@JsonProperty("unitPrice")
	private BigDecimal unitPrice = null;

	@JsonProperty("totalPrice")
	private BigDecimal totalPrice = null;

	@JsonProperty("deliveryTime")
	private String deliveryTime = null;

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDuin() {
		return duin;
	}

	public void setDuin(String duin) {
		this.duin = duin;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public OrderLine(BigDecimal quantity, String title, String subtitle, String duin, String imageUrl, String merchantName, String merchantId, BigDecimal unitPrice, BigDecimal totalPrice, String deliveryTime) {
		this.quantity = quantity;
		this.title = title;
		this.subtitle = subtitle;
		this.duin = duin;
		this.imageUrl = imageUrl;
		this.merchantName = merchantName;
		this.merchantId = merchantId;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.deliveryTime = deliveryTime;
	}
	
	public OrderLine() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
		result = prime * result + ((duin == null) ? 0 : duin.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((merchantId == null) ? 0 : merchantId.hashCode());
		result = prime * result + ((merchantName == null) ? 0 : merchantName.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
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
		OrderLine other = (OrderLine) obj;
		if (deliveryTime == null) {
			if (other.deliveryTime != null)
				return false;
		} else if (!deliveryTime.equals(other.deliveryTime))
			return false;
		if (duin == null) {
			if (other.duin != null)
				return false;
		} else if (!duin.equals(other.duin))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (merchantId == null) {
			if (other.merchantId != null)
				return false;
		} else if (!merchantId.equals(other.merchantId))
			return false;
		if (merchantName == null) {
			if (other.merchantName != null)
				return false;
		} else if (!merchantName.equals(other.merchantName))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (subtitle == null) {
			if (other.subtitle != null)
				return false;
		} else if (!subtitle.equals(other.subtitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		if (unitPrice == null) {
			if (other.unitPrice != null)
				return false;
		} else if (!unitPrice.equals(other.unitPrice))
			return false;
		return true;
	}
}
