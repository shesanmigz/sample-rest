package com.sample.platform.ui.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class ComponentList {
	private final List<Component> componentList;

	@JsonValue
	public List<Component> getComponentList() {
		return componentList;
	}

	public ComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}
}
