package com.example.musicplayer.model;

import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
public class PlayListModel {


    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private List<File> playList = new LinkedList<>();


    private MediaPlayer currentMediaPlayer;


    public PlayListModel() {
    }


    public void init() {


    }


    public void addFile(String absolutePath) {

        final File file = new File(absolutePath);

        if (!file.exists()) {
        }

        playList.add(file);

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


    public void setCurrentMediaPlayer(MediaPlayer newMediaPlayer) {


        pcs.firePropertyChange("media-change", this.currentMediaPlayer, newMediaPlayer);

        this.currentMediaPlayer = newMediaPlayer;


    }
}
