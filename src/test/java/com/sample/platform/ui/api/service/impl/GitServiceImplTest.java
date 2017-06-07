package com.sample.platform.ui.api.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sample.platform.ui.api.service.impl.GitServiceImpl;

public class GitServiceImplTest {
	private GitServiceImpl gs;
	
	@Before
	public void setUp() {
		this.gs = Mockito.spy(new GitServiceImpl());
	}
	
	@Test
	public void testGetCommitId() {
		Mockito.doReturn(null).when(this.gs).getGitCommitId();
		Assert.assertEquals("<development>", this.gs.getCommitId());

		Mockito.doReturn("").when(this.gs).getGitCommitId();
		Assert.assertEquals("<development>", this.gs.getCommitId());
		
		Mockito.doReturn(" ").when(this.gs).getGitCommitId();
		Assert.assertEquals("<development>", this.gs.getCommitId());

		Mockito.doReturn("~git.commit.id~").when(this.gs).getGitCommitId();
		Assert.assertEquals("<development>", this.gs.getCommitId());
		
		Mockito.doReturn("a").when(this.gs).getGitCommitId();
		Assert.assertEquals("a", this.gs.getCommitId());
	}
}
