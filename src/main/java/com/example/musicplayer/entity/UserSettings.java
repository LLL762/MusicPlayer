package com.example.musicplayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettings {

	private String defaultPlaylistPath;
	private String defaultAudioFilePath;

}
