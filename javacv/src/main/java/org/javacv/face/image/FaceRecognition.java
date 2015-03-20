package org.javacv.face.image;

import java.io.File;
import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

import static org.bytedeco.javacpp.opencv_contrib.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;

/**
 *
 * @author spindizzy
 */
public class FaceRecognition {

    private final FaceRecognizer faceRecognizer;

    public FaceRecognition(RecognizerType type) {
        if (type == RecognizerType.Fisher) {
            faceRecognizer = createFisherFaceRecognizer();
        } else {
            faceRecognizer = createEigenFaceRecognizer();
        }
    }

    public void train(Trainable trainer) {
        train(trainer.getParameter());
    }

    private void train(TrainingParameter param) {
        train(param.getImages(), param.getLabels());
    }

    private void train(MatVector images, Mat labels) {
        faceRecognizer.train(images, labels);
    }

    private Mat readImage(String imgName) {
        File f = new File(imgName);
        return ImageReader.Instance.read(f.getAbsolutePath());
    }

    private int predict(Mat image) {
        return faceRecognizer.predict(image);
    }

    public int predict(String imgName) {
        Mat img = readImage(imgName);
        return predict(img);
    }
}
