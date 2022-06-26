package com.example.musicplayer.repo;

import java.util.Optional;

import com.example.musicplayer.entity.UserSettings;

public interface UserSettingsRepo extends Repo<UserSettings> {

	Optional<UserSettings> get();

}
