package org.javacv.face.recognition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author spindizzy
 */
class FaceRecognationTest {
    
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
    void testPredict_EigenFaces() {
        Recognizer classUnderTest = new Recognizer(RecognizerType.Eigen);
        classUnderTest.train(new DefaultTrainer(trainingPath));
        
        String imgName = getClass().getResource("f1_0.jpg").getFile();
        int result = classUnderTest.predict(imgName);
        assertEquals(1, result);
        
        imgName = getClass().getResource("f2_0.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(2, result);
        
        imgName = getClass().getResource("m1_0.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(3, result);
        
        imgName = getClass().getResource("salma.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(3, result);
    }
    
    @Test
    void testPredict_Gender() {
        //0 == female, 1 == male
        Recognizer classUnderTest = new Recognizer(RecognizerType.Fisher);
        classUnderTest.train(new GenderTrainer(trainingPath));
        
        String imgName = getClass().getResource("f1_1.jpg").getFile();
        int result = classUnderTest.predict(imgName);
        assertEquals(0, result);
        
        imgName = getClass().getResource("f2_0.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(0, result);
        
        imgName = getClass().getResource("m1_1.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(1, result);
    }
}
