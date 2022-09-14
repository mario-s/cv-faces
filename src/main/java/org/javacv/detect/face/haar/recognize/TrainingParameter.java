package org.javacv.detect.face.haar.recognize;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

/**
 * The parameter for training are the images and their labels.
 *
 * @author spindizzy
 */
public class TrainingParameter {
    private final MatVector images;
    private final Mat labels;

    public TrainingParameter(MatVector images, Mat labels) {
        this.images = images;
        this.labels = labels.clone();
    }

    public MatVector getImages() {
        return images;
    }

    public Mat getLabels() {
        return labels.clone();
    }

}
