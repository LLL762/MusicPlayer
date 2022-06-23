package com.example.musicplayer.controller;

import com.example.musicplayer.model.PlayListModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
public class MediaButtonBarController implements PropertyChangeListener, Initializable {

    @FXML
    private Button playPauseButton;


    private PlayListModel playListModel;


    private MediaPlayer mediaPlayer;

    private MediaPlayer.Status mediaStatus;


    public MediaButtonBarController(PlayListModel playListModel) {
        this.playListModel = playListModel;
        setUp();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setUp() {

        mediaPlayer = playListModel.getMediaPlayer();
        playListModel.addPropertyChangeListener(this);
        mediaStatus = mediaPlayer.getStatus();


    }


    @FXML
    private void playPause() {

        mediaStatus = mediaPlayer.getStatus();

        if (mediaStatus == MediaPlayer.Status.PLAYING) {

            mediaPlayer.pause();

        }
        if (mediaStatus == MediaPlayer.Status.PAUSED || mediaStatus == MediaPlayer.Status.READY) {

            mediaPlayer.play();

        }


    }

    @FXML
    private void playRandom() {

    }


    @FXML
    private void nextAudioFile() {

    }

    @FXML
    private void previousAudioFile() {


    }

    @FXML
    private void reset() {

        mediaPlayer.seek(Duration.ZERO);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


        if (evt.getPropertyName().equals("media-status-change")) {

            mediaStatus = (MediaPlayer.Status) evt.getNewValue();

        }


        if (evt.getPropertyName().equals("media-change")) {

            mediaPlayer = (MediaPlayer) evt.getNewValue();

            if (mediaStatus == MediaPlayer.Status.PLAYING) {
                mediaPlayer.setAutoPlay(true);

            }

        }


    }


}
