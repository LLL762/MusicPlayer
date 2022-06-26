package com.example.musicplayer.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayList {

	private String name;
	private LinkedList<AudioFile> audioFileList = new LinkedList<>();

	public PlayList(String name) {
		this.name = name;
	}

	public void addAudioFile(final AudioFile audioFile) {

		if (!audioFileList.contains(audioFile)) {
			audioFileList.add(audioFile);
		}

	}

	public void addAudioFile(final AudioFile audioFile, int index) {

		if (!audioFileList.contains(audioFile)) {
			audioFileList.add(index, audioFile);
		}

	}

	public void removeAudioFile(final AudioFile audioFile) {

		audioFileList.remove(audioFile);

	}

	public List<AudioFile> getAudioFileList() {
		return new ArrayList<>(audioFileList);
	}
}
