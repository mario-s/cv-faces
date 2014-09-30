package org.opencv.face.video.javafx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

/**
 *
 * @author spindizzy
 */
public class VideoController {

    @FXML
    private VideoView videoView;

    @FXML
    protected void initialize() {

        Task<Void> task = new CameraTask(videoView);
        Thread th = new Thread(task);
        th.start();

    }

}
