package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class RecommendedProductList {
	private final List<RecommendedProduct> recommendedProductList;

	@JsonValue
	public List<RecommendedProduct> getRecommendedProductList() {
		return recommendedProductList;
	}

	public RecommendedProductList(List<RecommendedProduct> recommendedProductList) {
		this.recommendedProductList = recommendedProductList;
	}
}
