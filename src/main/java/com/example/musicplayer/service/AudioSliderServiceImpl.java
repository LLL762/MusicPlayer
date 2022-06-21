package com.example.musicplayer.service;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class AudioSliderServiceImpl implements AudioSliderService {


	private final DecimalFormat formatter = new DecimalFormat("##0.00");
	private final DecimalFormatSymbols dfSymbols = new DecimalFormatSymbols();

	public AudioSliderServiceImpl() {
		init();
	}

	private void init() {
		dfSymbols.setDecimalSeparator(':');
		formatter.setDecimalFormatSymbols(dfSymbols);

	}

	/**
	 * Update @param audioTimeSlider thumb position based on @param mediaPlayer
	 * duration. Display @param mediaPlayer currentTime and totalDuration in @param
	 * durationLabel.
	 *
	 * @param audioTimeSlider
	 * @param mediaPlayer
	 * @param durationLabel
	 */
	@Override
	public void displayTime(final Slider audioTimeSlider, final MediaPlayer mediaPlayer, final Label durationLabel) {

		final double minutes = mediaPlayer.getCurrentTime().toMinutes();

		audioTimeSlider.setValue(mediaPlayer.getCurrentTime().toMinutes());

		durationLabel.setText(
				formatter.format(minutes) + "/" + formatter.format(mediaPlayer.getTotalDuration().toMinutes()));

	}

	@Override
	public void showProgress(final Slider audioTimeSlider) {

		final String progress = String.valueOf(audioTimeSlider.getValue() * 100 / audioTimeSlider.getMax());

		audioTimeSlider.lookup(".track")
				.setStyle("-fx-background-color: linear-gradient(to right,  red " + progress + "% ,  grey "
						+ progress
						+ "%);");

	}

	@Override
	public void hideThumb(final Slider audioTimeSlider) {
		final StackPane thumb = (StackPane) audioTimeSlider.lookup(".thumb");
		thumb.setOpacity(0);

	}

	@Override
	public void showThumb(final Slider audioTimeSlider) {
		final StackPane thumb = (StackPane) audioTimeSlider.lookup(".thumb");

		thumb.setOpacity(1);

	}

}
