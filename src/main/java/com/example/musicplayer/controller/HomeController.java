package com.example.musicplayer.controller;

import static com.example.musicplayer.component.SliderDecorator.setUp;
import static java.util.Collections.addAll;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class HomeController implements Initializable {

	private List<File> playList = new ArrayList<>();
	private File playListDirectory;

	private Media media;

	private Status mediaStatus = Status.UNKNOWN;

	private MediaPlayer mediaPlayer;
	@FXML
	private Label songNameLabel, durationLabel;
	@FXML
	private Slider audioTimeSlider;
	private final InvalidationListener audioTimeListener = observable -> {

		DecimalFormat formatter = new DecimalFormat("##0.00");
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		double minutes = mediaPlayer.getCurrentTime().toMinutes();

		audioTimeSlider.setValue(mediaPlayer.getCurrentTime().toMinutes());

		otherSymbols.setDecimalSeparator(':');

		formatter.setDecimalFormatSymbols(otherSymbols);

		durationLabel.setText(
				formatter.format(minutes) + "/" + formatter.format(mediaPlayer.getTotalDuration().toMinutes()));

	};
	@FXML
	private BarChart<String, Number> audioBarChart;
	@FXML
	private Slider speedSlider, volumeSlider;

	@FXML
	private ListView<String> fileListView;

	@FXML
	private Button playPauseButton;

	private XYChart.Series<String, Number> topSeries = new XYChart.Series<>();
	private XYChart.Series<String, Number> bottomSeries = new XYChart.Series<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		init();

	}

	public void init() {

		initPlayList();
		initAudioBarChart();
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
		mediaPlayer.setAudioSpectrumListener((timestamps, duration, magnitudes, phases) -> {

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

		);

		mediaPlayer.setOnEndOfMedia(this::nextSong);

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
		selectionModel	.selectedIndexProperty()
						.addListener(observable -> setPlayListItem(selectionModel.getSelectedItem()));

	}

	private void initAudioTimeSlider() {

		setUp(audioTimeSlider, 0, mediaPlayer.getTotalDuration().toMinutes(), 0);

		audioTimeSlider.valueProperty().addListener(obs -> {

			String progress = String.valueOf(audioTimeSlider.getValue() * 100 / audioTimeSlider.getMax());

			audioTimeSlider	.lookup(".track")
							.setStyle("-fx-background-color: linear-gradient(to right,  red " + progress + "% ,  grey "
									+ progress
									+ "%);");
		});

		audioTimeSlider.setOnMousePressed(e -> removeAudioTimeListener());
		audioTimeSlider.setOnMouseReleased(e -> setMediaCurrentTime());
		audioTimeSlider.setOnKeyPressed(e -> removeAudioTimeListener());
		audioTimeSlider.setOnKeyReleased(e -> setMediaCurrentTime());

	}

	private void initSpeedSlider() {

		setUp(speedSlider, 0.1, 8, 1);

		speedSlider.valueProperty().addListener(e -> changeAudioSpeed());
	}

	private void initVolumeSlider() {

		setUp(volumeSlider, 0, 1, 1);

		volumeSlider.valueProperty().addListener(e -> changeAudioVolume());

	}

	private void changeAudioVolume() {

		mediaPlayer.setVolume(volumeSlider.getValue());

	}

	private void changeAudioSpeed() {

		mediaPlayer.setRate(speedSlider.getValue());

	}

	private void removeAudioTimeListener() {

		if (mediaStatus == Status.UNKNOWN) {
			mediaStatus = mediaPlayer.getStatus();

			// Solve problem when user types fast or use mouse and keyboard at the same time
			// without throwing NPE, wrapping mediaStatus into an optional may be better.
		}

		mediaPlayer.pause();

		mediaPlayer.currentTimeProperty().removeListener(audioTimeListener);
	}

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
		changeAudioSpeed();

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

}
