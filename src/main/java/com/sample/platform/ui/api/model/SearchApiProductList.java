package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class SearchApiProductList {
	private final List<SearchApiProduct> searchApiProductList;

	@JsonValue
	public List<SearchApiProduct> getSearchApiProductList() {
		return searchApiProductList;
	}

	public SearchApiProductList(List<SearchApiProduct> searchApiProductList) {
		this.searchApiProductList = searchApiProductList;
	}
}
