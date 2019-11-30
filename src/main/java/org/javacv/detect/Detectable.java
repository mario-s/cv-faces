package org.javacv.detect;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacpp.opencv_core.Mat;

/**
 * Interface to detect an object in a image.
 */
public interface Detectable {

    long markObjects(Frame img);

    long markObjects(Mat img);
}
