package com.example.musicplayer.config;

import static com.example.musicplayer.utility.ResourceUtility.getResourcePath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.example.musicplayer.exception.ConfigLoadException;

/**
 * 24/06/2022.
 *
 * @author Laurent Lamiral
 */

public enum AppConfig {

	INSTANCE;

	private String appPropertyPath;

	private final Properties propertyConfig = new Properties();

	AppConfig() {

		init();

	}

	public void refresh() {

		init();

	}

	private void init() {

		try {
			appPropertyPath = getResourcePath("/application.properties");

			try (BufferedReader reader = new BufferedReader(new FileReader(new File(appPropertyPath)));) {
				propertyConfig.load(reader);
			}

		} catch (NullPointerException e) {

			throw new ConfigLoadException("File application.properties not found!");
		}

		catch (IOException e) {

			throw new ConfigLoadException("Failed loading application.properties");
		}

	}

	public String getProperty(final String key) {

		final String value = propertyConfig.getProperty(key);

		if (value == null) {

			throw new ConfigLoadException(String.format("Property % not found !", key));

		}

		return value;

	}

}
