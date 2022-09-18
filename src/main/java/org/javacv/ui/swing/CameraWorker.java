package org.javacv.ui.swing;

import org.bytedeco.javacpp.opencv_core.Size;
import org.javacv.detect.Detectable;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.function.Consumer;

/**
 * A worker that deals with image update from the camera.
 *
 * @author spindizzy
 */
public class CameraWorker extends SwingWorker<Void, Mat> {

    private static final Logger LOG = LoggerFactory.getLogger(CameraWorker.class);

    private final Consumer<Mat> videoCanvas;

    private final Consumer<Size> videoWindow;

    private final Detectable faceDetector;

    private final VideoCaptureProxy capture;

    private boolean updated;

    public CameraWorker(Consumer<Size> videoWindow, Consumer<Mat> videoCanvas, Detectable faceDetector) {
        this(videoWindow, videoCanvas, faceDetector, new VideoCaptureProxy());
    }

    CameraWorker(Consumer<Size> videoWindow, Consumer<Mat> videoCanvas, Detectable faceDetector, VideoCaptureProxy capture) {
        this.videoWindow = videoWindow;
        this.videoCanvas = videoCanvas;
        this.faceDetector = faceDetector;
        this.capture = capture;
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
            videoWindow.accept(size);
            updated = true;
        }
    }

    private long markObjects(Mat webcamImage) {
        long marked = faceDetector.markObjects(webcamImage);
        videoCanvas.accept(webcamImage);
        return marked;
    }

    @Override
    protected void done() {
        capture.release();
    }
}
