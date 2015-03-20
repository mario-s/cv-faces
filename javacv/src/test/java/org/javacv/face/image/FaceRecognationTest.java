/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javacv.face.image;

import java.net.URL;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author spindizzy
 */
public class FaceRecognationTest {
    
    private String trainingPath;
    
    @Before
    public void setUp() {
        trainingPath = getClass().getResource("train").getPath();
    }
    
    /**
     * Test of predict method, of class SimpleFaceRecognation.
     */
    @Test
    public void testPredict_EigenFaces() {
        FaceRecognition classUnderTest = new FaceRecognition(RecognizerType.Eigen);
        classUnderTest.train(new DefaultTrainer(trainingPath));
        
        String imgName = getClass().getResource("1_test.jpg").getFile();
        int result = classUnderTest.predict(imgName);
        assertEquals(1, result);
        
        imgName = getClass().getResource("2_test.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(2, result);
        
        imgName = getClass().getResource("3_test.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(3, result);
        
        imgName = getClass().getResource("3_salma_test.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(3, result);
    }
    
    @Test
    public void testPredict_Gender() {
        //0 == female, 1 == male
        FaceRecognition classUnderTest = new FaceRecognition(RecognizerType.Fisher);
        classUnderTest.train(new GenderTrainer(trainingPath));
        
        String imgName = getClass().getResource("1_test.jpg").getFile();
        int result = classUnderTest.predict(imgName);
        assertEquals(0, result);
        
        imgName = getClass().getResource("2_test.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(0, result);
        
        imgName = getClass().getResource("3_test.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(1, result);
        
        imgName = getClass().getResource("3_salma_test.jpg").getFile();
        result = classUnderTest.predict(imgName);
        assertEquals(0, result);
    }
    
}
