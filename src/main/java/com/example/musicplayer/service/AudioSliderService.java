package com.example.musicplayer.service;

import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public interface AudioSliderService {

	void displayTime(final Slider audioTimeSlider, final MediaPlayer mediaPlayer);

	void showProgress(final Slider audioTimeSlider);

	void hideThumb(final Slider audioTimeSlider);

	void showThumb(final Slider audioTimeSlider);

}
