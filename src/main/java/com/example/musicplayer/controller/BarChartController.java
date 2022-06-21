package com.example.musicplayer.controller;

import com.example.musicplayer.model.PlayListModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 21/06/2022.
 *
 * @author Laurent Lamiral
 */

@Getter
@Setter
public class BarChartController implements PropertyChangeListener, Initializable {


    private final XYChart.Series<String, Number> topSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> bottomSeries = new XYChart.Series<>();

    @FXML
    private BarChart<String, Number> audioBarChart;

    private PlayListModel playListModel;


    public BarChartController(PlayListModel playListModel) {
        this.playListModel = playListModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();

    }

    public void init() {

        audioBarChart.getData().add(topSeries);
        audioBarChart.getData().add(bottomSeries);

        playListModel.addPropertyChangeListener(this);

        playListModel.getCurrentMediaPlayer()
                .setAudioSpectrumListener(this::displayAudio);

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


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        MediaPlayer newValue;

        if (evt.getPropertyName().equals("media-change")) {

            newValue = (MediaPlayer) evt.getNewValue();
            newValue.setAudioSpectrumListener(this::displayAudio);

        }


    }


}
