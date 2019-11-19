package org.javacv.face.recognition;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.javacv.common.ImageUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation for {@link Trainable}.
 *
 * @author spindizzy
 */
public class DefaultTrainer implements Trainable{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultTrainer.class);

    private static final String JPG = ".jpg";

    private final String trainingDir;

    public DefaultTrainer(String trainingDir) {
        this.trainingDir = trainingDir;
    }
    
    @Override
    public TrainingParameter getParameter() {
        File[] imageFiles = filterImageFiles(JPG);
        int len = imageFiles.length;

        MatVector images = new MatVector(len);
        Mat labels = new Mat(len, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();

        for (int i = 0; i < len; i++) {
            File file = imageFiles[i];

            Mat img = ImageUtility.Instance.readAsGray(file.getAbsolutePath());
            images.put(i, img);

            int label = createLabel(file);
            LOG.trace("label {} for file {}", label, file);
            labelBuffer.put(i, label);
        }

        LOG.debug("labels for training: {}", labelBuffer);

        return new TrainingParameter(images, labels);
    }

    protected int createLabel(File file) {
        return parseInt(file.getName().split("\\-")[1]);
    }

    protected File[] filterImageFiles(String suffix) {
        return new File(trainingDir).listFiles((File dir, String name) -> name.toLowerCase().endsWith(suffix));
    }
    
}
