package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersconfirmmerchantIdOrders {
	@JsonProperty("orderId")
	private String orderId;

	@JsonProperty("orderPosId")
	private String orderPosId;

	@JsonProperty("sku")
	private String sku;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderPosId() {
		return orderPosId;
	}

	public void setOrderPosId(String orderPosId) {
		this.orderPosId = orderPosId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public OrdersconfirmmerchantIdOrders(String orderId, String orderPosId, String sku) {
		this.orderId = orderId;
		this.orderPosId = orderPosId;
		this.sku = sku;
	}

	public OrdersconfirmmerchantIdOrders() {
		
	}
}
