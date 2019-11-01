package org.javacv.face.recognition;

/**
 * Defines training parameter.
 *
 * @author spindizzy
 */
@FunctionalInterface
public interface Trainable {
    
    TrainingParameter getParameter();
}
