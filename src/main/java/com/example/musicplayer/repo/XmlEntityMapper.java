package com.example.musicplayer.repo;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

import java.io.File;
import java.io.IOException;

import com.example.musicplayer.exception.ReadOperationFailsException;
import com.example.musicplayer.exception.WriteOperationFailsException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public enum XmlEntityMapper implements EntityMapper {

	INSTANCE;

	private final XmlMapper xmlMapper = new XmlMapper();

	XmlEntityMapper() {

		init();

	}

	private void init() {

		xmlMapper.enable(INDENT_OUTPUT);

	}

	@Override
	public <T> T read(final String path, Class<T> typeClass) throws ReadOperationFailsException {

		final File file = new File(path);
		T output;

		try {
			output = xmlMapper.readValue(file, typeClass);
		} catch (IOException e) {

			throw new ReadOperationFailsException();

		}

		return output;

	}

	@Override
	public <T> T write(final String path, final T object) throws WriteOperationFailsException {

		final File file = new File(path);

		try {
			xmlMapper.writeValue(file, object);
		} catch (IOException e) {

			throw new WriteOperationFailsException();

		}

		return object;

	}

}
