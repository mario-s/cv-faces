package org.opencv;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mario
 */
public class VideoWindow extends JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(VideoWindow.class);

    private final FaceDetector faceDetector;

    private final VideoPanel videoPanel;

    public VideoWindow() {
        super("Face Detection");
        faceDetector = new FaceDetector();
        videoPanel = new VideoPanel();

        getContentPane().add(videoPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        try {
            readVideoStream();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            dispose();
        }
    }

    private void readVideoStream() throws InterruptedException {
        Mat webcamImage = new Mat();
        VideoCapture capture = new VideoCapture(0);
        Thread.sleep(2000);
        if (capture.isOpened()) {
            while (true) {
                capture.read(webcamImage);
                if (!webcamImage.empty()) {
                    setSize(webcamImage.width() + 40, webcamImage.height() + 60);
                    //-- 3. Apply the classifier to the captured image  
//                    webcamImage = faceDetector.findFace(webcamImage);
                    //-- 4. Display the image  
                    videoPanel.updateImage(webcamImage);
                } else {
                    LOG.warn(" --(!) No captured frame -- Break!");
                    break;
                }
            }
        }
    }

}
