package org.javacv.detect;

import org.javacv.detect.face.haar.HaarDetector;
import org.javacv.detect.face.haar.recognize.GenderPredictor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

/**
 * Create a new detector.
 */
public class DetectorFactory {
    private static final Logger LOG = LoggerFactory.getLogger(DetectorFactory.class);

    public enum DetectorType {
        DNN, HAAR
    }

    public static Detectable create(DetectorType type) {
        switch (type) {
            case HAAR: return createHaarDetector();
            default: throw new UnsupportedOperationException(format("%s is unsupported", type));
        }
    }

    private static Detectable createHaarDetector() {
        var trainingPath = DetectorFactory.class.getResource("../train").getPath();
        LOG.debug("using images from {}", trainingPath);
        HaarDetector detector = new HaarDetector();
        detector.setPrediction(new GenderPredictor(trainingPath));
        return detector;
    }
}
