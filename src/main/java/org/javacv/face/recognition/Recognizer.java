package org.javacv.face.recognition;

import java.io.File;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.javacv.common.ImageUtility;

import static org.bytedeco.javacpp.opencv_core.*;


/**
 * Default implementation of {@link Trainable} to find recognize faces.
 * @author spindizzy
 */
public class Recognizer implements Trainable<Integer> {
    
    private Size trainingImageSize;

    private final FaceRecognizer faceRecognizer;

    public Recognizer(RecognizerType type) {
        if (type == RecognizerType.Fisher) {
            faceRecognizer = FisherFaceRecognizer.create();
        } else {
            faceRecognizer = EigenFaceRecognizer.create();
        }
    }

    @Override
    public void train(TrainingSupplier trainer) {
        TrainingParameter parameter = trainer.get();
        MatVector images = parameter.getImages();
        Mat labels = parameter.getLabels();
        train(images, labels);
    }

    private void train(MatVector images, Mat labels) {
        faceRecognizer.train(images, labels);
        trainingImageSize = images.get(0L).size();
    }

    private Mat readImage(String imgName) {
        return ImageUtility.Instance.readAsGray(new File(imgName).getAbsolutePath());
    }

    @Override
    public Integer predict(Mat image) {
        Mat target = resizeImage(image);
        return faceRecognizer.predict_label(target);
    }

    private Mat resizeImage(Mat image) {
        if (!image.size().equals(trainingImageSize)){
            return ImageUtility.Instance.resize(image, trainingImageSize);
        }
        return image;
    }

    public int predict(String imgName) {
        Mat img = readImage(imgName);
        return predict(img);
    }
}
