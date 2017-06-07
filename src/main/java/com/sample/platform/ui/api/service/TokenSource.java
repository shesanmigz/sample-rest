package com.sample.platform.ui.api.service;

import java.util.List;

/**
 * Obtains security tokens.
 */
public interface TokenSource {
	/**
	 * Returns a list of security tokens.
	 */
	List<String> getTokens() throws Exception;
}
