package org.opencv.face.video.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author spindizzy
 */
public class VideoApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("videoApp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
