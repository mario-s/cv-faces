package org.javacv.face.recognition;

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
        switch (recognizer.predict(face)) {
            case 0: return "female";
            case 1: return "male";
            default: return "";
        }
    }
}
