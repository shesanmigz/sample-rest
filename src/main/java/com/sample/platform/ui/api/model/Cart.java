package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
	@JsonProperty("items")
	private List<CartItem> items;

	@JsonProperty("billSummary")
	private BillItem billSummary;

	@JsonProperty("bills")
	private List<BillItem> bills;

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public Cart(List<CartItem> items, BillItem billSummary, List<BillItem> bills) {
		this.items = items;
		this.billSummary = billSummary;
		this.bills = bills;
	}

	public Cart() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billSummary == null) ? 0 : billSummary.hashCode());
		result = prime * result + ((bills == null) ? 0 : bills.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		Cart other = (Cart) obj;
		if (billSummary == null) {
			if (other.billSummary != null)
				return false;
		} else if (!billSummary.equals(other.billSummary))
			return false;
		if (bills == null) {
			if (other.bills != null)
				return false;
		} else if (!bills.equals(other.bills))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
}
