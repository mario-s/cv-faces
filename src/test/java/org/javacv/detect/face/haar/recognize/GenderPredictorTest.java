package org.javacv.detect.face.haar.recognize;

import org.javacv.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Function;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.junit.jupiter.api.Assertions.*;

class GenderPredictorTest {

    private final Function<String, String> resource = f -> getClass().getResource(f).getFile();

    private GenderPredictor classUnderTest;

    @BeforeEach
    void setUp() {
        var resource = Main.class.getResource("train");
        var trainingPath = resource.getPath();
        classUnderTest = new GenderPredictor(trainingPath);
    }

    @ParameterizedTest(name = "{index} It should return {0} of a trained image {1}.")
    @CsvSource(value = {"male, m1_1.jpg", "female, salma.jpg"})
    void predict(String expected, String file) {
        assertEquals(expected, classUnderTest.predict(imread(resource.apply(file))));
    }

}