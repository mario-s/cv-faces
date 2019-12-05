package org.javacv.detect;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import org.javacv.glue.ImageShowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A service to detect objects. It can be used to run in a different thread to avoid blocking.
 *
 * @author spindizzy
 */
public final class DetectorService implements Runnable {

    private static final long DEFAULT_DELAY = 3000;

    private static final Logger LOG = LoggerFactory.getLogger(DetectorService.class);

    private boolean run;

    private long delay;

    private final FrameGrabber grabber;

    private final Detectable detector;

    private final ImageShowable canvas;

    public DetectorService(ImageShowable canvas, Detectable detectable) {
        this(new OpenCVFrameGrabber(0), canvas, detectable);
    }

    public DetectorService(FrameGrabber grabber, ImageShowable canvas, Detectable detectable) {
        this.grabber = grabber;
        this.detector = detectable;
        this.canvas = canvas;

        run = true;
        delay = DEFAULT_DELAY;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        boolean sizeAdjusted = false;

        try {
            startGrabber();

            while (run) {

                if (!sizeAdjusted) {
                    //Set canvas size as per dimensions of video frame.
                    int width = grabber.getImageWidth();
                    int height = grabber.getImageHeight();
                    LOG.debug("canvas size: {}x{}", width, height);
                    canvas.setSize(width, height);
                }

                Frame img = grabber.grab();

                if (img != null) {
                    sizeAdjusted = true;
                    //Show video frame in canvas
                    detector.markObjects(img);
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
        Thread.sleep(delay);
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
