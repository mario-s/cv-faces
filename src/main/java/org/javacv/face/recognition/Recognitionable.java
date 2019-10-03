package org.javacv.face.recognition;

import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author spindizzy
 */
public interface Recognitionable {

    int predict(opencv_core.Mat image);

    void train(Trainable trainer);
    
}
