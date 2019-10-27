package org.javacv.face.recognition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URL;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Integration test for {@link Recognizer}
 *
 * @author spindizzy
 */
@DisplayName("Integration test for Recognizer")
class FaceRecognationTest {

    private final Function<String, String> resource = f -> FaceRecognationTest.class.getResource(f).getFile();

    private String trainingPath;

    @BeforeEach
    void setUp() {
        URL res = Recognizer.class.getResource("../../train");
        trainingPath = res.getPath();
    }

    @Nested
    class Eigen {
        private Recognizer classUnderTest;

        @BeforeEach
        void setup() {
            classUnderTest = new Recognizer(RecognizerType.Eigen);
            classUnderTest.train(new DefaultTrainer(trainingPath));
        }

        @Test
        @DisplayName("It should return index of a trained image.")
        void predict() {
            int result = classUnderTest.predict(resource.apply("f1_0.jpg"));
            assertEquals(1, result);

            result = classUnderTest.predict(resource.apply("f2_0.jpg"));
            assertEquals(2, result);

            result = classUnderTest.predict(resource.apply("m1_0.jpg"));
            assertEquals(3, result);

            result = classUnderTest.predict(resource.apply("salma.jpg"));
            assertEquals(3, result);
        }
    }

    @Nested
    class Fisher {

        private Recognizer classUnderTest;

        @BeforeEach
        void setup() {
            classUnderTest = new Recognizer(RecognizerType.Fisher);
            classUnderTest.train(new GenderTrainer(trainingPath));
        }

        @ParameterizedTest(name = "{index} It should return 0 for a picture ({0}) of a female.")
        @ValueSource(strings = {"f1_1.jpg", "f2_0.jpg"})
        void predict_F(String source) {
            int result = classUnderTest.predict(resource.apply(source));
            assertEquals(0, result);
        }

        @ParameterizedTest(name = "{index} It should return 1 for a picture ({0}) of a male.")
        @ValueSource(strings = {"m1_1.jpg"})
        void predict_M(String source) {
            int result = classUnderTest.predict(resource.apply(source));
            assertEquals(1, result);
        }
    }
}
