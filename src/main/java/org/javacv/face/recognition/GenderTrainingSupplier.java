package org.javacv.face.recognition;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides {@link TrainingParameter} for gender recognition.
 * @author spindizzy
 */
public class GenderTrainingSupplier extends DefaultTrainingSupplier {

    private static final String GENDER_JSON = "gender.json";
    private static final Logger LOG = LoggerFactory.getLogger(GenderTrainingSupplier.class);

    private Optional<Gender> gender = empty();

    public GenderTrainingSupplier(String trainingDir) {
        super(trainingDir);
        readGenders(trainingDir);
    }

    private void readGenders(String trainingDir) {
        try {
            Gson gson = new Gson();
            File file = new File(trainingDir, GENDER_JSON);
            Reader reader = new FileReader(file);
            gender = ofNullable(gson.fromJson(reader, Gender.class));
        } catch (FileNotFoundException ex) {
            LOG.warn(ex.getMessage(), ex);
        }
    }

    @Override
    protected int createLabel(File file) {
        return parseInt(file.getName().split("\\-")[0]);
    }

    private static class Gender {

        private final List<String> females;
        private final List<String> males;

        public Gender(List<String> females, List<String> males) {
            this.females = females;
            this.males = males;
        }

        public List<String> getFemales() {
            return females;
        }

        public List<String> getMales() {
            return males;
        }
    }

}
