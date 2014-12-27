package org.javacv.face.image;

import java.io.File;
import java.io.FilenameFilter;
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

    private int imageType;

    public FaceRecognition() {
        faceRecognizer = createEigenFaceRecognizer();
    }

    public void train(Trainable trainable) {
        train(trainable.getParameter());
    }
    
    private void train(TrainingParameter param) {
        train(param.getImages(), param.getLabels());
    }

    private void train(MatVector images, Mat labels) {
        faceRecognizer.train(images, labels);
    }

    private Mat readImage(String imgName, int imageType) {
        File f = new File(imgName);
        return imread(f.getAbsolutePath(), imageType);
    }

    public int predictGrayScale(String imgName) {
        return predict(readImage(imgName, CV_LOAD_IMAGE_GRAYSCALE));
    }
    
    private int predict(Mat image) {
        return faceRecognizer.predict(image);
    }

}
