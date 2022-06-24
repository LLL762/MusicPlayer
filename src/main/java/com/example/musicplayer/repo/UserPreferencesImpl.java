package com.example.musicplayer.repo;

import java.util.Optional;

import com.example.musicplayer.config.AppConfig;
import com.example.musicplayer.entity.UserPreferences;
import com.example.musicplayer.exception.ResourceNotFoundException;
import com.example.musicplayer.utility.ResourceUtility;

public class UserPreferencesImpl implements UserPreferencesRepo {

	private String path;

	private static final String PROPERTY_NAME = "settings.preferences-path";

	public UserPreferencesImpl() {

		init();

	}

	public void init() {

		final String relativePath = AppConfig.INSTANCE.getProperty(PROPERTY_NAME);

		try {
			path = ResourceUtility.getResourcePath(relativePath);
		} catch (NullPointerException e) {

			throw new ResourceNotFoundException("Missing file : " + relativePath);
		}

	}

	public void refresh() {
		AppConfig.INSTANCE.refresh();
		init();

	}

	@Override
	public Optional<UserPreferences> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPreferences save(UserPreferences userPreferences) {

		return XmlEntityMapper.INSTANCE.write(path, userPreferences);

	}
}
