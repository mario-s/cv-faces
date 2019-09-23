package org.javacv.face.recognition;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.javacv.common.ImageUtility;

/**
 *
 * @author spindizzy
 */
public class DefaultTrainer implements Trainable{
    public static final String JPG = ".jpg";
    private final String trainingDir;

    public DefaultTrainer(String trainingDir) {
        this.trainingDir = trainingDir;
    }
    
    @Override
    public TrainingParameter getParameter() {
        File[] imageFiles = filterImageFiles(JPG);
        
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();
        int counter = 0;

        for (File file : imageFiles) {
            Mat img = ImageUtility.Instance.read(file.getAbsolutePath());
            images.put(counter, img);
            labelBuffer.put(counter, createLabel(file));
            counter++;
        }
        
        return new TrainingParameter(images, labels);
    }

    protected int createLabel(File file) {
        return parseInt(file.getName().split("\\-")[1]);
    }

    protected File[] filterImageFiles(String suffix) {
        File root = new File(trainingDir);
        return root.listFiles((File dir, String name) -> name.toLowerCase().endsWith(suffix));
    }
    
}
