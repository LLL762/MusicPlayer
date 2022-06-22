package com.example.musicplayer.repo;

import java.util.Optional;

public interface Repo<T> {

    Optional<T> getByName(String name);


}
