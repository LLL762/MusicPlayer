package com.example.musicplayer.controller;

import com.example.musicplayer.component.AudioSliderBuilder;
import com.example.musicplayer.model.PlayListModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 21/06/2022.
 *
 * @author Laurent Lamiral
 */
public class AudioTimeSliderController implements PropertyChangeListener, Initializable {

    private PlayListModel playListModel;

    private Status mediaStatus = Status.UNKNOWN;

    private MediaPlayer mediaPlayer;

    @FXML
    private Slider audioTimeSlider;

    public AudioTimeSliderController(PlayListModel playListModel) {
        this.playListModel = playListModel;

        mediaPlayer = playListModel.getMediaPlayer();
        playListModel.addPropertyChangeListener(this);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaPlayer.setOnReady(this::init);

    }

    public void init() {


        setUp();

        audioTimeSlider.valueProperty().addListener(e -> showProgress());

    }

    public void setUp() {

        AudioSliderBuilder.build(audioTimeSlider, 0, mediaPlayer.getTotalDuration().toMinutes(),
                mediaPlayer.getCurrentTime().toMinutes());

        playListModel.getMediaPlayer().currentTimeProperty().addListener(e -> setThumbPosition());

    }

    private void setThumbPosition() {

        audioTimeSlider.setValue(playListModel.getMediaPlayer().getCurrentTime().toMinutes());

    }

    private void showProgress() {

        final String progress = String.valueOf(audioTimeSlider.getValue() * 100 / audioTimeSlider.getMax());

        audioTimeSlider.lookup(".track")
                .setStyle("-fx-background-color: linear-gradient(to right,  red " + progress + "% ,  grey "
                        + progress
                        + "%);");

    }

    @FXML
    private void removeAudioTimeListener(MouseEvent event) {

        if (mediaStatus == Status.UNKNOWN) {
            mediaStatus = mediaPlayer.getStatus();

            // Solve problem when user types fast or use mouse and keyboard at the same time
            // without throwing NPE, wrapping mediaStatus into an optional may be better.
        }

        mediaPlayer.pause();

        playListModel.getMediaPlayer().currentTimeProperty().removeListener(e -> setThumbPosition());

    }

    @FXML
    private void setMediaCurrentTime(MouseEvent event) {

        mediaPlayer.seek(Duration.minutes(audioTimeSlider.getValue()));

        mediaPlayer.currentTimeProperty().addListener(e -> setThumbPosition());

        if (mediaStatus.equals(Status.PLAYING)) {

            mediaPlayer.play();

        }
        mediaStatus = Status.UNKNOWN;

    }

    @FXML
    private void showThumb(MouseEvent event) {

        final StackPane thumb = (StackPane) audioTimeSlider.lookup(".thumb");

        thumb.setOpacity(1);

    }

    @FXML
    private void hideThumb(MouseEvent event) {

        final StackPane thumb = (StackPane) audioTimeSlider.lookup(".thumb");
        thumb.setOpacity(0);
        audioTimeSlider.getTooltip().hide();

    }

    @FXML
    private void updateTooltip(MouseEvent e) {

        final Point2D mousePos = audioTimeSlider.localToScreen(e.getX(), e.getY());
        audioTimeSlider.getTooltip().show(audioTimeSlider, mousePos.getX(), mousePos.getY() + 20);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        MediaPlayer newValue;


        if (evt.getPropertyName().equals("media-change")) {

            newValue = (MediaPlayer) evt.getNewValue();
            mediaPlayer = newValue;

            mediaPlayer.setOnReady(this::setUp);

        }

    }

}
