package org.opencv.face;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public abstract class AbstractFaceDetector {

    protected static final Scalar COLOR = new Scalar(0, 255, 0);

    protected final Logger log;

    public AbstractFaceDetector() {
        log = LoggerFactory.getLogger(getClass());
    }

    public abstract MatOfRect findFace(Mat image);
}
