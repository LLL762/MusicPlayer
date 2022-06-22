package com.example.musicplayer.model;

import com.example.musicplayer.entity.PlayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Getter
@Setter
public class PlayListModel {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private PlayList playList;


    private int currentAudioFileIndex;
    private MediaPlayer mediaPlayer;

    public PlayListModel(PlayList playList) {
        this.playList = playList;

        changeMedia(0);
    }

    public void init() {

        changeMedia(0);

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
        mediaPlayer.play();

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

    public void setMediaPlayer(MediaPlayer newMediaPlayer) {


        pcs.firePropertyChange("media-change", this.mediaPlayer, newMediaPlayer);

        this.mediaPlayer = newMediaPlayer;

    }

}
