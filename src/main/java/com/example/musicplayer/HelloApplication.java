package com.example.musicplayer;

import com.example.musicplayer.controller.HomeController;
import com.example.musicplayer.model.PlayListModel;
import com.example.musicplayer.repo.PlayListRepoMock;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));


        PlayListRepoMock repoMock = new PlayListRepoMock();
        PlayListModel playListModel = new PlayListModel(repoMock.getByName("Test").orElseThrow());

        fxmlLoader.setControllerFactory(e -> {

            Constructor<?> cons;
            try {
                cons = e.getConstructor(PlayListModel.class);
                return cons.newInstance(playListModel);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                     | IllegalArgumentException | InvocationTargetException e1) {

                e1.printStackTrace();
            }

            return null;
        });

        Scene scene = new Scene(fxmlLoader.load());

        HomeController homeController = fxmlLoader.getController();

        homeController.init();

        scene.getStylesheets().add(HelloApplication.class.getResource("/style/home.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
}