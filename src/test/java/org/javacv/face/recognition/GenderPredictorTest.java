package org.javacv.face.recognition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.function.Function;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.junit.jupiter.api.Assertions.*;

class GenderPredictorTest {

    private final Function<String, String> resource = f -> getClass().getResource(f).getFile();

    private GenderPredictor classUnderTest;

    @BeforeEach
    void setUp() {
        String trainingPath = Recognizer.class.getResource("../../train").getPath();
        classUnderTest = new GenderPredictor(trainingPath);
    }

    @Test
    @DisplayName("It should return male for a male picture.")
    void apply_M() {
        assertEquals("male", classUnderTest.predict(imread(resource.apply("m1_1.jpg"))));
    }

    @Test
    @DisplayName("It should return female for a female picture.")
    void apply_F() {
        assertEquals("female", classUnderTest.predict(imread(resource.apply("salma.jpg"))));
    }
}