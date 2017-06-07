package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillItems {
	@JsonProperty("offerId")
	private String offerId;

	@JsonProperty("duin")
	private String duin;

	@JsonProperty("sku")
	private String sku;

	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("quantity")
	private Integer quantity;

	@JsonProperty("merchant")
	private String merchant;

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("condition")
	private String condition;

	@JsonProperty("itemNote")
	private String itemNote;

	@JsonProperty("taxtype")
	private Integer taxtype;

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getDuin() {
		return duin;
	}

	public void setDuin(String duin) {
		this.duin = duin;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
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

	public Integer getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(Integer taxtype) {
		this.taxtype = taxtype;
	}

	public BillItems(String offerId, String duin, String sku, BigDecimal price, Integer quantity, String merchant,
			String deliveryDate, String condition, String itemNote, Integer taxtype) {
		this.offerId = offerId;
		this.duin = duin;
		this.sku = sku;
		this.price = price;
		this.quantity = quantity;
		this.merchant = merchant;
		this.deliveryDate = deliveryDate;
		this.condition = condition;
		this.itemNote = itemNote;
		this.taxtype = taxtype;
	}

	public BillItems() {
		
	}
}
