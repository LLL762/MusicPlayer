package com.example.musicplayer.controller;

import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.util.Collections.addAll;

public class HomeController implements Initializable {


    private List<File> playList = new ArrayList<>();
    private File playListDirectory;

    private int currentSongIndex;

    private Media media;

    private MediaPlayer mediaPlayer;
    @FXML
    private Label songNameLabel;
    @FXML
    private Slider audioTimeSlider;
    private final InvalidationListener audioTimeListener = observable -> {
        audioTimeSlider.setValue(mediaPlayer.getCurrentTime().toMinutes());

    };
    @FXML
    private Slider speedSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ListView<String> fileListView;

    @FXML
    private Button playButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        init();


    }

    public void init() {

        initPlayList();
        initMedia(playList.get(0).toURI().toString());
        initVolumeSlider();
        initSpeedSlider();
        initFileListView();


        mediaPlayer.play();


    }


    private void initPlayList() {

        final String pathname = HomeController.class.getResource("/songs").toExternalForm();

        playListDirectory = new File(
                String.valueOf(pathname).substring(6));

        addAll(playList, Objects.requireNonNull(playListDirectory.listFiles()));


    }

    private void initMedia(String path) {

        media = new Media(path);
        mediaPlayer = new MediaPlayer(media);


        mediaPlayer.setOnReady(this::initAudioTimeSlider);
        mediaPlayer.currentTimeProperty().addListener(audioTimeListener);
        mediaPlayer.setOnEndOfMedia(this::nextSong);

    }


    private void initFileListView() {

        final MultipleSelectionModel<String> selectionModel;

        fileListView.getItems().addAll(
                playList.stream()
                        .map(File::getName)
                        .toList());

        fileListView.getSelectionModel().selectFirst();
        songNameLabel.setText(fileListView.getSelectionModel().getSelectedItem());

        selectionModel = fileListView.getSelectionModel();
        selectionModel.selectedIndexProperty().addListener(observable ->
                setPlayListItem(selectionModel.getSelectedItem())
        );


    }


    private void initAudioTimeSlider() {

        audioTimeSlider.setMin(0);
        audioTimeSlider.setMax(mediaPlayer.getTotalDuration().toMinutes());
        audioTimeSlider.setValue(0);


        audioTimeSlider.setOnMousePressed(e -> removeAudioTimeListener());
        audioTimeSlider.setOnMouseReleased(e -> setMediaCurrentTime());
    }

    private void initSpeedSlider() {

        speedSlider.setMin(0);
        speedSlider.setMax(8);
        speedSlider.setValue(1);

        speedSlider.valueProperty().addListener(e -> changeAudioSpeed());
    }


    private void initVolumeSlider() {

        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(1);
        volumeSlider.valueProperty().addListener(e -> changeAudioVolume());


    }


    private void changeAudioVolume() {

        mediaPlayer.setVolume(volumeSlider.getValue());


    }

    private void changeAudioSpeed() {

        mediaPlayer.setRate(speedSlider.getValue());


    }


    private void removeAudioTimeListener() {

        mediaPlayer.currentTimeProperty().removeListener(audioTimeListener);
    }

    private void setMediaCurrentTime() {


        mediaPlayer.seek(Duration.minutes(audioTimeSlider.getValue()));

        mediaPlayer.currentTimeProperty().addListener(audioTimeListener);


    }


    private void setPlayListItem(final String item) {

        final String fileName = playList.stream()
                .filter(v -> v.getName().equals(item))
                .findFirst()
                .orElseThrow()
                .getName();


        mediaPlayer.stop();

        initMedia(HomeController.class.getResource("/songs").toExternalForm() + "/" + fileName);

        songNameLabel.setText(fileName);
        changeAudioVolume();
        changeAudioSpeed();

        mediaPlayer.play();


    }


    public void playPause() {

        final MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status.equals(MediaPlayer.Status.PLAYING)) {


            playButton.getStyleClass().removeAll("play-button");
            playButton.getStyleClass().add("pause-button");

            mediaPlayer.pause();
            return;
        }

        playButton.getStyleClass().removeAll("pause-button");
        playButton.getStyleClass().add("play-button");
        mediaPlayer.play();

    }

    public void reset() {

        mediaPlayer.seek(Duration.ZERO);
    }

    public void previousSong() {

        final int currentIndex = fileListView.getSelectionModel().getSelectedIndex();

        if (currentIndex <= 0) {

            fileListView.getSelectionModel().selectLast();
            return;

        }

        fileListView.getSelectionModel().selectPrevious();

    }

    public void nextSong() {


        final int currentIndex = fileListView.getSelectionModel().getSelectedIndex();

        if (currentIndex >= fileListView.getItems().size() - 1) {

            fileListView.getSelectionModel().selectFirst();
            return;

        }

        fileListView.getSelectionModel().selectNext();


    }


}
