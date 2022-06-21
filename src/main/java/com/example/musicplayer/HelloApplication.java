package com.example.musicplayer;

import com.example.musicplayer.controller.HomeController;
import com.example.musicplayer.controller.MediaPlayerInfoDisplay;
import com.example.musicplayer.service.AudioSliderServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        fxmlLoader.setControllerFactory(
                c -> new HomeController(new AudioSliderServiceImpl(), new MediaPlayerInfoDisplay()));

        Scene scene = new Scene(fxmlLoader.load());


        scene.getStylesheets().add(HelloApplication.class.getResource("/style/home.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
}