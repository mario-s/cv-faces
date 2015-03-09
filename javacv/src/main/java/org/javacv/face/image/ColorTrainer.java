package org.javacv.face.image;

import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_COLOR;

/**
 *
 * @author spindizzy
 */
public class ColorTrainer extends AbstractTrainer{

    public ColorTrainer(String trainingDir) {
        super(trainingDir);
    }

    @Override
    public int getImageType() {
        return CV_LOAD_IMAGE_COLOR;
    }
    
}
