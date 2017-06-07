package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class PromotionList {
	private final List<Promotion> promotionList;
	
	@JsonValue
	public List<Promotion> getPromotionList() {
		return promotionList;
	}

	public PromotionList(List<Promotion> promotionList) {
		this.promotionList = promotionList;
	}
}
