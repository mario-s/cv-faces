package org.javacv.face.image;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;

/**
 *
 * @author spindizzy
 */
public class GrayScaleTrainer extends AbstractTrainer{
    
    public GrayScaleTrainer(String trainingDir) {
        super(trainingDir);
    }
    
    @Override
    public TrainingParameter getParameter() {
        File[] imageFiles = filterImageFiles(JPG);
        
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();
        int counter = 0;

        for (File file : imageFiles) {
            Mat img = imread(file.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            images.put(counter, img);
            labelBuffer.put(counter, parseInt(file.getName().split("\\-")[0]));
            counter++;
        }
        
        return new TrainingParameter(images, labels);
    }
}
