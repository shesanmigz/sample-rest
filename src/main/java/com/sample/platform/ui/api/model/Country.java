package com.sample.platform.ui.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
	@JsonProperty("name")
	private String name;

	@JsonProperty("iso2")
	private String iso2;

	@JsonProperty("iso3")
	private String iso3;

	@JsonProperty("numericCode")
	private Integer numericCode;

	@JsonProperty("default")
	private Boolean _default;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public Integer getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(Integer numericCode) {
		this.numericCode = numericCode;
	}

	public Boolean get_default() {
		return _default;
	}

	public void set_default(Boolean _default) {
		this._default = _default;
	}

	public Country(String name, String iso2, String iso3, Integer numericCode, Boolean _default) {
		this.name = name;
		this.iso2 = iso2;
		this.iso3 = iso3;
		this.numericCode = numericCode;
		this._default = _default;
	}

	public Country() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_default == null) ? 0 : _default.hashCode());
		result = prime * result + ((iso2 == null) ? 0 : iso2.hashCode());
		result = prime * result + ((iso3 == null) ? 0 : iso3.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((numericCode == null) ? 0 : numericCode.hashCode());
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
		Country other = (Country) obj;
		if (_default == null) {
			if (other._default != null)
				return false;
		} else if (!_default.equals(other._default))
			return false;
		if (iso2 == null) {
			if (other.iso2 != null)
				return false;
		} else if (!iso2.equals(other.iso2))
			return false;
		if (iso3 == null) {
			if (other.iso3 != null)
				return false;
		} else if (!iso3.equals(other.iso3))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numericCode == null) {
			if (other.numericCode != null)
				return false;
		} else if (!numericCode.equals(other.numericCode))
			return false;
		return true;
	}
}
