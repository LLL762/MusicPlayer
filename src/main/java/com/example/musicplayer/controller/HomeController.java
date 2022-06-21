package com.example.musicplayer.controller;

import com.example.musicplayer.component.AudioSliderBuilder;
import com.example.musicplayer.model.PlayListModel;
import com.example.musicplayer.service.AudioSliderService;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import lombok.Getter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.util.Collections.addAll;


@Getter
public class HomeController implements Initializable {


    private final AudioSliderService audioSliderService;
    private List<File> playList = new ArrayList<>();
    private File playListDirectory;
    private Status mediaStatus = Status.UNKNOWN;
    private InvalidationListener audioTimeListener;


    private PlayListModel playListModel;

    private MediaPlayer mediaPlayer;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label durationLabel;
    @FXML
    private Slider audioTimeSlider;


    @FXML
    private ListView<String> fileListView;

    @FXML
    private Button playPauseButton;


    public HomeController(AudioSliderService audioSliderService, PlayListModel playListModel) {
        this.audioSliderService = audioSliderService;
        this.playListModel = playListModel;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void init() {


        audioTimeListener = e -> audioSliderService.displayTime(audioTimeSlider, mediaPlayer, durationLabel);
        initPlayList();
        initMedia(playList.get(0).toURI().toString());


        initFileListView();


        mediaPlayer.play();

    }

    public void initPlayList() {

        final String pathname = HomeController.class.getResource("/songs").toExternalForm();

        playListDirectory = new File(
                String.valueOf(pathname).substring(6));

        addAll(playList, Objects.requireNonNull(playListDirectory.listFiles()));

    }

    private void initMedia(String path) {

        mediaPlayer = new MediaPlayer(new Media(path));

        mediaPlayer.setOnReady(this::initAudioTimeSlider);

        mediaPlayer.currentTimeProperty()
                .addListener(audioTimeListener);


        mediaPlayer.setOnEndOfMedia(this::nextSong);

        playListModel.setCurrentMediaPlayer(mediaPlayer);

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
        selectionModel.selectedIndexProperty()
                .addListener(observable -> setPlayListItem(selectionModel.getSelectedItem()));

    }

    private void initAudioTimeSlider() {

        AudioSliderBuilder.build(audioTimeSlider, 0, mediaPlayer.getTotalDuration().toMinutes(),
                mediaPlayer.getCurrentTime().toMinutes());

        audioTimeSlider.valueProperty().addListener(obs -> audioSliderService.showProgress(audioTimeSlider));

        hideThumb();

    }

    @FXML
    private void removeAudioTimeListener() {

        if (mediaStatus == Status.UNKNOWN) {
            mediaStatus = mediaPlayer.getStatus();

            // Solve problem when user types fast or use mouse and keyboard at the same time
            // without throwing NPE, wrapping mediaStatus into an optional may be better.
        }

        mediaPlayer.pause();

        mediaPlayer.currentTimeProperty().removeListener(audioTimeListener);
    }

    @FXML
    private void setMediaCurrentTime() {

        mediaPlayer.seek(Duration.minutes(audioTimeSlider.getValue()));

        mediaPlayer.currentTimeProperty().addListener(audioTimeListener);

        if (mediaStatus.equals(Status.PLAYING)) {

            mediaPlayer.play();
            mediaStatus = Status.UNKNOWN;

        }

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

        playListModel.setCurrentMediaPlayer(mediaPlayer);

        mediaPlayer.play();

    }

    public void playPause() {

        final MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status.equals(MediaPlayer.Status.PLAYING)) {

            playPauseButton.getStyleClass().removeAll("play-button");
            playPauseButton.getStyleClass().add("pause-button");

            mediaPlayer.pause();
            return;
        }

        playPauseButton.getStyleClass().removeAll("pause-button");
        playPauseButton.getStyleClass().add("play-button");
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

    public void playRandom() {

    }

    public void openFileChooser() {
        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(songNameLabel.getScene().getWindow());

    }

    public void showThumb() {

        audioSliderService.showThumb(audioTimeSlider);

    }

    public void hideThumb() {

        audioSliderService.hideThumb(audioTimeSlider);
        audioTimeSlider.getTooltip().hide();

    }

    @FXML
    private void updateTooltip(MouseEvent e) {

        final Point2D mousePos = audioTimeSlider.localToScreen(e.getX(), e.getY());
        audioTimeSlider.getTooltip().show(audioTimeSlider, mousePos.getX(), mousePos.getY() + 20);

    }


}
