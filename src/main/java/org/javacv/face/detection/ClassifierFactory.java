package org.javacv.face.detection;

import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * Factory for {@link CascadeClassifier}.
 * 
 * @author spindizzy
 */
public enum ClassifierFactory {

    Instance;

    private static final Logger LOG = LoggerFactory.getLogger(ClassifierFactory.class);

    public CascadeClassifier create(String fileName) {
        URL resource = getClass().getResource(fileName);
        LOG.debug("using classifier from {}", resource);

        return new CascadeClassifier(new File(resource.getPath()).getPath());
    }
}
