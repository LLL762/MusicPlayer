package com.example.musicplayer.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.musicplayer.component.SpeedComboBuilder;
import com.example.musicplayer.model.PlayListModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.media.MediaPlayer;

/**
 * 21/06/2022.
 *
 * @author Laurent Lamiral
 */

public class SpeedController implements PropertyChangeListener, Initializable {

	private PlayListModel playListModel;

	@FXML
	private ComboBox<String> speedComboBox;

	public SpeedController(PlayListModel playListModel) {
		this.playListModel = playListModel;

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		init();
	}

	public void init() {

		SpeedComboBuilder.build(speedComboBox);

		playListModel.addPropertyChangeListener(this);

	}

	@FXML
	void changeSpeed() {

		playListModel.getMediaPlayer().setRate(comboValueToDouble());

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		MediaPlayer newMediaPlayer;

		if (evt.getPropertyName().equals("media-change")) {

			newMediaPlayer = (MediaPlayer) evt.getNewValue();

			newMediaPlayer.setRate(comboValueToDouble());
		}

	}

	private double comboValueToDouble() {

		return Double.parseDouble(speedComboBox.getValue().substring(1));

	}

}
