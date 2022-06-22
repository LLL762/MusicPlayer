package com.example.musicplayer.utility;

import java.util.Objects;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
public final class ResourceUtility {

    private ResourceUtility() {
    }

    public static String getResource(String uri) {

        return Objects.requireNonNull(
                ResourceUtility.class.getResource(uri)).toExternalForm();

    }


}
