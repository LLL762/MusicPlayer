package com.example.musicplayer.controller;

import com.example.musicplayer.model.PlayListModel;
import com.example.musicplayer.repo.PlayListRepoImpl;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import lombok.Getter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
public class HomeController implements Initializable, PropertyChangeListener {

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
        playListModel.addPropertyChangeListener(this);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init() {


    }

    @FXML
    private void savePlayList() throws IOException {

        PlayListRepoImpl repo = new PlayListRepoImpl();
        repo.save(playListModel.getPlayList());

    }


    public void openFileChooser() throws IOException {
        final FileChooser fileChooser = new FileChooser();

        final File file = fileChooser.showOpenDialog(songNameLabel.getScene().getWindow());

        playListModel.addMediaFile(file);


        System.out.println(file.getAbsolutePath());

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
