package com.example.musicplayer.repo;

import java.io.File;
import java.util.Optional;

import com.example.musicplayer.config.AppConfig;
import com.example.musicplayer.entity.UserSettings;
import com.example.musicplayer.entitymanager.UserSettingsManager;
import com.example.musicplayer.exception.ResourceNotFoundException;
import com.example.musicplayer.utility.ResourceUtility;
import com.example.musicplayer.utility.reset.DefaultUserSettings;

public class UserSettingsRepoImpl implements UserSettingsRepo {

	private String path;

	private final UserSettingsManager manager;

	private final EntityMapper mapper;

	private static final String PROPERTY_NAME = "settings.path";

	public UserSettingsRepoImpl(final UserSettingsManager manager, final EntityMapper mapper) {

		this.manager = manager;
		this.mapper = mapper;

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

		init();

	}

	@Override
	public Optional<UserSettings> get() {

		UserSettings outputValue;

		if (manager.getCurrentSettings() != null) {

			return Optional.of(manager.getCurrentSettings());

		}

		outputValue = mapper.read(path, UserSettings.class);

		if (outputValue == null) {

			return Optional.empty();
		}

		manager.setCurrentSettings(outputValue);

		return Optional.of(outputValue);

	}

	@Override
	public UserSettings save(UserSettings userPreferences) {

		final UserSettings output;

		if (manager.getCurrentSettings() != null && manager.getCurrentSettings().equals(userPreferences)) {

			return userPreferences;

		}

		output = mapper.write(path, userPreferences);
		manager.setCurrentSettings(output);

		return output;

	}

	@Override
	public UserSettings restore() {

		new File(path);

		return save(DefaultUserSettings.build());

	}

	@Override
	public UserSettings restorePrevious() {
		// TODO Auto-generated method stub
		return null;
	}
}
