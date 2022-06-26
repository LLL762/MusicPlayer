package com.example.musicplayer.repo;

import static com.example.musicplayer.utility.ResourceUtility.getResourceUri;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import com.example.musicplayer.entity.AudioFile;
import com.example.musicplayer.entity.PlayList;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
public class PlayListRepoMock implements PlayListRepo {

	private final AudioFile[] mockAudioFiles = {

			new AudioFile("inspiring emotional",
					getResourceUri("/songs/inspiring-emotional-uplifting-piano-112623.mp3")),

			new AudioFile("Lofi study",
					getResourceUri("/songs/lofi-study-112191.mp3")),

			new AudioFile("stomping rock four shots",
					getResourceUri("/songs/stomping-rock-four-shots-111444.mp3"))

	};

	private final PlayList testPlayList = new PlayList("Test",
			new LinkedList<>(Arrays.stream(mockAudioFiles).toList()));

	public Optional<PlayList> getByName(String name) {
		return name.equals("Test") ? Optional.of(testPlayList) : Optional.empty();
	}

	@Override
	public PlayList save(PlayList object) {
		System.out.println("toto");
		return null;
	}
}
