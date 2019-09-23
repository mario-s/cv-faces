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
 *
 * @author spindizzy
 */
public class FaceRecognition implements FaceRecognitionable{
    
    private Size trainingImageSize;

    private final FaceRecognizer faceRecognizer;

    public FaceRecognition(RecognizerType type) {
        if (type == RecognizerType.Fisher) {
            faceRecognizer = FisherFaceRecognizer.create();
        } else {
            faceRecognizer = EigenFaceRecognizer.create();
        }
    }

    @Override
    public void train(Trainable trainer) {
        train(trainer.getParameter());
    }

    private void train(TrainingParameter param) {
        train(param.getImages(), param.getLabels());
    }

    private void train(MatVector images, Mat labels) {
        faceRecognizer.train(images, labels);
        trainingImageSize = images.get(0L).size();
    }

    private Mat readImage(String imgName) {
        File f = new File(imgName);
        return ImageUtility.Instance.read(f.getAbsolutePath());
    }

    @Override
    public int predict(Mat image) {
        Mat target = resizeImage(image);
        return faceRecognizer.predict_label(target);
    }

    private Mat resizeImage(Mat image) {
        Mat target = image;
        if(!image.size().equals(trainingImageSize)){
            target = ImageUtility.Instance.resize(image, trainingImageSize);
        }
        return target;
    }

    public int predict(String imgName) {
        Mat img = readImage(imgName);
        return predict(img);
    }
}
