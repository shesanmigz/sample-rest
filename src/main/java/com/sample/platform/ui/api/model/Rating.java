package com.sample.platform.ui.api.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
	@JsonProperty("bucketCount")
	private BigDecimal bucketCount;

	@JsonProperty("rating")
	private BigDecimal rating;

	@JsonProperty("reviewCounts")
	private List<Bucket> reviewCounts;

	public BigDecimal getBucketCount() {
		return bucketCount;
	}

	public void setBucketCount(BigDecimal bucketCount) {
		this.bucketCount = bucketCount;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public List<Bucket> getReviewCounts() {
		return reviewCounts;
	}

	public void setReviewCounts(List<Bucket> reviewCounts) {
		this.reviewCounts = reviewCounts;
	}

	public Rating(BigDecimal bucketCount, BigDecimal rating, List<Bucket> reviewCounts) {
		this.bucketCount = bucketCount;
		this.rating = rating;
		this.reviewCounts = reviewCounts;
	}

	public Rating() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bucketCount == null) ? 0 : bucketCount.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((reviewCounts == null) ? 0 : reviewCounts.hashCode());
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
		Rating other = (Rating) obj;
		if (bucketCount == null) {
			if (other.bucketCount != null)
				return false;
		} else if (!bucketCount.equals(other.bucketCount))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (reviewCounts == null) {
			if (other.reviewCounts != null)
				return false;
		} else if (!reviewCounts.equals(other.reviewCounts))
			return false;
		return true;
	}
}