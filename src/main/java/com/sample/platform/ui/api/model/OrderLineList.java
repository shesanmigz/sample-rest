package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class OrderLineList {
	private final List<OrderLine> orderLineList;

	@JsonValue
	public List<OrderLine> getOrderLineList() {
		return orderLineList;
	}
	
	public OrderLineList(List<OrderLine> orderLineList) {
		this.orderLineList = orderLineList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(orderLineList);
	}
}
