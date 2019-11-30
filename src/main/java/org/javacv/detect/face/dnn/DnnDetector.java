package org.javacv.detect.face.dnn;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.javacv.detect.Detectable;

/**
 * Detector which uses a pretrained model to detect faces.
 */
public class DnnDetector implements Detectable {

    @Override
    public long markObjects(Frame img) {
        return 0;
    }

    @Override
    public long markObjects(opencv_core.Mat img) {
        return 0;
    }
}
