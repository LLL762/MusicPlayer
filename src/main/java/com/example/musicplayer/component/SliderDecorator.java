package com.example.musicplayer.component;

import javafx.scene.control.Slider;

public final class SliderDecorator {

    private SliderDecorator() {

    }

    public static void setUp(Slider slider, final double minValue, final double maxValue, final double value) {

        slider.setMin(minValue);
        slider.setMax(maxValue);
        slider.setValue(value);


        slider.setBlockIncrement(0.25);

    }


}
