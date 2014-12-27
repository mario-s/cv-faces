package org.javacv.face.image;

import java.io.File;
import java.io.FilenameFilter;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;

/**
 *
 * @author spindizzy
 */
public class GrayScaleTrainer implements Trainable{
    
    public static final String SUFFIX = ".jpg";
    
    private final String trainingDir;
    
    public GrayScaleTrainer(String trainingDir) {
        this.trainingDir = trainingDir;
    }
    
    @Override
    public TrainingParameter getParameter() {
        File root = new File(trainingDir);

        FilenameFilter filter = (File dir, String name) -> name.toLowerCase().endsWith(SUFFIX);

        File[] imageFiles = root.listFiles(filter);
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        int counter = 0;

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            images.put(counter, img);
            labels.getIntBuffer().put(counter, label);
            counter++;
        }
        
        return new TrainingParameter(images, labels);
    }
    
    
}
