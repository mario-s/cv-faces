package org.javacv.ui;

import org.bytedeco.javacpp.opencv_core.Size;
import org.javacv.face.detection.FaceDetector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 *
 * @author spindizzy
 */
public class CameraWorker extends SwingWorker<Void, Mat> {

    private static final Logger LOG = LoggerFactory.getLogger(CameraWorker.class);

    private final VideoPanel videoPanel;

    private final JFrame videoWindow;

    private final FaceDetector faceDetector;

    private final VideoCapture capture;

    private boolean updated;
    
    public CameraWorker(JFrame videoWindow, VideoPanel videoPanel) {
        this.videoWindow = videoWindow;
        this.videoPanel = videoPanel;
        faceDetector = new FaceDetector();
        capture = new VideoCapture(0);
    }

    @Override
    protected Void doInBackground() throws Exception {

        Thread.sleep(1000);
        Mat webcamImage = new Mat();
        while (!isCancelled()) {
            capture.read(webcamImage);
            if (!webcamImage.empty()) {
                
                if(!updated){
                    Size size = webcamImage.size();
                    videoWindow.setSize(size.width() + 40, size.height() + 60);
                    updated = true;
                }
                
                faceDetector.markFaces(webcamImage);
                videoPanel.updateImage(webcamImage);
            } else {
                LOG.warn(" --(!) No captured frame -- Break!");
                break;
            }
        }

        return null;
    }

    @Override
    protected void done() {
        capture.release();
    }

}
