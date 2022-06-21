module com.example.musicplayer {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;
    requires javafx.graphics;
    requires static lombok;


    exports com.example.musicplayer;
    exports com.example.musicplayer.controller;

    opens com.example.musicplayer.controller to javafx.fxml;

    exports com.example.musicplayer.service;
}