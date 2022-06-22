package com.example.musicplayer.component;

import javafx.scene.chart.BarChart;

/**
 * 21/06/2022.
 *
 * @author Laurent Lamiral
 */
public final class AudioBarChartBuilder {

    private AudioBarChartBuilder() {
    }

    public static void build(final BarChart<String, Number> audioBarChart) {

        audioBarChart.getYAxis().setAutoRanging(false);


    }


}
