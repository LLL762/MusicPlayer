package com.example.musicplayer.repo;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

import java.io.File;
import java.io.IOException;

import com.example.musicplayer.exception.SaveFailException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public enum XmlEntityMapper {

	INSTANCE;

	private final XmlMapper xmlMapper = new XmlMapper();

	XmlEntityMapper() {

		init();

	}

	private void init() {

		xmlMapper.enable(INDENT_OUTPUT);

	}

	public <T> T write(final String path, final T object) {

		final File file = new File(path);

		try {
			xmlMapper.writeValue(file, object);
		} catch (IOException e) {

			throw new SaveFailException();

		}

		return object;

	}

}
