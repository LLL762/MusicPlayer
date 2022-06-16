package com.example.musicplayer.validation;

import java.util.List;

public interface Validator<T> {

	boolean validate(T object);

	List<String> getErrors();

	void clearErrors();

}
