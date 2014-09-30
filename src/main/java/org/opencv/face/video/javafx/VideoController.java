package org.opencv.face.video.javafx;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 *
 * @author spindizzy
 */
public class VideoController {

    @FXML
    private ImageView videoView;
    
    private CameraTask cameraTask;

    @FXML
    protected void initialize() {
        videoView.setPreserveRatio(true);
        cameraTask = new CameraTask(videoView);
        Thread thread = new Thread(cameraTask);
        thread.start();
    }
    
    void onClosing() {
        if(cameraTask != null){
            cameraTask.cancel();
        }
    }

}
