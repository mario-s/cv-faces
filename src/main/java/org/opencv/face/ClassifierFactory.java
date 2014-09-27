package org.opencv.face;

import java.io.File;
import java.net.URL;
import org.opencv.objdetect.CascadeClassifier;

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
