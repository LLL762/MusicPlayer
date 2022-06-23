package com.example.musicplayer.service;

import com.example.musicplayer.entity.AudioFile;
import com.example.musicplayer.entity.PlayList;
import com.example.musicplayer.repo.PlayListRepo;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlayListRepo repo;


    @Override
    public void addAudioFile(PlayList playList, AudioFile file) throws IOException {

        playList.addAudioFile(file);
        repo.save(playList);

    }


}
