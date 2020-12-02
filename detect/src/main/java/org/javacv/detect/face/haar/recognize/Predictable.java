package org.javacv.detect.face.haar.recognize;

import org.bytedeco.javacpp.opencv_core.Mat;

/**
 * Defines a way to predict the label for the given image.
 */
@FunctionalInterface
public interface Predictable<T> {

    T predict(Mat image);
}
