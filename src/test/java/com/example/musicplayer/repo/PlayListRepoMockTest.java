package com.example.musicplayer.repo;

import org.junit.jupiter.api.Test;

import com.example.musicplayer.HelloApplication;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
class PlayListRepoMockTest {

	private final PlayListRepoMock repoMock = new PlayListRepoMock();

	@Test
	void getByName_should_return_an_not_empty_optional() {

		System.out.println(HelloApplication.class.getResource("/application.properties").getPath());

	}

}