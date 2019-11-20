package org.javacv.face.recognition;

import org.bytedeco.javacpp.opencv_core.Mat;

/**
 * Defines a way to recognize images.
 * 
 * @author spindizzy
 */
public interface Recognitionable {

    /**
     * Predict the category of the given image.
     * @param image an image as {@link Mat}.
     * @return
     */
    int predict(Mat image);

    /**
     * Feed the implementation with training images.
     * @param trainer
     */
    void train(TrainingSupplier trainer);
    
}
