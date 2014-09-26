package org.opencv.face.swing;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class CamWorker extends SwingWorker<Void, Mat> {

    private static final Logger LOG = LoggerFactory.getLogger(CamWorker.class);

    private final VideoPanel videoPanel;

    private final JFrame videoWindow;

    private final FaceDetector faceDetector;

    private final VideoCapture capture;

    private boolean updated;
    
    public CamWorker(JFrame videoWindow, VideoPanel videoPanel) {
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
                    videoWindow.setSize(webcamImage.width() + 40, webcamImage.height() + 60);
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
