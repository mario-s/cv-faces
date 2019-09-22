package org.javacv.face.image;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public final class DetectorService implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DetectorService.class);

    private boolean run;

    private final FrameGrabber grabber;

    private final FaceDetector detector;

    private final CanvasFrame canvas;

    public DetectorService(CanvasFrame canvas, FaceRecognition recognition) {
        this.grabber = new OpenCVFrameGrabber(0);
        this.detector = new FaceDetector(recognition);
        this.canvas = canvas;
        run = true;
    }

    @Override
    public void run() {
        boolean sizeAdjusted = false;

        try {
            //Start grabber to capture video
            grabber.start();
            LOG.debug("giving camera some time...");
            Thread.sleep(3000);

            while (run) {

                if (!sizeAdjusted) {
                    //Set canvas size as per dimentions of video frame.
                    canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
                }

                //insert grabed video frame to IplImage img
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
