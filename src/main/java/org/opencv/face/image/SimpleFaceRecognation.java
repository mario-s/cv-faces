package org.opencv.face.image;

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
 * @author schroeder
 */
public class SimpleFaceRecognation {
    
    private FaceRecognizer faceRecognizer;
    
    public SimpleFaceRecognation(String trainingDir) {
        faceRecognizer = createFisherFaceRecognizer();
        train(trainingDir);
    }

    private void train(String trainingDir) {
        File root = new File(trainingDir);

        FilenameFilter pngFilter = (File dir, String name) -> name.toLowerCase().endsWith(".jpg");

        File[] imageFiles = root.listFiles(pngFilter);
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.getIntBuffer();
        int counter = 0;

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            images.put(counter, img);
            labelsBuf.put(counter, label);
            counter++;
        }
        
        faceRecognizer.train(images, labels);
    }
    
    public int predict(String imgName) {
        File f = new File(imgName);
        Mat testImage = imread(f.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
        return faceRecognizer.predict(testImage);
    }
   
}
