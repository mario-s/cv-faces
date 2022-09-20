package org.javacv.detect;

import org.javacv.detect.face.dnn.DnnDetector;
import org.javacv.detect.face.haar.HaarDetector;
import org.javacv.detect.face.haar.recognize.GenderPredictor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

/**
 * Factory to create a new detector.
 *
 */
public class DetectorFactory {
    private static final Logger LOG = LoggerFactory.getLogger(DetectorFactory.class);

    public static Detectable create(String ... args) {
        String type = (args != null && args[0] != null) ? args[0] : "DNN";
        return create(type);
    }
    /**
     * Create a new detector.
     * @param type Possible values: "HARR", "DNN"
     * @return a new {@link Detectable}
     */
    public static Detectable create(String type) {
        LOG.debug("using detector type: {}", type);
        var t = ofNullable(type).map(String::toUpperCase).orElseGet(() -> "");
        return switch (t) {
            case "HAAR" -> haarDetector();
            case "DNN" -> dnnDetector();
            default -> throw new UnsupportedOperationException(format("%s is unsupported", type));
        };
    }

    private static Detectable haarDetector() {
        var trainingPath = DetectorFactory.class.getResource("../train").getPath();
        LOG.debug("using images from {}", trainingPath);
        HaarDetector detector = new HaarDetector();
        detector.setPrediction(new GenderPredictor(trainingPath));
        return detector;
    }

    private static Detectable dnnDetector() {
        return new DnnDetector();
    }
}
