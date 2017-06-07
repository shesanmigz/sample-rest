package com.sample.platform.ui.api.model;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchApiProduct {
	@JsonProperty("duin")
	private String duin;

	@JsonProperty("gtin")
	private String gtin;

	@JsonProperty("name")
	private String name;

	@JsonProperty("price")
	private Float price;

	@JsonProperty("releaseDate")
	private DateTime releaseDate;

	@JsonProperty("categories")
	private List<CategorySearch> categories;

	@JsonProperty("contributors")
	private List<Contributor> contributors;

	public String getDuin() {
		return duin;
	}

	public void setDuin(String duin) {
		this.duin = duin;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
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

	public List<Contributor> getContributors() {
		return contributors;
	}

	public void setContributors(List<Contributor> contributors) {
		this.contributors = contributors;
	}

	public SearchApiProduct(String duin, String gtin, String name, Float price, DateTime releaseDate,
			List<CategorySearch> categories, List<Contributor> contributors) {
		this.duin = duin;
		this.gtin = gtin;
		this.name = name;
		this.price = price;
		this.releaseDate = releaseDate;
		this.categories = categories;
		this.contributors = contributors;
	}

	public SearchApiProduct() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((contributors == null) ? 0 : contributors.hashCode());
		result = prime * result + ((duin == null) ? 0 : duin.hashCode());
		result = prime * result + ((gtin == null) ? 0 : gtin.hashCode());
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
		SearchApiProduct other = (SearchApiProduct) obj;
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
		if (gtin == null) {
			if (other.gtin != null)
				return false;
		} else if (!gtin.equals(other.gtin))
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
