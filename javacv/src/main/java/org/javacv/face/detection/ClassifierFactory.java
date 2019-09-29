package org.javacv.face.detection;

import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import java.io.File;
import java.net.URL;

/**
 * Factory for {@link CascadeClassifier}.
 * 
 * @author spindizzy
 */
public enum ClassifierFactory {

    Instance;

    public CascadeClassifier create(String fileName) {
        URL resource = getClass().getResource(fileName);
        File cFile = new File(resource.getPath());
        String cPath = cFile.getPath();
        
        return new CascadeClassifier(cPath);
    }
}
