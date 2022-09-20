package org.javacv.detect.face.haar.recognize;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.javacv.common.ImageUtil;

/**
 * This class predicts the gender of a face image. It will return the gender in plain English.
 */
public class GenderPredictor implements Predictable<String> {

    private final Trainable<Integer> recognizer;

    public GenderPredictor(String trainingPath) {
        recognizer = new Recognizer(RecognizerType.Fisher);
        recognizer.train(new GenderTrainingSupplier(trainingPath));
    }

    @Override
    public String predict(Mat mat) {
        Mat face = ImageUtil.toGray(mat);
        return switch (recognizer.predict(face)) {
            case 0 -> "female";
            case 1 -> "male";
            default -> "";
        };
    }
}
