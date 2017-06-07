package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bill {
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("subtotal")
	private BigDecimal subtotal;

	@JsonProperty("shipping")
	private BigDecimal shipping;

	@JsonProperty("net")
	private BigDecimal net;

	@JsonProperty("vat")
	private BigDecimal vat;

	@JsonProperty("total")
	private BigDecimal total;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	public BigDecimal getNet() {
		return net;
	}

	public void setNet(BigDecimal net) {
		this.net = net;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Bill(String title, BigDecimal subtotal, BigDecimal shipping, BigDecimal net, BigDecimal vat,
			BigDecimal total) {
		this.title = title;
		this.subtotal = subtotal;
		this.shipping = shipping;
		this.net = net;
		this.vat = vat;
		this.total = total;
	}

	public Bill() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((net == null) ? 0 : net.hashCode());
		result = prime * result + ((shipping == null) ? 0 : shipping.hashCode());
		result = prime * result + ((subtotal == null) ? 0 : subtotal.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
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
		Bill other = (Bill) obj;
		if (net == null) {
			if (other.net != null)
				return false;
		} else if (!net.equals(other.net))
			return false;
		if (shipping == null) {
			if (other.shipping != null)
				return false;
		} else if (!shipping.equals(other.shipping))
			return false;
		if (subtotal == null) {
			if (other.subtotal != null)
				return false;
		} else if (!subtotal.equals(other.subtotal))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (vat == null) {
			if (other.vat != null)
				return false;
		} else if (!vat.equals(other.vat))
			return false;
		return true;
	}
}
