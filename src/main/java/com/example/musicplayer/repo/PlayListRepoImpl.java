package com.example.musicplayer.repo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.example.musicplayer.entity.PlayList;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */
public class PlayListRepoImpl implements PlayListRepo {

	private static final String FOLDER_PATH;

	static {
		try {
			FOLDER_PATH = Paths	.get(Objects.requireNonNull(PlayListRepoImpl.class.getResource("/playlist")).toURI())
								.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private final XmlMapper xmlMapper = new XmlMapper();

	@Override
	public Optional<PlayList> getByName(String name) throws IOException {

		final File folder = new File(FOLDER_PATH);

		final Optional<File> file = Arrays	.stream(Objects.requireNonNull(folder.listFiles()))
											.filter(f -> f.getName().equals(name))
											.findFirst();

		if (file.isPresent()) {

			return Optional.of(xmlMapper.readValue(file.get(), PlayList.class));

		}

		return Optional.empty();

	}

	@Override
	public void save(PlayList playList) throws IOException {

		final File file = new File(FOLDER_PATH, playList.getName() + ".xml");

		xmlMapper.writeValue(file, playList);

	}
}
