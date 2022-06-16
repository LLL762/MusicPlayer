package com.example.musicplayer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayList {

	private List<File> playList = new LinkedList<>();

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
