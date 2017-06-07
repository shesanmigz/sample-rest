package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.Gson;

public class TextblockList {
	private final List<Textblock> textblockList;
	
	@JsonValue
	public List<Textblock> getTextblockList() {
		return textblockList;
	}

	public TextblockList(List<Textblock> textblockList) {
		this.textblockList = textblockList;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(textblockList);
	}
}
