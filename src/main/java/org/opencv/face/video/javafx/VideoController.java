package org.opencv.face.video.javafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author spindizzy
 */
public class VideoController {

    private final ObjectProperty<Image> imageProperty;

    @FXML
    private ImageView videoView;

    private CameraTask cameraTask;

    public VideoController() {
        imageProperty = new SimpleObjectProperty<>();
    }

    @FXML
    protected void initialize() {
        cameraTask = new CameraTask(imageProperty);
        videoView.setPreserveRatio(true);

        videoView.imageProperty().bind(imageProperty);

        Thread th = new Thread(cameraTask);
//        th.setDaemon(true);
        th.start();
    }

    void onClosing() {
        if (cameraTask != null) {
            cameraTask.cancel();
        }
    }

}
