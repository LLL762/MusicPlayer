package com.example.musicplayer.utility;

import java.util.Random;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */
public final class RandomInstance {

    private static final Random RANDOM = new Random();

    private RandomInstance() {
    }

    public static int getRangomInt(final int origin, final int bound) {

        return RANDOM.nextInt(origin, bound);
    }

    public static Random getInstance() {

        return RANDOM;
    }


}
