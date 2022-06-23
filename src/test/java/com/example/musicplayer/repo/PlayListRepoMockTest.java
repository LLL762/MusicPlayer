package com.example.musicplayer.repo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
class PlayListRepoMockTest {


    private final PlayListRepoMock repoMock = new PlayListRepoMock();


    @Test
    void getByName_should_return_an_not_empty_optional() {


        assertThat(repoMock.getByName("Test")).isNotEmpty();


    }


}