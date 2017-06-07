package com.sample.platform.ui.api.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
	@JsonProperty("date")
	private String date;

	@JsonProperty("orderId")
	private String orderId;

	@JsonProperty("status")
	private String status;

	@JsonProperty("amount")
	private BigDecimal amount;

	@JsonProperty("shippingAddress")
	private Address shippingAddress;

	@JsonProperty("billingAddress")
	private Address billingAddress;

	@JsonProperty("shippingMethod")
	private String shippingMethod;

	@JsonProperty("paymentMethod")
	private String paymentMethod;

	@JsonProperty("bill")
	private Bill bill;

	@JsonProperty("orderLines")
	private List<OrderLine> orderLines;

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

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
	public Order(String date, String orderId, String status, BigDecimal amount) {
		this.date = date;
		this.orderId = orderId;
		this.status = status;
		this.amount = amount;
		this.shippingAddress = null;
		this.billingAddress = null;
		this.shippingMethod = null;
		this.paymentMethod = null;
		this.bill = null;
		this.orderLines = null;
	}

	public Order(String date, String orderId, String status, BigDecimal amount, Address shippingAddress, Address billingAddress, String shippingMethod, String paymentMethod, Bill bill, List<OrderLine> orderLines) {
		this.date = date;
		this.orderId = orderId;
		this.status = status;
		this.amount = amount;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.shippingMethod = shippingMethod;
		this.paymentMethod = paymentMethod;
		this.bill = bill;
		this.orderLines = orderLines;
	}
	
	public Order() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((bill == null) ? 0 : bill.hashCode());
		result = prime * result + ((billingAddress == null) ? 0 : billingAddress.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderLines == null) ? 0 : orderLines.hashCode());
		result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime * result + ((shippingAddress == null) ? 0 : shippingAddress.hashCode());
		result = prime * result + ((shippingMethod == null) ? 0 : shippingMethod.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Order other = (Order) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bill == null) {
			if (other.bill != null)
				return false;
		} else if (!bill.equals(other.bill))
			return false;
		if (billingAddress == null) {
			if (other.billingAddress != null)
				return false;
		} else if (!billingAddress.equals(other.billingAddress))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderLines == null) {
			if (other.orderLines != null)
				return false;
		} else if (!orderLines.equals(other.orderLines))
			return false;
		if (paymentMethod == null) {
			if (other.paymentMethod != null)
				return false;
		} else if (!paymentMethod.equals(other.paymentMethod))
			return false;
		if (shippingAddress == null) {
			if (other.shippingAddress != null)
				return false;
		} else if (!shippingAddress.equals(other.shippingAddress))
			return false;
		if (shippingMethod == null) {
			if (other.shippingMethod != null)
				return false;
		} else if (!shippingMethod.equals(other.shippingMethod))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
