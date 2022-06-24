package com.example.musicplayer.config;

import com.example.musicplayer.HelloApplication;
import com.example.musicplayer.exception.ConfigLoadException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 24/06/2022.
 *
 * @author Laurent Lamiral
 */


public class AppConfigInstance {

    private static final String PROPERTY_PATH;
    private static final Properties APP_CONFIG = new Properties();

    static {
        try {
            PROPERTY_PATH = HelloApplication.class.getResource("/appliction.properties").getPath();
            APP_CONFIG.load(new BufferedReader(new FileReader(new File(PROPERTY_PATH))));

        } catch (NullPointerException e) {

            throw new ConfigLoadException(e.getMessage());

        } catch (IOException e) {
           
            throw new ConfigLoadException("Config loading fail!");

        }

    }


    private AppConfigInstance() {
    }

    public static Properties getInstance() {

        return APP_CONFIG;
    }


}
