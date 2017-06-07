package com.sample.platform.ui.api.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.Application;
import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.controller.ShopApiControllerTests;
import com.sample.platform.ui.api.filters.CommitFilter;
import com.sample.platform.ui.api.service.GitService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@ContextConfiguration
@ActiveProfiles("test")
public class CommitFilterTest extends ShopApiControllerTests {
	@Autowired
	private CommitFilter commitFilter;

	private GitService gs;

	@Before
	@Override
	public void setUp() throws Exception {
		CommitFilter cf = Mockito.spy(this.commitFilter);
		this.gs = Mockito.mock(GitService.class);
		Mockito.doReturn(this.gs).when(cf).getGitService();

		this.mockMvc = this.createMockMVC(this.wac, cf);

		this.startMockServer();
	}

	@Test
	public void testCommitHeader() throws Exception {
		Mockito.doReturn("a").when(this.gs).getCommitId();

		this.stub()
				.withGET("/v1/countries")
				.withPOJO(ImmutableList.of())
				.build();

		this.test()
				.withGET("/api/v1/localization/countries")
				.expectHeader(ShopApiConstants.COMMIT_HEADER, "a")
				.run();
	}
}
