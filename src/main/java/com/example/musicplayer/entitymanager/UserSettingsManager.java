package com.example.musicplayer.entitymanager;

import java.util.ArrayList;
import java.util.List;

import com.example.musicplayer.entity.UserSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSettingsManager {

	private UserSettings currentSettings;

	private List<UserSettings> previousSettings = new ArrayList<>();

}
