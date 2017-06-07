package com.sample.platform.ui.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bucket {
	@JsonProperty("bucket")
	private BigDecimal bucket;

	@JsonProperty("count")
	private BigDecimal count;

	public BigDecimal getBucket() {
		return bucket;
	}

	public void setBucket(BigDecimal bucket) {
		this.bucket = bucket;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public Bucket(BigDecimal bucket, BigDecimal count) {
		this.bucket = bucket;
		this.count = count;
	}

	public Bucket() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bucket == null) ? 0 : bucket.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
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
		Bucket other = (Bucket) obj;
		if (bucket == null) {
			if (other.bucket != null)
				return false;
		} else if (!bucket.equals(other.bucket))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		return true;
	}
}