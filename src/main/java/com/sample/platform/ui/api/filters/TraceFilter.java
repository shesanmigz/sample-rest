package com.sample.platform.ui.api.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.sample.platform.ui.api.ShopApiConstants;

/**
 * Checks if a request contains a trace id in the X-Sample-Trace header. If not,
 * generates one. In the both cases the trace id value is then set to the trace
 * id attribute of the request scope. The attribute is then may be used to send
 * the trace id to external services.<br/>
 * When the request handling finishes, adds the trace id to the X-Sample-Trace
 * response header.
 */
@Order(-1)
@Component
public class TraceFilter implements Filter {
	public static final String MDC_KEY = "trace";

	private static final Logger LOGGER = LoggerFactory.getLogger(TraceFilter.class);
	
	private final String traceId;
	
	public TraceFilter() {
		this.traceId = null;
	}
	
	/**
	 * This is for testing, to make tests more deterministic.
	 */
	public TraceFilter(final String traceId) {
		this.traceId = traceId;
	}
	
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		
		String traceId = this.getTraceId((HttpServletRequest) request);
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader(ShopApiConstants.TRACE_HEADER, traceId);

		try {
			chain.doFilter(request, response);
		} finally {
			MDC.remove(TraceFilter.MDC_KEY);
		}
	}

	private String getTraceId(final HttpServletRequest req) {
		String traceId = (String) req.getAttribute(ShopApiConstants.TRACE_HEADER);
		String message = "Reusing X-Sample-Trace {} for request to {}";

		if (this.noTraceId(traceId)) {
			traceId = req.getHeader(ShopApiConstants.TRACE_HEADER);
			if (this.noTraceId(traceId)) {
				traceId = this.traceId == null ? UUID.randomUUID().toString() : this.traceId;
				message = "Generated X-Sample-Trace {} for request to {}";
			} else {
				message = "Using provided X-Sample-Trace {} for request to {}";
			}
			req.setAttribute(ShopApiConstants.TRACE_HEADER, traceId);
		}

		MDC.put(TraceFilter.MDC_KEY, traceId);
		LOGGER.trace(message, traceId, req.getRequestURI());

		return traceId;
	}

	private boolean noTraceId(final String traceId) {
		return Strings.nullToEmpty(traceId).trim().isEmpty();
	}
}
