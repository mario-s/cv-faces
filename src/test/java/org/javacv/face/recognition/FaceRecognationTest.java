package org.javacv.face.recognition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author spindizzy
 */
class FaceRecognationTest {

    private final Function<String, String> resource = f -> getClass().getResource(f).getFile();
    
    private String trainingPath;
    
    @BeforeEach
    void setUp() {
        URL res = Recognizer.class.getResource("../../train");
        trainingPath = res.getPath();
    }
    
    /**
     * Test of predict method, of class SimpleFaceRecognation.
     */
    @Test
    @DisplayName("It should return index of a trained image.")
    void testPredict_EigenFaces() {
        Recognizer classUnderTest = new Recognizer(RecognizerType.Eigen);
        classUnderTest.train(new DefaultTrainer(trainingPath));
        
        int result = classUnderTest.predict(resource.apply("f1_0.jpg"));
        assertEquals(1, result);
        
        result = classUnderTest.predict(resource.apply("f2_0.jpg"));
        assertEquals(2, result);
        
        result = classUnderTest.predict(resource.apply("m1_0.jpg"));
        assertEquals(3, result);
        
        result = classUnderTest.predict(resource.apply("salma.jpg"));
        assertEquals(3, result);
    }
    
    @Test
    @DisplayName("It should return integer for a picture of male or female.")
    void testPredict_Gender_Numeric() {
        //0 == female, 1 == male
        Recognizer classUnderTest = new Recognizer(RecognizerType.Fisher);
        classUnderTest.train(new GenderTrainer(trainingPath));
        
        int result = classUnderTest.predict(resource.apply("f1_1.jpg"));
        assertEquals(0, result);
        
        result = classUnderTest.predict(resource.apply("f2_0.jpg"));
        assertEquals(0, result);
        
        result = classUnderTest.predict(resource.apply("m1_1.jpg"));
        assertEquals(1, result);
    }
}
