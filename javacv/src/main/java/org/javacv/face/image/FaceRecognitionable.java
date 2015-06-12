package org.javacv.face.image;

import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author spindizzy
 */
public interface FaceRecognitionable {

    int predict(opencv_core.Mat image);

    void train(Trainable trainer);
    
}
