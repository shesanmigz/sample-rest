package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillBody {
	@JsonProperty("items")
	private List<BillItems> items;

	public List<BillItems> getItems() {
		return items;
	}

	public void setItems(List<BillItems> items) {
		this.items = items;
	}

	public BillBody(List<BillItems> items) {
		this.items = items;
	}

	public BillBody() {
		
	}
}
