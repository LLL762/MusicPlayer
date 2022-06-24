package com.example.musicplayer.component;

import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileExplorerBuilder {

	private final FileChooser fileChooser;

	private void init() {

		fileChooser.setInitialDirectory(null);

	}

}
