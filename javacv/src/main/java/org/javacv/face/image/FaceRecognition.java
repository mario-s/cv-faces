package org.javacv.face.image;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
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
        this(null);
    }

    public FaceRecognition(int imageType) {
        this(imageType, null);
    }

    public FaceRecognition(String trainingDir) {
        this(CV_LOAD_IMAGE_GRAYSCALE, trainingDir);
    }

    public FaceRecognition(int imageType, String trainingDir) {
        this.imageType = imageType;
        faceRecognizer = createEigenFaceRecognizer();

        if (trainingDir != null) {
            train(trainingDir);
        }
    }

    private void train(String trainingDir) {
        File root = new File(trainingDir);

        FilenameFilter filter = (File dir, String name) -> name.toLowerCase().endsWith(".jpg");

        File[] imageFiles = root.listFiles(filter);
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        int counter = 0;

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), imageType);
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            images.put(counter, img);
            labels.getIntBuffer().put(counter, label);
            counter++;
        }

        faceRecognizer.train(images, labels);
    }

    private Mat readImage(String imgName) {
        File f = new File(imgName);
        return imread(f.getAbsolutePath(), imageType);
    }

    public int predict(String imgName) {
        return faceRecognizer.predict(readImage(imgName));
    }

}
