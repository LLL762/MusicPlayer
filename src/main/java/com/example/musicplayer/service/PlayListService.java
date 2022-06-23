package com.example.musicplayer.service;

import com.example.musicplayer.entity.AudioFile;
import com.example.musicplayer.entity.PlayList;

import java.io.IOException;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */
public interface PlayListService {

    void addAudioFile(PlayList playList, AudioFile file) throws IOException;


}
