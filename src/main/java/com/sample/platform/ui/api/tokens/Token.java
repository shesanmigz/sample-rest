package com.sample.platform.ui.api.tokens;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.impl.PublicClaims;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * This is a token holder class. The most generic way to store tokens is a JSON
 * map. We are forced to use JSON maps instead of JWT objects since this is
 * required by
 * org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter.decode(String)
 *
 * We need that method to use the default implementation of the token decoding
 * when we validate client tokens to allow or reject them accessing our
 * resources. But we also need the original string token that's why this class
 * wraps the token JSON map, the token string representation and provides some
 * methods to get token details.
 */
public class Token {
	public final ImmutableMap<String, Object> json;
	public final String token;
	
	public Token(final String token, final Map<String, Object> json) {
		if (json.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (Strings.nullToEmpty(token).trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.json = ImmutableMap.copyOf(json);
		this.token = token;
	}
	
	public Long getExpiresAt() {
		return this.getDate(PublicClaims.EXPIRES_AT);
	}
	
	public Long getNotBefore() {
		return this.getDate(PublicClaims.NOT_BEFORE);
	}
	
	public Long getIssuedAt() {
		return this.getDate(PublicClaims.ISSUED_AT);
	}
	
	public List<String> getAudience() {
		return this.getStrings(PublicClaims.AUDIENCE);
	}
	
	private List<String> getStrings(final String fieldName) {
		List<String> result = new ArrayList<>();
		
		Object object = this.json.get(fieldName);
		if (object instanceof String) {
			result.add((String) object);
		} else if (object instanceof String[]) {
			result = Arrays.asList((String[]) object);
		} else if (object instanceof Iterable) {
			result = Lists.newArrayList((Iterable<String>) object);
		}
		
		return result;
	}
	
	private Long getDate(final String fieldName) {
		Long result = null;
		
		Object object = this.json.get(fieldName);
		if (object != null) {
			if (object instanceof Date) {
				Date date = (Date) object;
				result = (long) Math.floor(date.getTime() / 1000.0);
			} else if (object instanceof Number) {
				if (object instanceof Double) {
					result = BigDecimal.valueOf((Double) object).longValueExact();
				} else if (object instanceof Float) {
					result = BigDecimal.valueOf(((Float) object).doubleValue()).longValueExact();
				} else {
					result = ((Number) object).longValue();
				}
			} else {
				throw new IllegalStateException("Unexpected value `" + object + "' of type " + object.getClass());
			}
		}
		
		return result;
	}
}
