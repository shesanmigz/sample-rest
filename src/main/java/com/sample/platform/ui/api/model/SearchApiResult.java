package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchApiResult {
	@JsonProperty("filtergroups")
	private List<FilterGroup> filtergroups;

	@JsonProperty("products")
	private List<SearchApiProduct> products;

	@JsonProperty("sortings")
	private List<Sorting> sortings;

	@JsonProperty("categories")
	private List<CategorySearch> categories;

	public List<FilterGroup> getFiltergroups() {
		return filtergroups;
	}

	public void setFiltergroups(List<FilterGroup> filtergroups) {
		this.filtergroups = filtergroups;
	}

	public List<SearchApiProduct> getProducts() {
		return products;
	}

	public void setProducts(List<SearchApiProduct> products) {
		this.products = products;
	}

	public List<Sorting> getSortings() {
		return sortings;
	}

	public void setSortings(List<Sorting> sortings) {
		this.sortings = sortings;
	}

	public List<CategorySearch> getCategories() {
		return categories;
	}

	public void setCategories(List<CategorySearch> categories) {
		this.categories = categories;
	}

	public SearchApiResult(List<FilterGroup> filtergroups, List<SearchApiProduct> products, List<Sorting> sortings,
			List<CategorySearch> categories) {
		this.filtergroups = filtergroups;
		this.products = products;
		this.sortings = sortings;
		this.categories = categories;
	}

	public SearchApiResult() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((filtergroups == null) ? 0 : filtergroups.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((sortings == null) ? 0 : sortings.hashCode());
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
		SearchApiResult other = (SearchApiResult) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (filtergroups == null) {
			if (other.filtergroups != null)
				return false;
		} else if (!filtergroups.equals(other.filtergroups))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (sortings == null) {
			if (other.sortings != null)
				return false;
		} else if (!sortings.equals(other.sortings))
			return false;
		return true;
	}
}
