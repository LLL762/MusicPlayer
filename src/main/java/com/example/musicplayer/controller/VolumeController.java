package com.example.musicplayer.controller;

import com.example.musicplayer.model.PlayListModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
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
public class VolumeController implements PropertyChangeListener, Initializable {


    @FXML
    private Slider volumeSlider;
    private PlayListModel playListModel;


    public VolumeController(PlayListModel playListModel) {
        this.playListModel = playListModel;
        init();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpVolumeSlider();
    }

    public void init() {

        playListModel.addPropertyChangeListener(this);

    }


    public void setUpVolumeSlider() {

        volumeSlider.valueProperty().addListener(e -> changeAudioVolume());

    }

    public void changeAudioVolume() {

        playListModel.getMediaPlayer().setVolume(volumeSlider.getValue());

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        MediaPlayer newValue;

        if (evt.getPropertyName().equals("media-change")) {


            newValue = (MediaPlayer) evt.getNewValue();
            newValue.setVolume(volumeSlider.getValue());

        }
    }


}
