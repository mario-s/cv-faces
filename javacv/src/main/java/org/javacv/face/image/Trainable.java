package org.javacv.face.image;

/**
 *
 * @author spindizzy
 */
public interface Trainable {
    
    TrainingParameter getParameter();
    
    int getImageType();
    
}
