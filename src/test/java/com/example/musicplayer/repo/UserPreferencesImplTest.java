package com.example.musicplayer.repo;

import org.junit.jupiter.api.Test;

import com.example.musicplayer.entity.UserPreferences;

class UserPreferencesImplTest {

	private final UserPreferencesImpl userPreferencesImpl = new UserPreferencesImpl();

	@Test
	void test() {
		userPreferencesImpl.save(new UserPreferences("riri", "loulou"));
	}

}
