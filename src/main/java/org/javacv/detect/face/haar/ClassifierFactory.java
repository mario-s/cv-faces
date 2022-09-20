package org.javacv.detect.face.haar;

import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Factory for {@link CascadeClassifier}.
 * 
 * @author spindizzy
 */
public enum ClassifierFactory {

    Instance;

    private static final Logger LOG = LoggerFactory.getLogger(ClassifierFactory.class);

    public Optional<CascadeClassifier> create(String fileName) {
        try {
            URL resource = getClass().getResource(fileName);

            String path = Paths.get(resource.toURI()).toString();
            LOG.debug("using classifier from {}", path);

            return of(new CascadeClassifier(path));
        } catch (URISyntaxException exc) {
            LOG.warn(exc.getMessage());
            return empty();
        }
    }
}
