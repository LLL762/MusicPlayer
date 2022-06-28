package com.example.musicplayer.repo;

import com.example.musicplayer.config.AppConfig;
import com.example.musicplayer.entity.UserSettings;
import com.example.musicplayer.entitymanager.UserSettingsManager;
import com.example.musicplayer.utility.reset.DefaultUserSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.example.musicplayer.utility.ResourceUtility.getResourcePath;

public class UserSettingsRepoImpl implements UserSettingsRepo {

    private static final String PROPERTY_NAME = "settings.path";
    private final UserSettingsManager manager;

    private final EntityMapper mapper;
    private String path;

    public UserSettingsRepoImpl(final UserSettingsManager manager, final EntityMapper mapper) {

        this.manager = manager;
        this.mapper = mapper;

        //   init();

    }

    public void init() {

        final String relativePath = AppConfig.INSTANCE.getProperty(PROPERTY_NAME);

        try {
            path = getResourcePath(relativePath);
        } catch (NullPointerException e) {


            //	throw new ResourceNotFoundException("Missing file : " + relativePath);
        }

    }

    public void refresh() {

        init();

    }

    @Override
    public Optional<UserSettings> get() {

        UserSettings outputValue;

        if (manager.getCurrentSettings() != null) {

            return Optional.of(manager.getCurrentSettings());

        }

        outputValue = mapper.read(path, UserSettings.class);

        if (outputValue == null) {

            return Optional.empty();
        }

        manager.setCurrentSettings(outputValue);

        return Optional.of(outputValue);

    }

    @Override
    public UserSettings save(UserSettings userPreferences) {

        final UserSettings output;

        if (manager.getCurrentSettings() != null && manager.getCurrentSettings().equals(userPreferences)) {

            return userPreferences;

        }

        output = mapper.write(path, userPreferences);
        manager.setCurrentSettings(output);

        return output;

    }

    @Override
    public UserSettings restore() {

        final String relativePath = AppConfig.INSTANCE.getProperty(PROPERTY_NAME);


        Path source = null;

        source = Paths.get("/src/main/resources").toAbsolutePath();


        Path newFolder = Paths.get(source + "/newFolder/");

        try {
            Files.createDirectories(newFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return save(DefaultUserSettings.build());

    }

    @Override
    public UserSettings restorePrevious() {
        // TODO Auto-generated method stub
        return null;
    }
}
