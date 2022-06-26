package com.example.musicplayer.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.musicplayer.entity.UserSettings;
import com.example.musicplayer.entitymanager.UserSettingsManager;
import com.example.musicplayer.exception.ReadOperationFailsException;
import com.example.musicplayer.exception.WriteOperationFailsException;

@ExtendWith(MockitoExtension.class)
class UserSettingsRepoImplTest {

	private static EasyRandom generator;

	@Mock
	private UserSettingsManager manager;
	@Mock
	private EntityMapper mapper;
	@InjectMocks
	private UserSettingsRepoImpl repo;

	@BeforeAll
	private static void init() {
		final EasyRandomParameters parameters = new EasyRandomParameters();
		parameters.stringLengthRange(4, 50);

		generator = new EasyRandom(parameters);

	}

	@Test
	void get_when_no_value_in_manager() {

		final UserSettings settings = generator.nextObject(UserSettings.class);

		when(mapper.read(anyString(), eq(UserSettings.class))).thenReturn(settings);

		assertThat(repo.get().orElseThrow()).isEqualTo(settings);

		verify(manager, times(1)).setCurrentSettings(settings);

	}

	@Test
	void get_when_value_in_manager() {

		final UserSettings settings = generator.nextObject(UserSettings.class);

		when(manager.getCurrentSettings()).thenReturn(settings);

		assertThat(repo.get().orElseThrow()).isEqualTo(settings);

		verify(mapper, never()).read(anyString(), any());
		verify(manager, never()).setCurrentSettings(any());

	}

	@Test
	void get_when_no_value_in_manager_and_mapper_throw_exception() {

		when(mapper.read(anyString(), eq(UserSettings.class))).thenThrow(new ReadOperationFailsException());

		assertThatExceptionOfType(ReadOperationFailsException.class).isThrownBy(() -> repo.get());

	}

	@Test
	void get_when_no_value_in_manager_and_mapper_return_null() {

		assertThat(repo.get()).isEmpty();

		verify(manager, never()).setCurrentSettings(any());

	}

	@Test
	void save_normal_scenario() {

		final UserSettings newSettings = generator.nextObject(UserSettings.class);
		UserSettings oldSettings = generator.nextObject(UserSettings.class);

		while (newSettings.equals(oldSettings)) {

			oldSettings = generator.nextObject(UserSettings.class);
		}

		when(mapper.write(anyString(), eq(newSettings))).thenReturn(newSettings);
		when(manager.getCurrentSettings()).thenReturn(oldSettings);

		assertThat(repo.save(newSettings)).isEqualTo(newSettings);

		verify(manager, times(1)).setCurrentSettings(newSettings);

	}

	@Test
	void save_when_new_settings_equals_old_settings() {

		final UserSettings newSettings = generator.nextObject(UserSettings.class);

		when(manager.getCurrentSettings()).thenReturn(newSettings);

		assertThat(repo.save(newSettings)).isEqualTo(newSettings);

		verify(mapper, never()).write(any(), any());
		verify(manager, never()).setCurrentSettings(newSettings);

	}

	@Test
	void save_when_mapper_throw_exception() {

		final UserSettings newSettings = generator.nextObject(UserSettings.class);

		UserSettings oldSettings = generator.nextObject(UserSettings.class);

		while (newSettings.equals(oldSettings)) {

			oldSettings = generator.nextObject(UserSettings.class);
		}

		when(manager.getCurrentSettings()).thenReturn(oldSettings);
		when(mapper.write(anyString(), eq(newSettings))).thenThrow(new WriteOperationFailsException());

		assertThatExceptionOfType(WriteOperationFailsException.class).isThrownBy(() -> repo.save(newSettings));

		verify(manager, never()).setCurrentSettings(newSettings);

	}

}
