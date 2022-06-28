package com.example.musicplayer.repo;

import com.example.musicplayer.entity.PlayList;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */
public class PlayListRepoImpl implements PlayListRepo {

    private static final String FOLDER_PATH;

    static {
        try {
            FOLDER_PATH = Paths.get(Objects.requireNonNull(PlayListRepoImpl.class.getResource("/playlists")).toURI())
                    .toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PlayList> getByName(String name) {

        final File folder = new File(FOLDER_PATH);

        final Optional<File> file = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(f -> f.getName().equals(name))
                .findFirst();

        if (file.isPresent()) {

//			return Optional.of(xmlMapper.readValue(file.get(), PlayList.class));

        }

        return Optional.empty();

    }

    @Override
    public PlayList save(PlayList playList) {

        final String path = Paths.get(FOLDER_PATH, playList.getName() + ".xml").toString();

        return XmlEntityMapper.INSTANCE.write(path, playList);

    }
}
