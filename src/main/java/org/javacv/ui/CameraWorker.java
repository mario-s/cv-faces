package org.javacv.ui;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Size;
import org.javacv.detect.Detectable;
import org.javacv.detect.DetectorFactory;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * A worker that deals with image update from the camera.
 *
 * @author spindizzy
 */
public class CameraWorker extends SwingWorker<Void, Mat> {

    private static final Logger LOG = LoggerFactory.getLogger(CameraWorker.class);

    private final VideoCanvas videoPanel;

    private final JFrame videoWindow;

    private final Detectable faceDetector;

    private final VideoCapture capture;

    private boolean updated;
    
    public CameraWorker(JFrame videoWindow, VideoCanvas videoPanel) {
        this.videoWindow = videoWindow;
        this.videoPanel = videoPanel;
        faceDetector = DetectorFactory.create("haar");
        capture = new VideoCapture(0);
    }

    @Override
    protected Void doInBackground() throws Exception {
        long lastMarked = 0;

        Thread.sleep(1000);
        Mat webcamImage = new Mat();
        while (!isCancelled()) {
            capture.read(webcamImage);
            if (!webcamImage.empty()) {

                updateWindowSize(webcamImage);
                long marked = markObjects(webcamImage);

                if (LOG.isDebugEnabled() && marked != lastMarked) {
                    LOG.debug("number of faces: {}", marked);
                    lastMarked = marked;
                }
            } else {
                LOG.warn(" --(!) No captured frame -- Break!");
                break;
            }
        }

        return null;
    }

    private void updateWindowSize(Mat webcamImage) {
        if(!updated){
            Size size = webcamImage.size();
            videoWindow.setSize(size.width() + 40, size.height() + 60);
            updated = true;
        }
    }

    private long markObjects(Mat webcamImage) {
        long marked = faceDetector.markObjects(webcamImage);
        videoPanel.updateImage(webcamImage);
        return marked;
    }

    @Override
    protected void done() {
        capture.release();
    }

}
