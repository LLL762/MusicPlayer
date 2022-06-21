package com.example.musicplayer.controller;

import javafx.beans.InvalidationListener;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static lombok.AccessLevel.PRIVATE;

/**
 * 21/06/2022.
 * <p>
 * Manage  media player  display of spectrum data and current time.
 *
 * </p>
 *
 * @author Laurent Lamiral
 */


@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class MediaPlayerInfoDisplay {

    final DecimalFormat formatter = new DecimalFormat("##0.00");
    final DecimalFormatSymbols dfSymbols = new DecimalFormatSymbols();

    final XYChart.Series<String, Number> topSeries = new XYChart.Series<>();
    final XYChart.Series<String, Number> bottomSeries = new XYChart.Series<>();
    final AudioSpectrumListener spectrumListener = this::displayAudio;
    MediaPlayer mediaPlayer;
    Slider audioTimeSlider;
    Label durationLabel;
    final InvalidationListener onMpCurrTimeChg = obs -> displayTime();
    BarChart<String, Number> audioBarChart;


    public MediaPlayerInfoDisplay() {

        init();
    }

    private void init() {
        dfSymbols.setDecimalSeparator(':');
        formatter.setDecimalFormatSymbols(dfSymbols);
    }


    public void setUpAll() {

        setUpMediaPlayer();
        setUpAudioBarChart();

    }


    public void setUpMediaPlayer() {

        addMpCurrTimeChg();
        setSpectrumListener();


    }

    public void setUpAudioBarChart() {

        audioBarChart.getYAxis().setAutoRanging(false);

        audioBarChart.getData().add(topSeries);
        audioBarChart.getData().add(bottomSeries);


    }


    public void setSpectrumListener() {

        mediaPlayer.setAudioSpectrumListener(spectrumListener);

    }

    public void removeSpectrumListener() {

        mediaPlayer.setAudioSpectrumListener(null);

    }


    public void addMpCurrTimeChg() {

        mediaPlayer.currentTimeProperty().addListener(onMpCurrTimeChg);

    }

    public void removeMpCurrTimeChg() {

        mediaPlayer.currentTimeProperty().removeListener(onMpCurrTimeChg);

    }

    private void displayAudio(double timestamps, double duration, float[] magnitudes, float[] phases) {

        topSeries.getData().clear();
        bottomSeries.getData().clear();

        /*
         * Ajoute les magnitudes (taille 128) au diagramme -60 est la valeur minimale de
         * la magnitude
         */
        for (int i = 0; i < magnitudes.length; i++) {

            /* Partie haute positive */
            topSeries.getData().add(new XYChart.Data<>(String.valueOf(i), magnitudes[i] + 60));

            /* Partie basse négative */
            bottomSeries.getData()
                    .add(new XYChart.Data<>(String.valueOf(i), -(magnitudes[i] + 60)));

        }

    }


    private void displayTime() {

        final double minutes = mediaPlayer.getCurrentTime().toMinutes();

        audioTimeSlider.setValue(mediaPlayer.getCurrentTime().toMinutes());

        durationLabel.setText(
                formatter.format(minutes) + "/" + formatter.format(mediaPlayer.getTotalDuration().toMinutes()));

    }


}
