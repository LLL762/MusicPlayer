package com.example.musicplayer.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.musicplayer.model.PlayListModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.media.MediaPlayer;

public class SongListViewController implements PropertyChangeListener, Initializable {

	@FXML
	private ListView<String> songListView;

	private PlayListModel playListModel;

	public SongListViewController(PlayListModel playListModel) {

		this.playListModel = playListModel;
		playListModel.addPropertyChangeListener(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		MediaPlayer newValue;

		if (evt.getPropertyName().equals("media-change")) {

			newValue = (MediaPlayer) evt.getNewValue();

		}

	}

}
