package com.example.musicplayer.exception.handler;

import java.util.Objects;

import com.example.musicplayer.exception.ConfigLoadException;

import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */

@Getter
@Setter
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

	public void handle(Throwable e) {

		if (getRootCause(e).getClass().equals(ConfigLoadException.class)) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(getRootCause(e).getMessage());
			alert.showAndWait();

		}
	}

	private Throwable getRootCause(Throwable e) {

		Objects.requireNonNull(e);
		Throwable rootCause = e;

		while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {

			rootCause = rootCause.getCause();
		}

		return rootCause;

	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {

		handle(e);

	}

}
