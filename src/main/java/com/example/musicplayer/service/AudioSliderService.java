package com.example.musicplayer.service;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public interface AudioSliderService {

	void displayTime(final Slider audioTimeSlider, final MediaPlayer mediaPlayer, final Label durationLabel);

	void showProgress(final Slider audioTimeSlider);

	void hideThumb(final Slider audioTimeSlider);

	void showThumb(final Slider audioTimeSlider);

}
