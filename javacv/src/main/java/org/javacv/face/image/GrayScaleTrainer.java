package org.javacv.face.image;

import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;

/**
 *
 * @author spindizzy
 */
public class GrayScaleTrainer extends AbstractTrainer{
    
    public GrayScaleTrainer(String trainingDir) {
        super(trainingDir);
    }
    
    @Override
    protected int getImageType() {
        return CV_LOAD_IMAGE_GRAYSCALE;
    }
}
