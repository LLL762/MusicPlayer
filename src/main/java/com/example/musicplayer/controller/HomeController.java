package com.example.musicplayer.controller;

import com.example.musicplayer.component.AudioSliderBuilder;
import com.example.musicplayer.component.SpeedComboBuilder;
import com.example.musicplayer.service.AudioSliderService;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioSpectrumListener;
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

import static com.example.musicplayer.component.SliderDecorator.setUp;
import static java.util.Collections.addAll;


@Getter
public class HomeController implements Initializable {

    private final MediaPlayerInfoDisplay mpInfoDisplay;
    private final AudioSliderService audioSliderService;
    private List<File> playList = new ArrayList<>();
    private File playListDirectory;
    private Status mediaStatus = Status.UNKNOWN;
    private InvalidationListener audioTimeListener;

    private MediaPlayer mediaPlayer;
    @FXML
    private Label songNameLabel;

    @FXML
    private Label durationLabel;
    @FXML
    private Slider audioTimeSlider;

    @FXML
    private BarChart<String, Number> audioBarChart;
    @FXML
    private Slider volumeSlider;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private Button playPauseButton;
    @FXML
    private ComboBox<String> speedComboBox;


    private XYChart.Series<String, Number> topSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> bottomSeries = new XYChart.Series<>();

    public HomeController(final AudioSliderService audioSliderService, final MediaPlayerInfoDisplay infoDisplay) {
        super();
        this.audioSliderService = audioSliderService;
        this.mpInfoDisplay = infoDisplay;
        audioTimeListener = e -> audioSliderService.displayTime(audioTimeSlider, mediaPlayer, durationLabel);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        init();

    }

    public void init() {

        initPlayList();
        initAudioBarChart();
        initMedia(playList.get(0).toURI().toString());
        initVolumeSlider();
        initSpeedComboBox();
        initFileListView();
        initSpeedComboBox();

        mediaPlayer.play();

    }

    private void initSpeedComboBox() {

        SpeedComboBuilder.build(speedComboBox);

    }

    private void initPlayList() {

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

        mediaPlayer.setAudioSpectrumListener(spectrumListener);

        mediaPlayer.setOnEndOfMedia(this::nextSong);

    }

    private void displayAudio(double timestamps, double duration, float[] magnitudes, float[] phases) {

        mediaPlayer.setAudioSpectrumListener(null);

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

        mediaPlayer.setAudioSpectrumListener(spectrumListener);

    }

    private void initAudioBarChart() {

        audioBarChart.getYAxis().setAutoRanging(false);

        audioBarChart.getData().add(topSeries);
        audioBarChart.getData().add(bottomSeries);


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

    private void initVolumeSlider() {

        setUp(volumeSlider, 0, 1, 1);

        volumeSlider.valueProperty().addListener(e -> changeAudioVolume());

    }

    private void changeAudioVolume() {

        mediaPlayer.setVolume(volumeSlider.getValue());

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
        changeAudioVolume();
        changeSpeed();

        mediaPlayer.play();

    }

    @FXML
    private void changeSpeed() {

        mediaPlayer.setRate(Double.parseDouble(speedComboBox.getValue().substring(1)));

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

    }    private AudioSpectrumListener spectrumListener = HomeController.this::displayAudio;

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
