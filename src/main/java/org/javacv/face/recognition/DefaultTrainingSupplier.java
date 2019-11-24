package org.javacv.face.recognition;

import static java.lang.Integer.parseInt;

import java.nio.IntBuffer;
import java.nio.file.*;
import java.util.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.javacv.common.FileUtil;
import org.javacv.common.ImageUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;

/**
 * Default implementation for {@link TrainingSupplier}.
 *
 * @author spindizzy
 */
public class DefaultTrainingSupplier implements TrainingSupplier {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultTrainingSupplier.class);

    /**
     * Constant for jpg files filter.
     */
    protected static final String SUFFIX_JPG = ".jpg";

    /**
     * Constant for pgm files filter.
     */
    protected static final String SUFFIX_PGM = ".pgm";

    private final String trainingDir;

    public DefaultTrainingSupplier(String trainingDir) {
        this.trainingDir = trainingDir;
    }
    
    @Override
    public TrainingParameter get() {
        List<Path> paths = filterImageFiles(filesSuffix());
        int len = paths.size();

        MatVector images = new MatVector(len);
        Mat labels = new Mat(len, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();

        for (int i = 0; i < len; i++) {
            Path path = paths.get(i);

            Mat img = ImageUtility.Instance.readAsGray(path.toString());
            images.put(i, img);

            int label = createLabel(path.getFileName().toString());
            LOG.trace("label {} for file {}", label, path);
            labelBuffer.put(i, label);
        }

        LOG.debug("labels for training: {}", labelBuffer);

        return new TrainingParameter(images, labels);
    }

    protected int createLabel(String fileName) {
        String chunk = fileName.split("\\-")[1];
        return parseInt(chunk);
    }

    /**
     * Returns the suffixes which should be used for image file filtering.
     * @return possible suffixes
     */
    protected String[] filesSuffix() {
        return new String[] {SUFFIX_JPG};
    }

    /**
     * Filters image files in the training directory by the suffixes.
     * @param suffixes suffixes for the files which should be returned
     * @return a collection of {@link Path} to the images files
     */
    protected List<Path> filterImageFiles(String[] suffixes) {
        return FileUtil.filterFiles(trainingDir, suffixes);
    }
}
