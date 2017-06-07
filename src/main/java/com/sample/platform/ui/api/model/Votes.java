package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Votes {
	@JsonProperty("up")
	private BigDecimal up;

	@JsonProperty("down")
	private BigDecimal down;

	public BigDecimal getUp() {
		return up;
	}

	public void setUp(BigDecimal up) {
		this.up = up;
	}

	public BigDecimal getDown() {
		return down;
	}

	public void setDown(BigDecimal down) {
		this.down = down;
	}

	public Votes(BigDecimal up, BigDecimal down) {
		this.up = up;
		this.down = down;
	}

	public Votes() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((down == null) ? 0 : down.hashCode());
		result = prime * result + ((up == null) ? 0 : up.hashCode());
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
		Votes other = (Votes) obj;
		if (down == null) {
			if (other.down != null)
				return false;
		} else if (!down.equals(other.down))
			return false;
		if (up == null) {
			if (other.up != null)
				return false;
		} else if (!up.equals(other.up))
			return false;
		return true;
	}
}
