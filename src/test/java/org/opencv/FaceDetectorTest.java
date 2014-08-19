package org.opencv;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author spindizzy
 */
public class FaceDetectorTest {
    
    private FaceDetector classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new FaceDetector();
    }
    
    private File createFile(String name){
        return new File(getClass().getResource(name).getFile());
    }

    @Test
    public void testIsFace() {
        assertFalse(classUnderTest.isFace(null));
        assertFalse(classUnderTest.isFace(new File(getClass().getName())));
        
        assertFalse("expected no human face for horses", classUnderTest.isFace(createFile("horses.jpg")));
        assertFalse("expected no human face for koala", classUnderTest.isFace(createFile("koala.jpg")));
        assertFalse("expected no human face for back", classUnderTest.isFace(createFile("back.jpg")));
        
        assertTrue("expected human face", classUnderTest.isFace(createFile("face.jpg")));
    }
}
