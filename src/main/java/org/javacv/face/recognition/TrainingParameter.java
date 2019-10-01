package org.javacv.face.recognition;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

/**
 *
 * @author spindizzy
 */
public class TrainingParameter {
    private final MatVector images;
    private final Mat labels;

    public TrainingParameter(MatVector images, Mat labels) {
        this.images = images;
        this.labels = labels;
    }

    public MatVector getImages() {
        return images;
    }

    public Mat getLabels() {
        return labels;
    }

}
