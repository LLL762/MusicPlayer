package com.example.musicplayer.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.musicplayer.entity.AudioFile;
import com.example.musicplayer.model.PlayListModel;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

public class SongListViewController implements PropertyChangeListener, Initializable {

	@FXML
	private ListView<String> songListView;

	private PlayListModel playListModel;

	private final ChangeListener<Number> indexChangeListener = (obs, oldValue,
			newValue) -> changeMedia(newValue.intValue());

	public SongListViewController(PlayListModel playListModel) {

		this.playListModel = playListModel;
		playListModel.addPropertyChangeListener(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();

	}

	public void init() {

		final MultipleSelectionModel<String> selectionModel;

		songListView.getItems().addAll(
				playListModel	.getPlayList().getAudioFileList().stream()
								.map(AudioFile::getName)
								.toList());

		songListView.getSelectionModel().selectFirst();

		selectionModel = songListView.getSelectionModel();

		selectionModel	.selectedIndexProperty()
						.addListener(indexChangeListener);

	}

	public void changeMedia(int index) {

		playListModel.changeMedia(index);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		final MultipleSelectionModel<String> selectionModel = songListView.getSelectionModel();

		selectionModel	.selectedIndexProperty()
						.removeListener(indexChangeListener);

		if (evt.getPropertyName().equals("media-change")) {

			songListView.getSelectionModel().select(playListModel.getCurrentAudioFileIndex());

		}

		selectionModel	.selectedIndexProperty()
						.addListener(indexChangeListener);

	}

}
