package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
	@JsonProperty("handle")
	private String handle;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("icon")
	private String icon;
	
	@JsonProperty("hidden")
	private Boolean hidden;

	@JsonProperty("parentHandle")
	private String parentHandle;
	
	public String getHandle() {
		return handle;
	}
	
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Boolean getHidden() {
		return hidden;
	}
	
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	public String getParentHandle() {
		return parentHandle;
	}

	public void setParentHandle(String parentHandle) {
		this.parentHandle = parentHandle;
	}

	public Category(String handle, String name, String icon, Boolean hidden, String parentHandle) {
		this.handle = handle;
		this.name = name;
		this.icon = icon;
		this.hidden = hidden;
		this.parentHandle = parentHandle;
	}

	public Category() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((handle == null) ? 0 : handle.hashCode());
		result = prime * result + ((hidden == null) ? 0 : hidden.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentHandle == null) ? 0 : parentHandle.hashCode());
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
		Category other = (Category) obj;
		if (handle == null) {
			if (other.handle != null)
				return false;
		} else if (!handle.equals(other.handle))
			return false;
		if (hidden == null) {
			if (other.hidden != null)
				return false;
		} else if (!hidden.equals(other.hidden))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentHandle == null) {
			if (other.parentHandle != null)
				return false;
		} else if (!parentHandle.equals(other.parentHandle))
			return false;
		return true;
	}
}
