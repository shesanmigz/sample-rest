package com.sample.platform.ui.api.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class TraceIdFilter implements Filter {
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		try {
			chain.doFilter(request, response);
		} catch (final Exception ex) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendError(555, ex.getCause().getMessage());
		}
	}
}