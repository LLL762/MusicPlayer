package com.example.musicplayer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.media.MediaPlayer;

public class PlayListModel {

	private List<File> playList = new LinkedList<>();

	private MediaPlayer currentMediaPlayer;

	public void addFile(String absolutePath) {

		final File file = new File(absolutePath);

		if (!file.exists()) {
		}

		playList.add(file);

	}

	public List<File> getPlayList() {
		return new ArrayList<>(playList);
	}

}
