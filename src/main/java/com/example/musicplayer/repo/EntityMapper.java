package com.example.musicplayer.repo;

import com.example.musicplayer.exception.ReadOperationFailsException;
import com.example.musicplayer.exception.WriteOperationFailsException;

public interface EntityMapper {

	<T> T read(final String path, Class<T> typeClass) throws ReadOperationFailsException;

	<T> T write(final String path, final T object) throws WriteOperationFailsException;

}
