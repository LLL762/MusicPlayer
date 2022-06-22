package com.example.musicplayer.controller;

import com.example.musicplayer.model.PlayListModel;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
public class HomeController implements Initializable {

    private List<File> playList = new ArrayList<>();
    private File playListDirectory;

    private InvalidationListener audioTimeListener;

    private PlayListModel playListModel;


    @FXML
    private Label songNameLabel;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private Button playPauseButton;

    public HomeController(PlayListModel playListModel) {

        this.playListModel = playListModel;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init() {


        //  initFileListView();


    }


//	private void initMedia(String path) {
//
//		mediaPlayer = new MediaPlayer(new Media(path));
//
//		mediaPlayer.setOnEndOfMedia(this::nextSong);
//
//		playListModel.setCurrentMediaPlayer(mediaPlayer);
//
//	}

//    private void initFileListView() {
//
//        final MultipleSelectionModel<String> selectionModel;
//
//        fileListView.getItems().addAll(
//                playList.stream()
//                        .map(File::getName)
//                        .toList());
//
//        fileListView.getSelectionModel().selectFirst();
//        songNameLabel.setText(fileListView.getSelectionModel().getSelectedItem());
//
//        selectionModel = fileListView.getSelectionModel();
//        selectionModel.selectedIndexProperty()
//                .addListener(observable -> setPlayListItem(selectionModel.getSelectedItem()));
//
//    }

//    private void setPlayListItem(final String item) {
//
//        final String fileName = playList.stream()
//                .filter(v -> v.getName().equals(item))
//                .findFirst()
//                .orElseThrow()
//                .getName();
//
//        mediaPlayer.stop();
//
//        initMedia(HomeController.class.getResource("/songs").toExternalForm() + "/" + fileName);
//
//        songNameLabel.setText(fileName);
//
//        playListModel.setCurrentMediaPlayer(mediaPlayer);
//
//        mediaPlayer.play();
//
//    }

    public void playPause() {

//        final MediaPlayer mediaPlayer = playListModel.getCurrentMediaPlayer();
//
//        final MediaPlayer.Status status = mediaPlayer.getStatus();
//
//        if (status.equals(MediaPlayer.Status.PLAYING)) {
//
//            playPauseButton.getStyleClass().removeAll("play-button");
//            playPauseButton.getStyleClass().add("pause-button");
//
//            mediaPlayer.pause();
//            return;
//        }
//
//        playPauseButton.getStyleClass().removeAll("pause-button");
//        playPauseButton.getStyleClass().add("play-button");
//        mediaPlayer.play();

    }

    public void reset() {


//        final MediaPlayer mediaPlayer = playListModel.getMediaPlayer();
//
//
//        mediaPlayer.seek(Duration.ZERO);
    }

//    public void previousSong() {
//
//        final int currentIndex = fileListView.getSelectionModel().getSelectedIndex();
//
//        if (currentIndex <= 0) {
//
//            fileListView.getSelectionModel().selectLast();
//            return;
//
//        }
//
//        fileListView.getSelectionModel().selectPrevious();
//
//    }

//    public void nextSong() {
//
//        final int currentIndex = fileListView.getSelectionModel().getSelectedIndex();
//
//        if (currentIndex >= fileListView.getItems().size() - 1) {
//
//            fileListView.getSelectionModel().selectFirst();
//            return;
//
//        }
//
//        fileListView.getSelectionModel().selectNext();
//
//    }

    public void playRandom() {

    }

    public void openFileChooser() {
        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(songNameLabel.getScene().getWindow());

    }

}
