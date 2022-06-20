package com.example.musicplayer.component;

import javafx.scene.control.Slider;
import javafx.util.Duration;

public class AudioSliderBuilder {

	private AudioSliderBuilder() {

	}

	public static void build(Slider audioTimeSlider, final double minValue, final double maxValue, final double value) {

		audioTimeSlider.setMin(minValue);
		audioTimeSlider.setMax(maxValue);
		audioTimeSlider.setValue(value);

		audioTimeSlider.setBlockIncrement(0.25);

		audioTimeSlider.getTooltip().setShowDelay(Duration.valueOf("100ms"));

	}

}
