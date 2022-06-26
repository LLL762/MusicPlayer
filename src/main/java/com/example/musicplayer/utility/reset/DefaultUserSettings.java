package com.example.musicplayer.utility.reset;

import com.example.musicplayer.entity.UserSettings;
import com.example.musicplayer.utility.ResourceUtility;

public final class DefaultUserSettings {

	private DefaultUserSettings() {

	}

	public static UserSettings build() {

		return new UserSettings(ResourceUtility.getResourcePath("/playlists"), "toto");

	}

}
