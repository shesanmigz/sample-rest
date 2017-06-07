package com.sample.platform.ui.api.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.sample.platform.ui.api.service.GitService;

@Service
public class GitServiceImpl implements GitService {
	private static final String DEVELOPMENT_COMMIT_ID = "<development>";
	private static final String COMMIT_ID_PLACEHOLDER = "~git.commit.id~";

	@Value("${git.commitId:}")
	private String gitCommitId;
	
	String getGitCommitId() {
		return this.gitCommitId;
	}

	@Override
	public String getCommitId() {
		String result = DEVELOPMENT_COMMIT_ID; 
		String commitId = Strings.nullToEmpty(this.getGitCommitId()).trim();
		if (!(commitId.isEmpty() || commitId.contains(COMMIT_ID_PLACEHOLDER))) {
			result = commitId;
		}
		return result;
	}
}
