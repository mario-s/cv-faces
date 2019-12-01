package org.javacv.detect;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacpp.opencv_core.Mat;

/**
 * Interface to detect an object in a image.
 */
public interface Detectable {

    /**
     * Marks objects on the image.
     *
     * @param img the image to be analysed as {@link Frame}.
     * @return number of detected objects.
     */
    long markObjects(Frame img);

    /**
     * Marks objects on the image.
     *
     * @param img the image to be analysed as {@link Mat}.
     * @return number of detected objects.
     */
    long markObjects(Mat img);
}
