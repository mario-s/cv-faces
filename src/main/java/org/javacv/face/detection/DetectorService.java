package org.javacv.face.detection;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import org.javacv.face.recognition.Predictable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A service to detect images. It can bbe used in a different thread.
 *
 * @author spindizzy
 */
public final class DetectorService implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DetectorService.class);

    private boolean run;

    private final FrameGrabber grabber;

    private final Detector detector;

    private final CanvasFrame canvas;

    public DetectorService(CanvasFrame canvas, Predictable<String> prediction) {
        this.grabber = new OpenCVFrameGrabber(0);
        this.detector = new Detector();
        this.detector.setPrediction(prediction);
        this.canvas = canvas;
        run = true;
    }

    @Override
    public void run() {
        boolean sizeAdjusted = false;

        try {
            startGrabber();

            while (run) {

                if (!sizeAdjusted) {
                    //Set canvas size as per dimensions of video frame.
                    canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
                }

                Frame img = grabber.grab();

                if (img != null) {
                    sizeAdjusted = true;
                    //Show video frame in canvas
                    detector.markFaces(img);
                    canvas.showImage(img);
                }
            }
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    //Start grabber to capture video
    private void startGrabber() throws FrameGrabber.Exception, InterruptedException {
        grabber.start();
        LOG.debug("giving camera some time...");
        Thread.sleep(3000);
        LOG.debug("...camera should be ready");
    }

    public void stop() {
        run = false;

        try {
            grabber.release();
            LOG.debug("grabber released");
        } catch (FrameGrabber.Exception ex) {
            LOG.warn(ex.getMessage(), ex);
        }
    }
}
