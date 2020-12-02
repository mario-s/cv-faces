package org.javacv.detect.face.haar.recognize;

/**
 * Defines a way to train for recognition.
 * 
 * @author spindizzy
 */
public interface Trainable<T> extends Predictable<T> {

    /**
     * Feed the implementation with training images.
     * @param trainer
     */
    void train(TrainingSupplier trainer);
    
}
