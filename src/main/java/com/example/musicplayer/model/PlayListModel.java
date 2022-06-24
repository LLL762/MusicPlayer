package com.example.musicplayer.model;

import com.example.musicplayer.entity.AudioFile;
import com.example.musicplayer.entity.PlayList;
import com.example.musicplayer.service.PlayListService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

@Getter
@Setter
@RequiredArgsConstructor
public class PlayListModel {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    private final PlayListService playListService;

    private PlayList playList;

    private int currentAudioFileIndex;
    private MediaPlayer mediaPlayer;


    public PlayListModel(PlayList playList, PlayListService playListService) {
        this.playList = playList;
        this.playListService = playListService;
   
        init();
    }

    public void init() {

        changeMedia(0);
        mediaPlayer.setAutoPlay(true);

    }

    public void changeMedia(int index) {

        int playListSize = playList.getAudioFileList().size();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }


        if (index < 0) {
            index = playListSize;
        }

        if (index >= playListSize) {
            index = 0;
        }

        currentAudioFileIndex = index;

        setMediaPlayer(new MediaPlayer(new Media(playList.getAudioFileList().get(index).getPath())));

        mediaPlayer.setOnEndOfMedia(() -> changeMedia(currentAudioFileIndex + 1));
        mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) ->
                pcs.firePropertyChange("media-status-change", oldStatus, newStatus)
        );


        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setAutoPlay(true);

        }


    }

    public void addMediaFile(File file) throws IOException {

        final AudioFile audioFile = new AudioFile(file.getName(), file.getAbsolutePath());


        // TODO: 24/06/2022 validation

        playListService.addAudioFile(playList, audioFile);

        pcs.firePropertyChange("audio-file-add", 1, audioFile);


    }


    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    public PropertyChangeListener[] getObserver() {

        return pcs.getPropertyChangeListeners();

    }

    private void setMediaPlayer(MediaPlayer newMediaPlayer) {


        pcs.firePropertyChange("media-change", this.mediaPlayer, newMediaPlayer);
        this.mediaPlayer = newMediaPlayer;

    }

    private void setPlayList(PlayList playlist) {

        mediaPlayer.stop();
        pcs.firePropertyChange("play-list-change", this.playList, playlist);
        this.playList = playlist;


    }


}
