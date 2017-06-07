package com.sample.platform.ui.api.model;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecommendedProduct {
	@JsonProperty("duin")
	private String duin;

	@JsonProperty("name")
	private String name;

	@JsonProperty("price")
	private Float price;

	@JsonProperty("releaseDate")
	private DateTime releaseDate;

	@JsonProperty("categories")
	private List<CategorySearch> categories;

	@JsonProperty("contributors")
	private Object contributors;

	public String getDuin() {
		return duin;
	}

	public void setDuin(String duin) {
		this.duin = duin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public DateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(DateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<CategorySearch> getCategories() {
		return categories;
	}

	public void setCategories(List<CategorySearch> categories) {
		this.categories = categories;
	}

	public Object getContributors() {
		return contributors;
	}

	public void setContributors(Object contributors) {
		this.contributors = contributors;
	}

	public RecommendedProduct(String duin, String name, Float price, DateTime releaseDate,
			List<CategorySearch> categories, Object contributors) {
		this.duin = duin;
		this.name = name;
		this.price = price;
		this.releaseDate = releaseDate;
		this.categories = categories;
		this.contributors = contributors;
	}

	public RecommendedProduct() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((contributors == null) ? 0 : contributors.hashCode());
		result = prime * result + ((duin == null) ? 0 : duin.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
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
		RecommendedProduct other = (RecommendedProduct) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (contributors == null) {
			if (other.contributors != null)
				return false;
		} else if (!contributors.equals(other.contributors))
			return false;
		if (duin == null) {
			if (other.duin != null)
				return false;
		} else if (!duin.equals(other.duin))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		return true;
	}
}
