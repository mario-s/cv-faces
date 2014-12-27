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

/**
 *
 * @author spindizzy
 */
public class FaceRecognationTest {
    
    private FaceRecognition classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new FaceRecognition();
    }

    /**
     * Test of predict method, of class SimpleFaceRecognation.
     */
    @Test
    public void testPredictGreyScale() {
        String path = getClass().getResource("train/bw").getPath();
        classUnderTest.train(new GrayScaleTrainer(path));
        
        String imgName = getClass().getResource("1_test.jpg").getFile();
        int result = classUnderTest.predictGrayScale(imgName);
        assertEquals(1, result);
        
        imgName = getClass().getResource("2_test.jpg").getFile();
        result = classUnderTest.predictGrayScale(imgName);
        assertEquals(2, result);
        
        imgName = getClass().getResource("3_test.jpg").getFile();
        result = classUnderTest.predictGrayScale(imgName);
        assertEquals(3, result);
    }
    
}
