package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartBill {
	@JsonProperty("billSummary")
	private Bill billSummary;

	@JsonProperty("bills")
	private List<Bill> bills;

	@JsonProperty("items")
	private List<CartOffer> items;

	public Bill getBillSummary() {
		return billSummary;
	}

	public void setBillSummary(Bill billSummary) {
		this.billSummary = billSummary;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public List<CartOffer> getItems() {
		return items;
	}

	public void setItems(List<CartOffer> items) {
		this.items = items;
	}

	public CartBill(Bill billSummary, List<Bill> bills, List<CartOffer> items) {
		this.billSummary = billSummary;
		this.bills = bills;
		this.items = items;
	}

	public CartBill() {
		
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
		CartBill other = (CartBill) obj;
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
