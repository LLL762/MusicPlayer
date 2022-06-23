package com.example.musicplayer.repo;

import java.io.IOException;
import java.util.Optional;

public interface Repo<T> {

    Optional<T> getByName(String name) throws IOException;

    void save(T object) throws IOException;


}
