package com.example.musicplayer.repo;

import org.junit.jupiter.api.Test;

import com.example.musicplayer.entitymanager.UserSettingsManager;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
class PlayListRepoMockTest {

	private final PlayListRepoMock repoMock = new PlayListRepoMock();

	private final UserSettingsRepoImpl repo = new UserSettingsRepoImpl(new UserSettingsManager(),
			XmlEntityMapper.INSTANCE);

	@Test
	void getByName_should_return_an_not_empty_optional() {

		repo.restore();

	}

}