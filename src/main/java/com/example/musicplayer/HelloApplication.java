package com.example.musicplayer;

import com.example.musicplayer.controller.*;
import com.example.musicplayer.model.PlayListModel;
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

        PlayListModel playListModel = new PlayListModel();


        fxmlLoader.setControllerFactory(e -> {
                    if (e == AudioTimeSliderController.class) {
                        return new AudioTimeSliderController();
                    }

                    if (e == VolumeController.class) {
                        return new VolumeController(playListModel);

                    }

                    if (e == HomeController.class) {
                        return new HomeController(new AudioSliderServiceImpl(), playListModel);
                    }

                    if (e == SpeedController.class) {

                        return new SpeedController(playListModel);


                    }
                    if (e == BarChartController.class) {

                        return new BarChartController(playListModel);


                    }


                    return null;

                }
        );

        Scene scene = new Scene(fxmlLoader.load());

        HomeController homeController = fxmlLoader.getController();

        homeController.init();

        System.out.println(playListModel.getObserver());

        scene.getStylesheets().add(HelloApplication.class.getResource("/style/home.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
}