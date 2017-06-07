package com.sample.platform.ui.api.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.service.GitService;

/**
 * Adds a commit id to a response header.
 */
@Component
@Order(10)
public class CommitFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommitFilter.class);
	
	@Autowired
	private GitService gitService;

	GitService getGitService() {
		return this.gitService;
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
		String commitId = this.getGitService().getCommitId();
		
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader(ShopApiConstants.COMMIT_HEADER, commitId);
		LOGGER.trace("Using commit id {}", commitId);

		chain.doFilter(request, response);
	}
}
