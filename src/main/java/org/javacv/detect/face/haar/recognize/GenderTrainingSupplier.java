package org.javacv.detect.face.haar.recognize;

import static java.lang.Integer.parseInt;

/**
 * This class provides {@link TrainingParameter} for gender recognition.
 * @author spindizzy
 */
public class GenderTrainingSupplier extends DefaultTrainingSupplier {

    public GenderTrainingSupplier(String trainingDir) {
        super(trainingDir);
    }

    @Override
    protected int createLabel(String fileName) {
        String chunk = fileName.split("\\-")[0];
        return parseInt(chunk);
    }

}
