package org.javacv.face.recognition;

/**
 *
 * @author spindizzy
 */
@FunctionalInterface
public interface Trainable {
    
    TrainingParameter getParameter();
}
