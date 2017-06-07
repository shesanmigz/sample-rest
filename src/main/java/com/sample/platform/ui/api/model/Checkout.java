package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Checkout {
	@JsonProperty("id")
	private String id;

	@JsonProperty("billSummary")
	private BillItem billSummary;

	@JsonProperty("orders")
	private List<OrderCheckout> orders;

	@JsonProperty("shippingMethod")
	private ShippingMethodCheckout shippingMethod;

	@JsonProperty("billingAddress")
	private AddressCheckout billingAddress;

	@JsonProperty("shippingAddress")
	private AddressCheckout shippingAddress;

	@JsonProperty("handling")
	private HandlingCheckout handling;

	@JsonProperty("paymentMethod")
	private PaymentMethodCheckout paymentMethod;

	@JsonProperty("previewResult")
	private Result previewResult;

	@JsonProperty("buyResult")
	private Result buyResult;

	@JsonProperty("completeResult")
	private Result completeResult;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BillItem getBillSummary() {
		return billSummary;
	}

	public void setBillSummary(BillItem billSummary) {
		this.billSummary = billSummary;
	}

	public List<OrderCheckout> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderCheckout> orders) {
		this.orders = orders;
	}

	public ShippingMethodCheckout getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethodCheckout shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public AddressCheckout getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressCheckout billingAddress) {
		this.billingAddress = billingAddress;
	}

	public AddressCheckout getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressCheckout shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public HandlingCheckout getHandling() {
		return handling;
	}

	public void setHandling(HandlingCheckout handling) {
		this.handling = handling;
	}

	public PaymentMethodCheckout getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodCheckout paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Result getPreviewResult() {
		return previewResult;
	}

	public void setPreviewResult(Result previewResult) {
		this.previewResult = previewResult;
	}

	public Result getBuyResult() {
		return buyResult;
	}

	public void setBuyResult(Result buyResult) {
		this.buyResult = buyResult;
	}

	public Result getCompleteResult() {
		return completeResult;
	}

	public void setCompleteResult(Result completeResult) {
		this.completeResult = completeResult;
	}

	public Checkout(String id, BillItem billSummary, List<OrderCheckout> orders, ShippingMethodCheckout shippingMethod,
			AddressCheckout billingAddress, AddressCheckout shippingAddress, HandlingCheckout handling,
			PaymentMethodCheckout paymentMethod, Result previewResult, Result buyResult, Result completeResult) {
		this.id = id;
		this.billSummary = billSummary;
		this.orders = orders;
		this.shippingMethod = shippingMethod;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
		this.handling = handling;
		this.paymentMethod = paymentMethod;
		this.previewResult = previewResult;
		this.buyResult = buyResult;
		this.completeResult = completeResult;
	}

	public Checkout() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billSummary == null) ? 0 : billSummary.hashCode());
		result = prime * result + ((billingAddress == null) ? 0 : billingAddress.hashCode());
		result = prime * result + ((buyResult == null) ? 0 : buyResult.hashCode());
		result = prime * result + ((completeResult == null) ? 0 : completeResult.hashCode());
		result = prime * result + ((handling == null) ? 0 : handling.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime * result + ((previewResult == null) ? 0 : previewResult.hashCode());
		result = prime * result + ((shippingAddress == null) ? 0 : shippingAddress.hashCode());
		result = prime * result + ((shippingMethod == null) ? 0 : shippingMethod.hashCode());
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
		Checkout other = (Checkout) obj;
		if (billSummary == null) {
			if (other.billSummary != null)
				return false;
		} else if (!billSummary.equals(other.billSummary))
			return false;
		if (billingAddress == null) {
			if (other.billingAddress != null)
				return false;
		} else if (!billingAddress.equals(other.billingAddress))
			return false;
		if (buyResult == null) {
			if (other.buyResult != null)
				return false;
		} else if (!buyResult.equals(other.buyResult))
			return false;
		if (completeResult == null) {
			if (other.completeResult != null)
				return false;
		} else if (!completeResult.equals(other.completeResult))
			return false;
		if (handling == null) {
			if (other.handling != null)
				return false;
		} else if (!handling.equals(other.handling))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (paymentMethod == null) {
			if (other.paymentMethod != null)
				return false;
		} else if (!paymentMethod.equals(other.paymentMethod))
			return false;
		if (previewResult == null) {
			if (other.previewResult != null)
				return false;
		} else if (!previewResult.equals(other.previewResult))
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
		return true;
	}
}
