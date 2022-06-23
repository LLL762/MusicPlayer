package com.example.musicplayer.repo;

import com.example.musicplayer.entity.AudioFile;
import com.example.musicplayer.entity.PlayList;

import java.util.Arrays;
import java.util.Optional;

import static com.example.musicplayer.utility.ResourceUtility.getResource;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
public class PlayListRepoMock implements PlayListRepo {


    private final AudioFile[] mockAudioFiles = {

            new AudioFile("inspiring emotional",
                    getResource("/songs/inspiring-emotional-uplifting-piano-112623.mp3")),

            new AudioFile("Lofi study",
                    getResource("/songs/lofi-study-112191.mp3")),

            new AudioFile("stomping rock four shots",
                    getResource("/songs/stomping-rock-four-shots-111444.mp3"))


    };


    private final PlayList testPlayList = new PlayList("Test", Arrays.stream(mockAudioFiles).toList());


    @Override
    public Optional<PlayList> getByName(String name) {
        return name.equals("Test") ? Optional.of(testPlayList) : Optional.empty();
    }

    @Override
    public void save(PlayList object) {
        System.out.println("toto");
    }
}
