package org.javacv.face.image;

import java.io.File;
import java.io.FilenameFilter;
import static java.lang.Integer.parseInt;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;

/**
 *
 * @author spindizzy
 */
abstract class AbstractTrainer implements Trainable{
    public static final String JPG = ".jpg";
    private final String trainingDir;

    public AbstractTrainer(String trainingDir) {
        this.trainingDir = trainingDir;
    }
    
    @Override
    public TrainingParameter getParameter() {
        File[] imageFiles = filterImageFiles(JPG);
        
        opencv_core.MatVector images = new opencv_core.MatVector(imageFiles.length);
        opencv_core.Mat labels = new opencv_core.Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();
        int counter = 0;

        for (File file : imageFiles) {
            opencv_core.Mat img = imread(file.getAbsolutePath(), getImageType());
            images.put(counter, img);
            labelBuffer.put(counter, parseInt(file.getName().split("\\-")[0]));
            counter++;
        }
        
        return new TrainingParameter(images, labels);
    }

    protected File[] filterImageFiles(String suffix) {
        File root = new File(trainingDir);
        FilenameFilter filter = (File dir, String name) -> name.toLowerCase().endsWith(suffix);
        return root.listFiles(filter);
    }

    protected abstract int getImageType();
}
