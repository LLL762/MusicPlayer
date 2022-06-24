package com.example.musicplayer.utility;

import java.util.Objects;

import com.example.musicplayer.HelloApplication;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
public final class ResourceUtility {

	private ResourceUtility() {
	}

	public static String getResourceUri(final String path) {

		return Objects.requireNonNull(
				HelloApplication.class.getResource(path)).toExternalForm();

	}

	public static String getResourcePath(final String path) {

		return HelloApplication.class.getResource(path).getPath();

	}

}
