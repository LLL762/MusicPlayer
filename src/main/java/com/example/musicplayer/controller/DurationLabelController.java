package com.example.musicplayer.controller;

import com.example.musicplayer.model.PlayListModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

/**
 * 21/06/2022.
 *
 * @author Laurent Lamiral
 */
public class DurationLabelController implements PropertyChangeListener, Initializable {

    private final DecimalFormat formatter = new DecimalFormat("##0.00");
    private final DecimalFormatSymbols dfSymbols = new DecimalFormatSymbols();
    @FXML
    private Label durationLabel;
    private PlayListModel playListModel;


    public DurationLabelController(PlayListModel playListModel) {
        this.playListModel = playListModel;
        playListModel.addPropertyChangeListener(this);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        init();

    }

    public void init() {

        dfSymbols.setDecimalSeparator(':');
        formatter.setDecimalFormatSymbols(dfSymbols);

        playListModel.getCurrentMediaPlayer().currentTimeProperty().addListener(e -> displayCurrentTime());


    }

    public void displayCurrentTime() {

        final MediaPlayer mediaPlayer = playListModel.getCurrentMediaPlayer();

        final double minutes = mediaPlayer.getCurrentTime().toMinutes();

        durationLabel.setText(
                formatter.format(minutes) + "/" + formatter.format(mediaPlayer.getTotalDuration().toMinutes()));

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        MediaPlayer newValue;

        if (evt.getPropertyName().equals("media-change")) {

            newValue = (MediaPlayer) evt.getNewValue();
            newValue.currentTimeProperty().addListener(e -> displayCurrentTime());

        }


    }


}
