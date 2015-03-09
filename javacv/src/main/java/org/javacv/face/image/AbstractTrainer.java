package org.javacv.face.image;

import java.io.File;
import java.io.FilenameFilter;

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
    
    protected File[] filterImageFiles(String suffix) {
        File root = new File(trainingDir);
        FilenameFilter filter = (File dir, String name) -> name.toLowerCase().endsWith(suffix);
        return root.listFiles(filter);
    }
    
}
