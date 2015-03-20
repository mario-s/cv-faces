package org.javacv.face.image;

import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;

/**
 *
 * @author spindizzy
 */
@FunctionalInterface
public interface Trainable {
    
    TrainingParameter getParameter();
    
    
}
