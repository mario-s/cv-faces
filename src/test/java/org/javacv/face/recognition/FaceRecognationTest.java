package org.javacv.face.recognition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

        @Test
        @DisplayName("It should return integer for a picture of male or female.")
        void predict() {
            //0 == female, 1 == male
            int result = classUnderTest.predict(resource.apply("f1_1.jpg"));
            assertEquals(0, result);

            result = classUnderTest.predict(resource.apply("f2_0.jpg"));
            assertEquals(0, result);

            result = classUnderTest.predict(resource.apply("m1_1.jpg"));
            assertEquals(1, result);
        }
    }
}
