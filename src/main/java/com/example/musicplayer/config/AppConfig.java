package com.example.musicplayer.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.example.musicplayer.HelloApplication;
import com.example.musicplayer.exception.ConfigLoadException;

import lombok.Getter;

/**
 * 24/06/2022.
 *
 * @author Laurent Lamiral
 */
@Getter
public class AppConfig {

	private String appPropertyPath;

	private Properties propertyConfig = new Properties();

	public AppConfig() {

		init();

	}

	public void retry() {

		propertyConfig = new Properties();
		init();

	}

	private void init() {

		try {
			appPropertyPath = HelloApplication.class.getResource("/application.properties").getPath();

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

}
