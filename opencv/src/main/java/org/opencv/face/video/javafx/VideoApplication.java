package org.opencv.face.video.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author spindizzy
 */
public class VideoApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("videoApp.fxml"));
        Pane root = (Pane) loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Face Detection");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        
        VideoController controller = loader.getController();
        scene.getWindow().setOnCloseRequest((WindowEvent event) -> {
            controller.onClosing();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
