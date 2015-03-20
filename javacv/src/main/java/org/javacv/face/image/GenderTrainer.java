package org.javacv.face.image;

import java.io.File;
import static java.lang.Integer.parseInt;

/**
 *
 * @author spindizzy
 */
public class GenderTrainer extends DefaultTrainer{

    public GenderTrainer(String trainingDir) {
        super(trainingDir);
    }

    @Override
    protected int createLabel(File file) {
        return parseInt(file.getName().split("\\-")[0]);
    }

    
}
