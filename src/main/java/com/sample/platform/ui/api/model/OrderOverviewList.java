package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class OrderOverviewList {
	private final List<OrderOverview> orderOverviewList;
	
	@JsonValue
	public List<OrderOverview> getOrderOverviewList() {
		return orderOverviewList;
	}

	public OrderOverviewList(List<OrderOverview> orderOverviewList) {
		this.orderOverviewList = orderOverviewList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(orderOverviewList);
	}
}
