package com.example.musicplayer.component;

import javafx.scene.control.ComboBox;

/**
 * 20/06/2022.
 *
 * @author Laurent Lamiral
 */
public final class SpeedComboBuilder {

    private static final String[] VALUES = {
            "x0.1",
            "x0.25",
            "x0.5",
            "x0.75",
            "x1",
            "x1.25",
            "x1.5",
            "x1.75",
            "x2",
            "x3",
            "x4",
            "x5",
            "x6",
            "x7",
            "x8"
    };


    private SpeedComboBuilder() {
    }


    public static void build(ComboBox<String> comboBox) {

        comboBox.getItems().addAll(VALUES);

        comboBox.getSelectionModel().select("x1");


    }


}
