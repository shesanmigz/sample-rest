package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Media {
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("sizeX")
	private Integer sizeX;
	
	@JsonProperty("sizeY")
	private Integer sizeY;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getSizeX() {
		return sizeX;
	}
	
	public void setSizeX(Integer sizeX) {
		this.sizeX = sizeX;
	}
	
	public Integer getSizeY() {
		return sizeY;
	}
	
	public void setSizeY(Integer sizeY) {
		this.sizeY = sizeY;
	}
	
	public Media(String type, String url, Integer sizeX, Integer sizeY) {
		this.type = type;
		this.url = url;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public Media() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sizeX == null) ? 0 : sizeX.hashCode());
		result = prime * result + ((sizeY == null) ? 0 : sizeY.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Media other = (Media) obj;
		if (sizeX == null) {
			if (other.sizeX != null)
				return false;
		} else if (!sizeX.equals(other.sizeX))
			return false;
		if (sizeY == null) {
			if (other.sizeY != null)
				return false;
		} else if (!sizeY.equals(other.sizeY))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
