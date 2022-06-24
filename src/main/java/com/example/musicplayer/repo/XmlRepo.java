package com.example.musicplayer.repo;

public interface XmlRepo<T> extends Repo<T> {

	String getPath();

	@Override
	default T save(T object) {

		return XmlEntityMapper.INSTANCE.write(getPath(), object);

	}

}
