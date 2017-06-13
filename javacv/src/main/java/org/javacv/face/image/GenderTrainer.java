package org.javacv.face.image;

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
 *
 * @author spindizzy
 */
public class GenderTrainer extends DefaultTrainer {

    private static final String GENDER_JSON = "gender.json";
    private static final Logger LOG = LoggerFactory.getLogger(GenderTrainer.class);

    private Optional<Gender> gender = empty();

    public GenderTrainer(String trainingDir) {
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

        private List<String> females;
        private List<String> males;

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
