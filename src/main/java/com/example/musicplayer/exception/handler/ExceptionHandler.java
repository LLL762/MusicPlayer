package com.example.musicplayer.exception.handler;

import com.example.musicplayer.exception.ConfigLoadException;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

/**
 * 23/06/2022.
 *
 * @author Laurent Lamiral
 */


@Getter
@Setter
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {


    private Task<Void> task = new Task<>() {
        @Override
        protected Void call() throws Exception {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
            return null;
        }
    };

    @Override
    public void uncaughtException(Thread thread, Throwable e) {


//        if (e.getClass().equals(RuntimeException.class)) {
//
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.showAndWait();
//
//
//            Platform.exit();
//
//
//        }

        if (e.getClass().equals(ConfigLoadException.class)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            
            return;


        }


        e.printStackTrace();
        System.exit(1);


    }


}
