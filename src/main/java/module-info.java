module com.example.musicplayer {
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.media;
	requires javafx.graphics;
	requires static lombok;
	requires transitive java.desktop;

	exports com.example.musicplayer;
	exports com.example.musicplayer.controller;
	exports com.example.musicplayer.model;

	opens com.example.musicplayer.controller to javafx.fxml;

}