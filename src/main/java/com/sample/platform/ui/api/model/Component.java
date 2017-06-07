package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Component {
	@JsonProperty("title")
	private Item title;

	@JsonProperty("component")
	private String component;

	@JsonProperty("value")
	private ComponentItem value;

	public Item getTitle() {
		return title;
	}

	public void setTitle(Item title) {
		this.title = title;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public ComponentItem getValue() {
		return value;
	}

	public void setValue(ComponentItem value) {
		this.value = value;
	}

	public Component(Item title, String component, ComponentItem value) {
		this.title = title;
		this.component = component;
		this.value = value;
	}

	public Component() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Component other = (Component) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
