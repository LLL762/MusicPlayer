package com.example.musicplayer.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.musicplayer.exception.ConfigLoadException;
import com.example.musicplayer.model.PlayListModel;
import com.example.musicplayer.utility.RandomInstance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
public class MediaButtonBarController implements PropertyChangeListener, Initializable {

	private final PlayListModel playListModel;
	@FXML
	private Button playPauseButton;
	private MediaPlayer mediaPlayer;

	private MediaPlayer.Status mediaStatus;

	private int playlistSize;

	public MediaButtonBarController(PlayListModel playListModel) {
		this.playListModel = playListModel;
		setUp();

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	private void setUp() {

		mediaPlayer = playListModel.getMediaPlayer();
		playListModel.addPropertyChangeListener(this);
		mediaStatus = mediaPlayer.getStatus();
		playlistSize = playListModel.getPlayList().getAudioFileList().size();

	}

	@FXML
	private void playPause() {

		mediaStatus = mediaPlayer.getStatus();

		if (mediaStatus == MediaPlayer.Status.PLAYING) {

			mediaPlayer.pause();

		}
		if (mediaStatus == MediaPlayer.Status.PAUSED || mediaStatus == MediaPlayer.Status.READY) {

			mediaPlayer.play();

		}

	}

	@FXML
	private void playRandom() {

		final int index = RandomInstance.getRangomInt(0, playlistSize);
		playListModel.changeMedia(index);
		throw new ConfigLoadException("Config loading fail!");

	}

	@FXML
	private void nextAudioFile() {

		playListModel.changeMedia(playListModel.getCurrentAudioFileIndex() + 1);

	}

	@FXML
	private void previousAudioFile() {

		int index = playListModel.getCurrentAudioFileIndex() - 1;

		if (index < 0) {
			index = playlistSize - 1;

		}

		playListModel.changeMedia(index);

	}

	@FXML
	private void reset() {

		mediaPlayer.seek(Duration.ZERO);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("media-status-change")) {

			mediaStatus = (MediaPlayer.Status) evt.getNewValue();

			if (mediaStatus == MediaPlayer.Status.PLAYING) {

				playPauseButton.getStyleClass().removeAll("play-button");
				playPauseButton.getStyleClass().add("pause-button");

			} else {

				playPauseButton.getStyleClass().removeAll("pause-button");
				playPauseButton.getStyleClass().add("play-button");
			}

		}

		if (evt.getPropertyName().equals("media-change")) {

			mediaPlayer = (MediaPlayer) evt.getNewValue();

			if (mediaStatus == MediaPlayer.Status.PLAYING) {
				mediaPlayer.setAutoPlay(true);

			}

		}

	}

}
