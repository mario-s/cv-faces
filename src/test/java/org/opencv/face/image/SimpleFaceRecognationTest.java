/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencv.face.image;

import java.net.URL;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author schroeder
 */
public class SimpleFaceRecognationTest {
    
    private SimpleFaceRecognation classUnderTest;
    
    @Before
    public void setUp() {
        URL resource = getClass().getResource("training");
        String path = resource.getPath();
        classUnderTest = new SimpleFaceRecognation(path);
    }

    /**
     * Test of predict method, of class SimpleFaceRecognation.
     */
    @Test
    public void testPredict() {
        String imgName = getClass().getResource("salma_hayek_test.jpg").getFile();
        int expResult = 1;
        int result = classUnderTest.predict(imgName);
        assertEquals(expResult, result);
    }
    
}
