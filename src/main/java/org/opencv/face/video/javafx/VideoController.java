package org.opencv.face.video.javafx;

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
        Image image = new Image("http://img2.wikia.nocookie.net/__cb20131002101853/rio/images/3/39/Mainpage-Navmap-Thumb-Savannah.jpg");
        videoView.setImage(image);
    }
}
