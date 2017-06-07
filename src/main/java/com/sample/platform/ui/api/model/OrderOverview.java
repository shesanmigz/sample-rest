package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderOverview {
	@JsonProperty("date")
	private String date = null;

	@JsonProperty("orderId")
	private String orderId = null;

	@JsonProperty("status")
	private String status = null;

	@JsonProperty("amount")
	private BigDecimal amount = null;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public OrderOverview(String date, String orderId, String status, BigDecimal amount) {
		this.date = date;
		this.orderId = orderId;
		this.status = status;
		this.amount = amount;
	}
	
	public OrderOverview() {
		
	}
}
