package com.netmap.github;

import java.io.IOException;

import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Kohsuke {

	public static void main(String[] args) {

		try {
			GitHub github = GitHub.connectUsingPassword("zhiqinghuang", "");
			//GHRepository repo = github.createRepository("new-repository", "this is my new repository", "http://www.kohsuke.org/", true/* public */);
			GHCreateRepositoryBuilder createRepositoryBuilder = github.createRepository("new-repository");
			GHRepository repository = createRepositoryBuilder.create();
			//repository.addCollaborators(github.getUser("abayer"), github.getUser("rtyler"));
			repository.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
