package org.opencv.face.swing;

import java.util.List;
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

    public CamWorker(JFrame videoWindow, VideoPanel videoPanel) {
        this.videoWindow = videoWindow;
        this.videoPanel = videoPanel;
        faceDetector = new FaceDetector();
        capture = new VideoCapture(0);
    }

    @Override
    protected Void doInBackground() throws Exception {

        Thread.sleep(1000);

        while (!isCancelled()) {
            Mat webcamImage = new Mat();
            capture.read(webcamImage);
            if (!webcamImage.empty()) {
                publish(webcamImage);
            } else {
                LOG.warn(" --(!) No captured frame -- Break!");
                break;
            }
        }

        return null;
    }

    @Override
    protected void process(List<Mat> chunks) {
        Mat img = chunks.get(0);
        videoWindow.setSize(img.width() + 40, img.height() + 60);
        //-- 3. Apply the classifier to the captured image  
        faceDetector.markFaces(img);
        //-- 4. Display the image  
        videoPanel.updateImage(img);
    }

    @Override
    protected void done() {
        capture.release();
    }

    
}
