package org.javacv.face.image;

/**
 *
 * @author spindizzy
 */
@FunctionalInterface
public interface Trainable {
    
    TrainingParameter getParameter();
    
}
