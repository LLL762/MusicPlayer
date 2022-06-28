package com.example.musicplayer.repo;

import com.example.musicplayer.HelloApplication;
import com.example.musicplayer.entitymanager.UserSettingsManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
class PlayListRepoMockTest {

    private final PlayListRepoMock repoMock = new PlayListRepoMock();

    private final UserSettingsRepoImpl repo = new UserSettingsRepoImpl(new UserSettingsManager(),
            XmlEntityMapper.INSTANCE);

    @Test
    void getByName_should_return_an_not_empty_optional() throws URISyntaxException, IOException {

        System.out.println(Paths.get("src/main/resources").toAbsolutePath());
        System.out.println(Paths.get("/src/main/resources").toAbsolutePath());
        System.out.println(Paths.get(this.getClass().getResource("").toURI()));
        final Path x = Paths.get(HelloApplication.class.getResource("").toURI());

        System.out.println(x);

        final Path dir = Paths.get(x.toString(), "/new-folder/");
        Files.createDirectories(dir);

        System.out.println(dir);

        //repo.restore();

    }

}