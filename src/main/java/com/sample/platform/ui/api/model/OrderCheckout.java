package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderCheckout {
	@JsonProperty("bill")
	private BillItem bill;

	@JsonProperty("orderLines")
	private List<OrderLine> orderLines;

	public BillItem getBill() {
		return bill;
	}

	public void setBill(BillItem bill) {
		this.bill = bill;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public OrderCheckout(BillItem bill, List<OrderLine> orderLines) {
		this.bill = bill;
		this.orderLines = orderLines;
	}

	public OrderCheckout() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bill == null) ? 0 : bill.hashCode());
		result = prime * result + ((orderLines == null) ? 0 : orderLines.hashCode());
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
		OrderCheckout other = (OrderCheckout) obj;
		if (bill == null) {
			if (other.bill != null)
				return false;
		} else if (!bill.equals(other.bill))
			return false;
		if (orderLines == null) {
			if (other.orderLines != null)
				return false;
		} else if (!orderLines.equals(other.orderLines))
			return false;
		return true;
	}
}
